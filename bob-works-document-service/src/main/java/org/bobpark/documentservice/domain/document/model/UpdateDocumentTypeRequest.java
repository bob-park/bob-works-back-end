package org.bobpark.documentservice.domain.document.model;

import lombok.Builder;

@Builder
public record UpdateDocumentTypeRequest(String name) {
}
