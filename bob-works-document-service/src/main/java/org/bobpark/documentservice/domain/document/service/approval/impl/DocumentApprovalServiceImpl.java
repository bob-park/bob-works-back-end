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
import org.bobpark.documentservice.domain.document.entity.DocumentApproval;
import org.bobpark.documentservice.domain.document.model.DocumentResponse;
import org.bobpark.documentservice.domain.document.model.approval.ApprovalDocumentRequest;
import org.bobpark.documentservice.domain.document.repository.approval.DocumentApprovalRepository;
import org.bobpark.documentservice.domain.document.service.approval.DocumentApprovalService;
import org.bobpark.documentservice.domain.document.type.DocumentStatus;
import org.bobpark.documentservice.domain.user.model.UserResponse;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class DocumentApprovalServiceImpl implements DocumentApprovalService {

    private final DocumentApprovalRepository documentApprovalRepository;

    @Transactional
    @Override
    public DocumentResponse approveDocument(Id<DocumentApproval, Long> approvalId,
        ApprovalDocumentRequest approvalRequest) {

        checkArgument(isNotEmpty(approvalRequest.status()), "status must be provided.");
        checkArgument(approvalRequest.status() != DocumentStatus.REJECT || hasText(approvalRequest.reason()),
            "reason must be provided.");

        DocumentApproval approval = getApproval(approvalId);

        approval.updateStatus(approvalRequest.status(), approvalRequest.reason());

        List<UserResponse> users = AuthenticationUtils.getInstance().getUsersByPrincipal();

        return toResponse(approval.getDocument(), users);
    }

    private DocumentApproval getApproval(Id<DocumentApproval, Long> approvalId) {
        return documentApprovalRepository.findById(approvalId.getValue())
            .orElseThrow(() -> new NotFoundException(approvalId));
    }

}
