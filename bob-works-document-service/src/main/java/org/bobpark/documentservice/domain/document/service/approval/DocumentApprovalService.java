package org.bobpark.documentservice.domain.document.service.approval;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.bobpark.core.model.common.Id;
import org.bobpark.documentservice.domain.document.entity.DocumentApproval;
import org.bobpark.documentservice.domain.document.model.DocumentResponse;
import org.bobpark.documentservice.domain.document.model.approval.ApprovalDocumentRequest;
import org.bobpark.documentservice.domain.document.model.approval.DocumentApprovalResponse;
import org.bobpark.documentservice.domain.document.model.approval.SearchDocumentApprovalRequest;

public interface DocumentApprovalService {

    DocumentResponse approveDocument(Id<DocumentApproval, Long> approvalId, ApprovalDocumentRequest approvalRequest);

    DocumentApprovalResponse getApproval(Id<DocumentApproval, Long> approvalId);


    Page<DocumentApprovalResponse> search(SearchDocumentApprovalRequest searchRequest, Pageable pageable);


}
