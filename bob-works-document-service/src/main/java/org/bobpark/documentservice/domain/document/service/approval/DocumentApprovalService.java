package org.bobpark.documentservice.domain.document.service.approval;

import org.bobpark.core.model.common.Id;
import org.bobpark.documentservice.domain.document.entity.DocumentApproval;
import org.bobpark.documentservice.domain.document.model.DocumentResponse;
import org.bobpark.documentservice.domain.document.model.approval.ApprovalDocumentRequest;

public interface DocumentApprovalService {

    DocumentResponse approveDocument(Id<DocumentApproval, Long> approvalId, ApprovalDocumentRequest approvalRequest);


}
