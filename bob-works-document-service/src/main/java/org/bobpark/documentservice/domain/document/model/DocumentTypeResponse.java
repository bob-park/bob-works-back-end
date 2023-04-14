package org.bobpark.documentservice.domain.document.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;

import org.bobpark.documentservice.domain.document.entity.DocumentType;
import org.bobpark.documentservice.domain.document.entity.DocumentTypeApprovalLine;
import org.bobpark.documentservice.domain.document.type.DocumentTypeName;
import org.bobpark.documentservice.domain.user.model.UserResponse;

@Builder
public record DocumentTypeResponse(
    Long id,
    DocumentTypeName type,
    String name,
    String description,
    LocalDateTime createdDate,
    String createdBy,
    LocalDateTime lastModifiedDate,
    String lastModifiedBy,
    DocumentTypeApprovalLineResponse approvalLine
) {

    public static DocumentTypeResponse toResponse(DocumentType documentType) {

        List<DocumentTypeApprovalLine> approveLines = documentType.getApprovalLines();

        DocumentTypeApprovalLine rootApproveLine =
            approveLines.stream()
                .filter(item -> item.getParent() == null)
                .findAny()
                .orElse(null);

        DocumentTypeApprovalLineResponse rootApprovalLine = null;

        if (rootApproveLine != null) {

            DocumentTypeApprovalLineResponse next = getNext(rootApproveLine, approveLines);

            rootApprovalLine =
                DocumentTypeApprovalLineResponse.builder()
                    .id(rootApproveLine.getId())
                    .user(UserResponse.toResponse(rootApproveLine.getUser()))
                    .next(next)
                    .build();

        }

        return DocumentTypeResponse.builder()
            .id(documentType.getId())
            .type(documentType.getType())
            .name(documentType.getName())
            .description(documentType.getDescription())
            .createdDate(documentType.getCreatedDate())
            .createdBy(documentType.getCreatedBy())
            .lastModifiedDate(documentType.getLastModifiedDate())
            .lastModifiedBy(documentType.getLastModifiedBy())
            .approvalLine(rootApprovalLine)
            .build();
    }

    private static DocumentTypeApprovalLineResponse getNext(
        DocumentTypeApprovalLine parent, List<DocumentTypeApprovalLine> approveLines) {

        List<DocumentTypeApprovalLine> children =
            approveLines.stream()
                .filter(item -> parent == item.getParent())
                .toList();

        return children.stream()
            .map(item -> {

                DocumentTypeApprovalLineResponse subNext = getNext(item, approveLines);

                return DocumentTypeApprovalLineResponse.builder()
                    .id(item.getId())
                    .user(UserResponse.toResponse(item.getUser()))
                    .next(subNext)
                    .build();
            })
            .findAny()
            .orElse(null);

    }
}
