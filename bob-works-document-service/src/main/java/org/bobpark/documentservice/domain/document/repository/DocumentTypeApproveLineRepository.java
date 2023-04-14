package org.bobpark.documentservice.domain.document.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.bobpark.documentservice.domain.document.entity.DocumentTypeApprovalLine;

public interface DocumentTypeApproveLineRepository extends JpaRepository<DocumentTypeApprovalLine, Long> {
}
