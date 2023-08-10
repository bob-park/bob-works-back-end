package org.bobpark.documentservice.domain.document.listener.holiday;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.documentservice.domain.document.entity.Document;
import org.bobpark.documentservice.domain.document.entity.DocumentApproval;
import org.bobpark.documentservice.domain.document.entity.holiday.HolidayWorkReport;
import org.bobpark.documentservice.domain.document.entity.holiday.HolidayWorkUser;
import org.bobpark.documentservice.domain.document.listener.DocumentProvider;
import org.bobpark.documentservice.domain.document.repository.approval.DocumentApprovalRepository;
import org.bobpark.documentservice.domain.document.type.DocumentStatus;
import org.bobpark.documentservice.domain.user.feign.client.UserClient;
import org.bobpark.documentservice.domain.user.model.vacation.AddAlternativeVacationRequest;

@Slf4j
@RequiredArgsConstructor
public class HolidayWorkReportProvider implements DocumentProvider {

    private final UserClient userClient;
    private final DocumentApprovalRepository documentApprovalRepository;

    @Override
    public Document approval(long approvalId, Document document) {

        HolidayWorkReport holidayWorkReport = (HolidayWorkReport)document;

        DocumentApproval approval = getApprovalById(approvalId);

        allowStatus(document.getStatus());
        allowStatus(approval.getStatus());

        approval.updateStatus(DocumentStatus.APPROVE, null);

        log.debug("approved vacation document. (id={})", holidayWorkReport.getId());

        if (approval.getApprovalLine().getNext() == null) {
            for (HolidayWorkUser workUser : holidayWorkReport.getUsers()) {

                if (!workUser.isManualInput() && workUser.isVacation()) {

                    int addAlternativeVacationCount = (int)workUser.getTotalWorkTime() / HolidayWorkUser.VACATION_TIME;

                    AddAlternativeVacationRequest addRequest =
                        AddAlternativeVacationRequest.builder()
                            .effectiveDate(workUser.getWorkDate())
                            .effectiveReason(holidayWorkReport.getWorkPurpose())
                            .effectiveCount(addAlternativeVacationCount)
                            .build();

                    userClient.addAlternativeVacation(workUser.getWorkUserId(), addRequest);
                }

            }
        }

        return holidayWorkReport;
    }

    @Override
    public Document reject(long approvalId, Document document, String reason) {

        DocumentApproval approval = getApprovalById(approvalId);

        allowStatus(document.getStatus());
        allowStatus(approval.getStatus());

        // TODO reject 처리
        document.updateStatus(DocumentStatus.REJECT);

        return null;
    }

    @Override
    public Document canceled(Document document) {

        allowStatus(document.getStatus());

        // TODO cancel 처리
        document.updateStatus(DocumentStatus.CANCEL);

        return document;
    }

    @Override
    public boolean isSupport(Class<? extends Document> clazz) {
        return clazz.isAssignableFrom(HolidayWorkReport.class);
    }

    private DocumentApproval getApprovalById(long approvalId) {
        return documentApprovalRepository.findById(approvalId)
            .orElseThrow(() -> new NotFoundException(DocumentApproval.class, approvalId));
    }

}
