package org.bobpark.documentservice.domain.document.entity;

import static com.google.common.base.Preconditions.*;
import static org.apache.commons.lang3.ObjectUtils.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

import org.springframework.security.core.Authentication;

import org.bobpark.documentservice.common.entity.BaseEntity;
import org.bobpark.documentservice.common.utils.authentication.AuthenticationUtils;
import org.bobpark.documentservice.common.utils.notification.NotificationUtils;
import org.bobpark.documentservice.domain.document.type.DocumentStatus;
import org.bobpark.documentservice.domain.document.type.DocumentTypeName;
import org.bobpark.documentservice.domain.user.model.UserResponse;
import org.bobpark.documentservice.domain.user.utils.UserUtils;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "documents")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
public abstract class Document extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    @Enumerated(EnumType.STRING)
    private DocumentTypeName type;

    @Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private DocumentType documentType;

    private Long writerId;

    @Enumerated(EnumType.STRING)
    private DocumentStatus status;

    @Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocumentApproval> approvals = new ArrayList<>();

    protected Document(Long id, DocumentTypeName type, DocumentType documentType, Long writerId, Long teamId,
        DocumentStatus status) {

        checkArgument(isNotEmpty(type), "type must be provided.");
        checkArgument(isNotEmpty(documentType), "documentType must be provided.");
        checkArgument(isNotEmpty(writerId), "writer must be provided.");
        checkArgument(isNotEmpty(teamId), "teamId must be provided.");


        this.id = id;
        this.type = type;
        this.documentType = documentType;
        this.writerId = writerId;
        this.status = defaultIfNull(status, DocumentStatus.PROCEEDING);

        // 문서의 처음 결제 작업 생성
        DocumentApproval approval =
            DocumentApproval.builder()
                .build();

        DocumentTypeApprovalLine approvalLine =
            documentType.getApprovalLines().stream()
                .filter(item -> item.getTeamId().equals(teamId))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("approvalLines must not empty"));

        approval.setDocument(this);
        approval.setApprovalLine(approvalLine);

        addApproval(approval);
    }

    public void addApproval(DocumentApproval approval) {
        getApprovals().add(approval);

        // send user notification
        sendMessageApprovalUser(approval);
    }

    public void updateStatus(DocumentStatus status) {
        this.status = status;
    }

    private void sendMessageApprovalUser(DocumentApproval approval) {

        DocumentTypeApprovalLine approvalLine = approval.getApprovalLine();
        Authentication auth = AuthenticationUtils.getInstance().getAuthentication();
        UserResponse user = AuthenticationUtils.getInstance().getUser(auth.getName());

        String message =
            String.format(
                "%s(%s - %s) 이(가) %s 을(를) 결재 신청하였습니다.",
                user.name(),
                user.team().name(),
                user.position().name(),
                getDocumentType().getName());

        NotificationUtils.getInstance().sendMessage(approvalLine.getUserId(), message);

    }

}
