package org.bobpark.documentservice.domain.document.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;

import org.bobpark.documentservice.domain.document.entity.DocumentType;
import org.bobpark.documentservice.domain.document.entity.DocumentTypeApproveLine;
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
    DocumentTypeApproveLineResponse approveLine
) {

    public static DocumentTypeResponse toResponse(DocumentType documentType) {

        List<DocumentTypeApproveLine> approveLines = documentType.getApproveLines();

        DocumentTypeApproveLine rootApproveLine =
            approveLines.stream()
                .filter(item -> item.getParent() == null)
                .findAny()
                .orElse(null);

        DocumentTypeApproveLineResponse rootApproveLineResponse = null;

        if (rootApproveLine != null) {

            List<DocumentTypeApproveLineResponse> children = getChildren(rootApproveLine, approveLines);

            rootApproveLineResponse =
                DocumentTypeApproveLineResponse.builder()
                    .id(rootApproveLine.getId())
                    .user(UserResponse.toResponse(rootApproveLine.getUser()))
                    .children(children)
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
            .approveLine(rootApproveLineResponse)
            .build();
    }

    private static List<DocumentTypeApproveLineResponse> getChildren(
        DocumentTypeApproveLine parent, List<DocumentTypeApproveLine> approveLines) {

        List<DocumentTypeApproveLine> children =
            approveLines.stream()
                .filter(item -> parent == item.getParent())
                .toList();

        return children.stream()
            .map(item -> {

                List<DocumentTypeApproveLineResponse> subChildren = getChildren(item, approveLines);

                return DocumentTypeApproveLineResponse.builder()
                    .id(item.getId())
                    .user(UserResponse.toResponse(item.getUser()))
                    .children(subChildren)
                    .build();
            })
            .toList();
    }
}
