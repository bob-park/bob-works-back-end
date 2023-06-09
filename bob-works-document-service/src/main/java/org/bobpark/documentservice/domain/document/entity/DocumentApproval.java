package org.bobpark.documentservice.domain.document.entity;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

import org.bobpark.documentservice.common.entity.BaseTimeEntity;
import org.bobpark.documentservice.domain.document.type.DocumentStatus;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "documents_approvals")
public class DocumentApproval extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private Document document;

    @Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "line_id")
    private DocumentTypeApprovalLine approvalLine;

    @Enumerated(EnumType.STRING)
    private DocumentStatus status;

    private LocalDateTime approvedDateTime;

    private String reason;

    @Builder
    private DocumentApproval(Long id, DocumentStatus status) {
        this.id = id;
        this.status = defaultIfNull(status, DocumentStatus.WAITING);
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public void setApprovalLine(DocumentTypeApprovalLine approvalLine) {
        this.approvalLine = approvalLine;
    }

    public void updateStatus(DocumentStatus status, String reason) {
        this.status = status;
        this.reason = reason;
        this.approvedDateTime = LocalDateTime.now();

        if (status == DocumentStatus.APPROVE) {

            DocumentTypeApprovalLine nextApprovalLine = approvalLine.getNext();
            if (nextApprovalLine != null) {

                DocumentApproval nextApproval =
                    DocumentApproval.builder()
                        .build();

                nextApproval.setDocument(getDocument());
                nextApproval.setApprovalLine(nextApprovalLine);

                getDocument().addApproval(nextApproval);
                return;
            }

        }

        getDocument().updateStatus(status);
    }
}
