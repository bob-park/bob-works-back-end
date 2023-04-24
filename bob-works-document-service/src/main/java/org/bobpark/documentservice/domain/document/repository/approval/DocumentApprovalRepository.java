package org.bobpark.documentservice.domain.document.repository.approval;

import org.springframework.data.jpa.repository.JpaRepository;

import org.bobpark.documentservice.domain.document.entity.DocumentApproval;
import org.bobpark.documentservice.domain.document.repository.approval.query.DocumentApprovalQueryRepository;

public interface DocumentApprovalRepository extends JpaRepository<DocumentApproval, Long>,
    DocumentApprovalQueryRepository {
}
