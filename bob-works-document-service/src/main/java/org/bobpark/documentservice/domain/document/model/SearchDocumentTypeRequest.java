package org.bobpark.documentservice.domain.document.model;

import lombok.Builder;

import org.bobpark.documentservice.domain.document.type.DocumentTypeName;

@Builder
public record SearchDocumentTypeRequest(DocumentTypeName type,
                                        String name) {
}
