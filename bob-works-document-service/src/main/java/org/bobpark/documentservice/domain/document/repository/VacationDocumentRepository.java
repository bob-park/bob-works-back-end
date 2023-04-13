package org.bobpark.documentservice.domain.document.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.bobpark.documentservice.domain.document.entity.vacation.VacationDocument;

public interface VacationDocumentRepository extends JpaRepository<VacationDocument, Long> {
}
