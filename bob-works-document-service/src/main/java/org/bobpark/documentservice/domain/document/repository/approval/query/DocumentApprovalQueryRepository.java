package org.bobpark.documentservice.domain.document.repository.approval.query;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.bobpark.core.model.common.Id;
import org.bobpark.documentservice.domain.document.entity.Document;
import org.bobpark.documentservice.domain.document.entity.DocumentApproval;
import org.bobpark.documentservice.domain.document.model.approval.SearchDocumentApprovalRequest;

public interface DocumentApprovalQueryRepository {

    Optional<DocumentApproval> findById(Id<DocumentApproval, Long> approvalId);

    Page<DocumentApproval> search(SearchDocumentApprovalRequest searchRequest, Pageable pageable);

    List<DocumentApproval> getAllApprovals(Id<? extends Document, Long> documentId);
}
