package org.bobpark.documentservice.domain.document.service.approval.impl;

import static com.google.common.base.Preconditions.*;
import static org.apache.commons.lang3.ObjectUtils.*;
import static org.bobpark.documentservice.domain.document.model.DocumentResponse.*;
import static org.springframework.util.StringUtils.*;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.core.model.common.Id;
import org.bobpark.documentservice.common.utils.authentication.AuthenticationUtils;
import org.bobpark.documentservice.domain.document.entity.Document;
import org.bobpark.documentservice.domain.document.entity.DocumentApproval;
import org.bobpark.documentservice.domain.document.entity.vacation.VacationDocument;
import org.bobpark.documentservice.domain.document.model.DocumentResponse;
import org.bobpark.documentservice.domain.document.model.approval.ApprovalDocumentRequest;
import org.bobpark.documentservice.domain.document.repository.VacationDocumentRepository;
import org.bobpark.documentservice.domain.document.repository.approval.DocumentApprovalRepository;
import org.bobpark.documentservice.domain.document.service.approval.DocumentApprovalService;
import org.bobpark.documentservice.domain.document.type.DocumentStatus;
import org.bobpark.documentservice.domain.document.type.VacationType;
import org.bobpark.documentservice.domain.user.feign.client.UserClient;
import org.bobpark.documentservice.domain.user.model.UserResponse;
import org.bobpark.documentservice.domain.user.model.vacation.UseUserVacationRequest;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class DocumentApprovalServiceImpl implements DocumentApprovalService {

    private final DocumentApprovalRepository documentApprovalRepository;
    private final VacationDocumentRepository vacationDocumentRepository;

    private final UserClient userClient;

    @Transactional
    @Override
    public DocumentResponse approveDocument(Id<DocumentApproval, Long> approvalId,
        ApprovalDocumentRequest approvalRequest) {

        checkArgument(isNotEmpty(approvalRequest.status()), "status must be provided.");
        checkArgument(approvalRequest.status() != DocumentStatus.REJECT || hasText(approvalRequest.reason()),
            "reason must be provided.");

        DocumentApproval approval = getApproval(approvalId);

        if (approval.getStatus() != DocumentStatus.WAITING
            && approval.getStatus() != DocumentStatus.PROCEEDING) {
            throw new IllegalStateException("Invalid status. (" + approval.getStatus() + ")");
        }

        approval.updateStatus(approvalRequest.status(), approvalRequest.reason());

        proceedLinkedUserProcess(approval.getDocument());

        List<UserResponse> users = AuthenticationUtils.getInstance().getUsersByPrincipal();

        return toResponse(approval.getDocument(), users);
    }

    private DocumentApproval getApproval(Id<DocumentApproval, Long> approvalId) {
        return documentApprovalRepository.findById(approvalId.getValue())
            .orElseThrow(() -> new NotFoundException(approvalId));
    }

    private void proceedLinkedUserProcess(Document document) {

        if (document.getStatus() == DocumentStatus.WAITING
            || document.getStatus() == DocumentStatus.PROCEEDING) {
            return;
        }

        switch (document.getType()) {

            case VACATION:
                // 휴가 결제 문서가 승인인 경우 사용자 휴가 처리
                proceedUserVacation(document.getId());
                break;

            default:
                break;
        }

    }

    private void proceedUserVacation(long documentId) {

        VacationDocument vacationDocument =
            vacationDocumentRepository.findById(documentId)
                .orElseThrow(() -> new NotFoundException(VacationDocument.class, documentId));

        if (vacationDocument.getStatus() == DocumentStatus.APPROVE) {
            useVacation(
                vacationDocument.getVacationType(),
                vacationDocument.getWriterId(),
                vacationDocument.getDaysCount());
        }

    }

    private void useVacation(VacationType type, long userId, double count) {
        userClient.useVacation(
            userId,
            UseUserVacationRequest.builder()
                .type(type)
                .useCount(count)
                .build());
    }

}
