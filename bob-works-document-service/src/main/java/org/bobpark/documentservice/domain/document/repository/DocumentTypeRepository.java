package org.bobpark.documentservice.domain.document.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.bobpark.documentservice.domain.document.entity.DocumentType;
import org.bobpark.documentservice.domain.document.repository.query.DocumentTypeQueryRepository;

public interface DocumentTypeRepository extends JpaRepository<DocumentType, Long>, DocumentTypeQueryRepository {
}
