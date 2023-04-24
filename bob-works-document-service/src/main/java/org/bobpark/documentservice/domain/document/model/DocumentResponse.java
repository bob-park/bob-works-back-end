package org.bobpark.documentservice.domain.document.model;

import static org.bobpark.documentservice.domain.user.utils.UserUtils.*;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;

import org.bobpark.documentservice.domain.document.entity.Document;
import org.bobpark.documentservice.domain.document.type.DocumentStatus;
import org.bobpark.documentservice.domain.document.type.DocumentTypeName;
import org.bobpark.documentservice.domain.user.model.UserResponse;

@Builder
public record DocumentResponse(Long id,
                               DocumentTypeResponse documentType,
                               UserResponse writer,
                               DocumentStatus status,
                               LocalDateTime createdDate,
                               String createdBy,
                               LocalDateTime lastModifiedDate,
                               String lastModifiedBy) {

    public static DocumentResponse toResponse(Document document, List<UserResponse> users) {
        return DocumentResponse.builder()
            .id(document.getId())
            .documentType(DocumentTypeResponse.toResponse(document.getDocumentType(), users))
            .writer(findByUser(users, document.getWriterId()))
            .status(document.getStatus())
            .createdDate(document.getCreatedDate())
            .createdBy(document.getCreatedBy())
            .lastModifiedDate(document.getLastModifiedDate())
            .lastModifiedBy(document.getLastModifiedBy())
            .build();
    }

}
