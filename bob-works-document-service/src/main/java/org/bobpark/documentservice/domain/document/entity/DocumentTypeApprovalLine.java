package org.bobpark.documentservice.domain.document.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "document_types_approval_lines")
public class DocumentTypeApprovalLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p_id")
    private DocumentTypeApprovalLine parent;

    @Exclude
    @OneToOne(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private DocumentTypeApprovalLine next;

    @Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_type_id")
    private DocumentType documentType;

    private Long userId;

    @Builder
    private DocumentTypeApprovalLine(Long id, DocumentType documentType, Long userId) {
        this.id = id;
        this.documentType = documentType;
        this.userId = userId;
    }

    public void setParent(DocumentTypeApprovalLine parent) {
        this.parent = parent;
    }

    public void addChild(DocumentTypeApprovalLine child) {

        child.setParent(this);
        this.next = child;
    }
}
