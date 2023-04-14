package org.bobpark.documentservice.domain.document.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.bobpark.documentservice.domain.document.entity.Document;
import org.bobpark.documentservice.domain.document.repository.query.DocumentQueryRepository;

public interface DocumentRepository extends JpaRepository<Document, Long>, DocumentQueryRepository {
}
