package org.bobpark.documentservice.domain.document.model;

import java.time.LocalDate;

import lombok.Builder;

import org.bobpark.documentservice.domain.document.type.DocumentStatus;
import org.bobpark.documentservice.domain.document.type.DocumentTypeName;

@Builder
public record SearchDocumentRequest(
    DocumentTypeName typeName,
    Long typeId,
    String writer,
    DocumentStatus status,
    LocalDate createdDateFrom,
    LocalDate createdDateTo
) {
}
