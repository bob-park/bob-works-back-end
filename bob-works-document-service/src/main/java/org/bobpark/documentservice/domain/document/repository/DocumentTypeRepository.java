package org.bobpark.documentservice.domain.document.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.bobpark.documentservice.domain.document.entity.DocumentType;

public interface DocumentTypeRepository extends JpaRepository<DocumentType, Long> {
}
