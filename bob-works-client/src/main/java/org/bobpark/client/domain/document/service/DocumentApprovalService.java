package org.bobpark.client.domain.document.service;

import org.springframework.data.domain.Pageable;

import org.bobpark.client.common.page.Page;
import org.bobpark.client.domain.document.model.ApprovalDocumentRequest;
import org.bobpark.client.domain.document.model.DocumentApprovalResponse;
import org.bobpark.client.domain.document.model.DocumentResponse;
import org.bobpark.client.domain.document.model.SearchDocumentApprovalRequest;

public interface DocumentApprovalService {

    Page<DocumentApprovalResponse> search(SearchDocumentApprovalRequest searchRequest, Pageable pageable);

    DocumentApprovalResponse getApproval(long approvalId);

    DocumentResponse approve(long approvalId, ApprovalDocumentRequest approvalRequest);
}
