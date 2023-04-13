package org.bobpark.documentservice.domain.document.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.bobpark.documentservice.domain.document.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
