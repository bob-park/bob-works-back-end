package org.bobpark.documentservice.domain.document.listener.holiday;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.documentservice.domain.document.entity.Document;
import org.bobpark.documentservice.domain.document.entity.DocumentApproval;
import org.bobpark.documentservice.domain.document.entity.holiday.HolidayWorkReport;
import org.bobpark.documentservice.domain.document.listener.DocumentProvider;
import org.bobpark.documentservice.domain.document.repository.approval.DocumentApprovalRepository;
import org.bobpark.documentservice.domain.document.type.DocumentStatus;
import org.bobpark.documentservice.domain.user.feign.client.UserClient;

@Slf4j
@RequiredArgsConstructor
public class HolidayWorkReportProvider implements DocumentProvider {

    private final UserClient userClient;
    private final DocumentApprovalRepository documentApprovalRepository;

    @Override
    public Document approval(long approvalId, Document document) {

        HolidayWorkReport holidayWorkReport = (HolidayWorkReport)document;

        DocumentApproval approval = getApprovalById(approvalId);

        allowStatus(approval.getStatus());

        approval.updateStatus(DocumentStatus.APPROVE, null);

        log.debug("approved vacation document. (id={})", holidayWorkReport.getId());

        return null;
    }

    @Override
    public Document reject(long approvalId, Document document, String reason) {
        return null;
    }

    @Override
    public Document canceled(Document document) {
        return null;
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
