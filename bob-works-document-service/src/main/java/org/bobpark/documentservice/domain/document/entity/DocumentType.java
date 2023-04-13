package org.bobpark.documentservice.domain.document.entity;

import static com.google.common.base.Preconditions.*;
import static org.apache.commons.lang3.ObjectUtils.*;
import static org.springframework.util.StringUtils.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

import org.bobpark.documentservice.common.entity.BaseEntity;
import org.bobpark.documentservice.domain.document.type.DocumentTypeName;
import org.bobpark.documentservice.domain.user.entity.User;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "document_types")
public class DocumentType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DocumentTypeName type;

    private String name;
    private String description;

    @Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "documentType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocumentTypeApprovalLine> approvalLines = new ArrayList<>();

    @Builder
    private DocumentType(Long id, DocumentTypeName type, String name, String description) {

        checkArgument(isNotEmpty(type), "type must be provided.");
        checkArgument(hasText(name), "name must be provided.");

        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
    }

    public void updateName(String name) {

        checkArgument(hasText(name), "name must be provided.");

        this.name = name;
    }

    public void addApproveLine(DocumentTypeApprovalLine parent, User user) {

        DocumentTypeApprovalLine createApproveLine = new DocumentTypeApprovalLine();

        if (parent != null) {
            parent.addChild(createApproveLine);
        }

        createApproveLine.setDocumentType(this);
        createApproveLine.setUser(user);

        getApprovalLines().add(createApproveLine);

    }
}
