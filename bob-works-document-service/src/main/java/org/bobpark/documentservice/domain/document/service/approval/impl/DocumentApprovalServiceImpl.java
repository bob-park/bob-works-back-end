package org.bobpark.documentservice.domain.document.service.approval.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.bobpark.documentservice.domain.document.model.DocumentResponse.toResponse;
import static org.springframework.util.StringUtils.hasText;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.core.model.common.Id;
import org.bobpark.documentservice.common.utils.authentication.AuthenticationUtils;
import org.bobpark.documentservice.domain.document.entity.Document;
import org.bobpark.documentservice.domain.document.entity.DocumentApproval;
import org.bobpark.documentservice.domain.document.listener.DocumentListener;
import org.bobpark.documentservice.domain.document.model.DocumentResponse;
import org.bobpark.documentservice.domain.document.model.approval.ApprovalDocumentRequest;
import org.bobpark.documentservice.domain.document.model.approval.DocumentApprovalResponse;
import org.bobpark.documentservice.domain.document.model.approval.SearchDocumentApprovalRequest;
import org.bobpark.documentservice.domain.document.repository.approval.DocumentApprovalRepository;
import org.bobpark.documentservice.domain.document.service.approval.DocumentApprovalService;
import org.bobpark.documentservice.domain.document.type.DocumentStatus;
import org.bobpark.documentservice.domain.user.feign.client.UserClient;
import org.bobpark.documentservice.domain.user.model.UserResponse;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class DocumentApprovalServiceImpl implements DocumentApprovalService {

    private final DocumentListener documentListener;
    private final DocumentApprovalRepository documentApprovalRepository;

    private final UserClient userClient;

    @Transactional
    @Override
    public DocumentResponse approveDocument(Id<DocumentApproval, Long> approvalId,
        ApprovalDocumentRequest approvalRequest) {

        checkArgument(isNotEmpty(approvalRequest.status()), "status must be provided.");
        checkArgument(approvalRequest.status() != DocumentStatus.REJECT || hasText(approvalRequest.reason()),
            "reason must be provided.");

        DocumentApproval approval = getApprovalById(approvalId);

        Document document = approval.getDocument();

        switch (approvalRequest.status()) {
            case APPROVE -> documentListener.approval(approvalId.getValue(), document);
            case REJECT -> documentListener.reject(approvalId.getValue(), document, approvalRequest.reason());
            default -> throw new IllegalArgumentException("Bad request document status.");
        }

        List<UserResponse> users = AuthenticationUtils.getInstance().getUsersByPrincipal();

        return toResponse(approval.getDocument(), users);
    }

    @Override
    public DocumentApprovalResponse getApproval(Id<DocumentApproval, Long> approvalId) {

        DocumentApproval approval = getApprovalById(approvalId);

        List<UserResponse> users = AuthenticationUtils.getInstance().getUsersByPrincipal();

        return DocumentApprovalResponse.builder()
            .id(approval.getId())
            .document(DocumentResponse.toResponse(approval.getDocument(), users))
            .status(approval.getStatus())
            .approvedDateTime(approval.getApprovedDateTime())
            .reason(approval.getReason())
            .build();
    }

    @Override
    public Page<DocumentApprovalResponse> search(SearchDocumentApprovalRequest searchRequest, Pageable pageable) {

        Authentication authentication = AuthenticationUtils.getInstance().getAuthentication();
        List<UserResponse> users = getUserAll();
        UserResponse me = findUser(authentication.getName(), users);

        SearchDocumentApprovalRequest searchDto =
            SearchDocumentApprovalRequest.withoutApprovalLineUserId(searchRequest)
                .approvalLineUserId(me.id())
                .build();

        Page<DocumentApproval> result = documentApprovalRepository.search(searchDto, pageable);

        return result.map(item ->
            DocumentApprovalResponse.builder()
                .id(item.getId())
                .document(DocumentResponse.toResponse(item.getDocument(), users))
                .status(item.getStatus())
                .approvedDateTime(item.getApprovedDateTime())
                .reason(item.getReason())
                .build());
    }

    @Override
    public List<DocumentApprovalResponse> getAllApprovals(Id<? extends Document, Long> documentId) {

        List<DocumentApproval> result = documentApprovalRepository.getAllApprovals(documentId);

        return result.stream()
            .map(DocumentApprovalResponse::toResponse)
            .toList();
    }

    private DocumentApproval getApprovalById(Id<DocumentApproval, Long> approvalId) {
        return documentApprovalRepository.findById(approvalId)
            .orElseThrow(() -> new NotFoundException(approvalId));
    }

    private List<UserResponse> getUserAll() {
        return userClient.getUserAll();
    }

    private UserResponse findUser(String userId, List<UserResponse> users) {
        return users.stream().filter(user -> user.userId().equals(userId))
            .findAny()
            .orElseThrow(() -> new NotFoundException(UserResponse.class, userId));
    }

}
