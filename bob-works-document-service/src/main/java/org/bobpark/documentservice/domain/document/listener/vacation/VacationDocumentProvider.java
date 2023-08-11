package org.bobpark.documentservice.domain.document.listener.vacation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.documentservice.domain.document.entity.Document;
import org.bobpark.documentservice.domain.document.entity.DocumentApproval;
import org.bobpark.documentservice.domain.document.entity.vacation.VacationDocument;
import org.bobpark.documentservice.domain.document.listener.DocumentProvider;
import org.bobpark.documentservice.domain.document.repository.approval.DocumentApprovalRepository;
import org.bobpark.documentservice.domain.document.type.DocumentStatus;
import org.bobpark.documentservice.domain.user.feign.client.UserClient;
import org.bobpark.documentservice.domain.user.model.vacation.CancelUserVacationRequest;
import org.bobpark.documentservice.domain.user.model.vacation.UseUserVacationRequest;

@Slf4j
@RequiredArgsConstructor
public class VacationDocumentProvider implements DocumentProvider {

    private final UserClient userClient;
    private final DocumentApprovalRepository documentApprovalRepository;

    @Override
    public Document approval(long approvalId, Document document) {
        VacationDocument vacationDocument = (VacationDocument)document;

        DocumentApproval approval = getApprovalById(approvalId);

        allowStatus(document.getStatus());
        allowStatus(approval.getStatus());

        approval.updateStatus(DocumentStatus.APPROVE, null);

        log.debug("approved vacation document. (id={})", vacationDocument.getId());

        if (approval.getApprovalLine().getNext() == null) {
            useUserVacation(vacationDocument);
        }

        return vacationDocument;
    }

    @Override
    public Document reject(long approvalId, Document document, String reason) {

        VacationDocument vacationDocument = (VacationDocument)document;

        DocumentApproval approval = getApprovalById(approvalId);

        allowStatus(document.getStatus());
        allowStatus(approval.getStatus());

        approval.updateStatus(DocumentStatus.REJECT, reason);

        return vacationDocument;
    }

    @Override
    public Document canceled(Document document) {

        // allowStatus(document.getStatus());
        // * 휴가계는 결재 상태가 "취소" 를 제외하고 취소할 수 있다.
        if (document.getStatus() == DocumentStatus.CANCEL) {
            throw new IllegalArgumentException("Invalid status. (" + document.getStatus() + ")");
        }

        VacationDocument vacationDocument = (VacationDocument)document;

        if (vacationDocument.getStatus() == DocumentStatus.APPROVE) {

            // update user vacation
            CancelUserVacationRequest cancelRequest =
                CancelUserVacationRequest.builder()
                    .type(vacationDocument.getVacationType())
                    .cancelCount(vacationDocument.getDaysCount())
                    .cancelAlternativeVacationIds(vacationDocument.getUseAlternativeVacationIds())
                    .build();

            userClient.cancelVacation(document.getWriterId(), cancelRequest);

            log.debug("canceled vacation document. (id={}, daysCount={})",
                vacationDocument.getId(),
                vacationDocument.getDaysCount());
        }

        vacationDocument.updateStatus(DocumentStatus.CANCEL);

        return document;
    }

    @Override
    public boolean isSupport(Class<? extends Document> clazz) {
        return clazz.isAssignableFrom(VacationDocument.class);
    }

    private DocumentApproval getApprovalById(long approvalId) {
        return documentApprovalRepository.findById(approvalId)
            .orElseThrow(() -> new NotFoundException(DocumentApproval.class, approvalId));
    }

    private void useUserVacation(VacationDocument document) {
        userClient.useVacation(
            document.getWriterId(),
            UseUserVacationRequest.builder()
                .type(document.getVacationType())
                .useCount(document.getDaysCount())
                .useAlternativeVacationIds(document.getUseAlternativeVacationIds())
                .build());
    }

}
