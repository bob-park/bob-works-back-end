package org.bobpark.documentservice.domain.document.model;

import org.bobpark.documentservice.domain.document.type.DocumentTypeName;

public record CreateDocumentTypeRequest(
    DocumentTypeName type,
    String name,
    String description
) {
}
