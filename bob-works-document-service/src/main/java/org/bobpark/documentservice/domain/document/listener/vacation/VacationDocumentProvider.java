package org.bobpark.documentservice.domain.document.listener.vacation;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.transaction.annotation.Transactional;

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
@Transactional(readOnly = true)
public class VacationDocumentProvider implements DocumentProvider {

    private static final List<DocumentStatus> ALLOW_STATUS = List.of(DocumentStatus.WAITING, DocumentStatus.PROCEEDING);

    private final UserClient userClient;

    private final DocumentApprovalRepository documentApprovalRepository;

    @Override
    public Document approval(long approvalId, Document document) {
        VacationDocument vacationDocument = (VacationDocument)document;

        DocumentApproval approval = getApprovalById(approvalId);

        if (!ALLOW_STATUS.contains(approval.getStatus())) {
            throw new IllegalStateException("Invalid status. (" + approval.getStatus() + ")");
        }

        approval.updateStatus(DocumentStatus.APPROVE, null);

        log.debug("approved vacation document. (id={})", vacationDocument.getId());

        useUserVacation(vacationDocument);

        return vacationDocument;
    }

    @Override
    public Document reject(long approvalId, Document document, String reason) {

        VacationDocument vacationDocument = (VacationDocument)document;

        DocumentApproval approval = getApprovalById(approvalId);

        if (!ALLOW_STATUS.contains(approval.getStatus())) {
            throw new IllegalStateException("Invalid status. (" + approval.getStatus() + ")");
        }

        approval.updateStatus(DocumentStatus.REJECT, reason);

        return vacationDocument;
    }

    @Override
    public Document canceled(Document document) {

        VacationDocument vacationDocument = (VacationDocument)document;

        if (vacationDocument.getStatus() == DocumentStatus.APPROVE) {

            // update user vacation
            CancelUserVacationRequest cancelRequest =
                CancelUserVacationRequest.builder()
                    .type(vacationDocument.getVacationType())
                    .cancelCount(vacationDocument.getDaysCount())
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
                .build());
    }

}
