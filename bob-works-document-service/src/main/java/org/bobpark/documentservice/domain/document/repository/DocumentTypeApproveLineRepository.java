package org.bobpark.documentservice.domain.document.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.bobpark.documentservice.domain.document.entity.DocumentTypeApproveLine;

public interface DocumentTypeApproveLineRepository extends JpaRepository<DocumentTypeApproveLine, Long> {
}
