package org.bobpark.client.domain.document.service.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.bobpark.client.common.page.Page;
import org.bobpark.client.domain.document.feign.DocumentApprovalClient;
import org.bobpark.client.domain.document.model.ApprovalDocumentRequest;
import org.bobpark.client.domain.document.model.DocumentApprovalResponse;
import org.bobpark.client.domain.document.model.DocumentResponse;
import org.bobpark.client.domain.document.model.SearchDocumentApprovalRequest;
import org.bobpark.client.domain.document.service.DocumentApprovalService;
import org.bobpark.client.domain.user.feign.UserClient;
import org.bobpark.client.domain.user.model.SearchUserRequest;
import org.bobpark.client.domain.user.model.UserResponse;

@Slf4j
@RequiredArgsConstructor
@Service
public class DocumentApprovalServiceImpl implements DocumentApprovalService {

    private final DocumentApprovalClient documentApprovalClient;
    private final UserClient userClient;

    @Override
    public Page<DocumentApprovalResponse> search(SearchDocumentApprovalRequest searchRequest, Pageable pageable) {

        Page<DocumentApprovalResponse> result = documentApprovalClient.search(searchRequest, pageable);

        List<DocumentApprovalResponse> content = result.content();

        List<Long> writerIds =
            content.stream()
                .map(item -> item.document().writerId())
                .toList();

        List<UserResponse> writers =
            userClient.searchUsers(
                SearchUserRequest.builder()
                    .ids(writerIds)
                    .build());

        return result.toBuilder()
            .content(content.stream().map(item -> {
                    UserResponse writer =
                        writers.stream().filter(w -> w.id().equals(item.document().writerId()))
                            .findAny()
                            .orElse(null);

                    return item.toBuilder()
                        .document(
                            item.document().toBuilder()
                                .writer(writer)
                                .build())
                        .status(item.document().status().equals("CANCEL") ? item.document().status() : item.status())
                        .build();
                })
                .toList())
            .build();
    }

    @Override
    public DocumentApprovalResponse getApproval(long approvalId) {

        DocumentApprovalResponse result = documentApprovalClient.getApproval(approvalId);

        UserResponse writer = userClient.getUserById(result.document().writerId());

        return result.toBuilder()
            .document(
                result.document().toBuilder()
                    .writer(writer)
                    .build())
            .status(result.document().status().equals("CANCEL") ? result.document().status() : result.status())
            .build();
    }

    @Override
    public DocumentResponse approve(long approvalId, ApprovalDocumentRequest approvalRequest) {
        return documentApprovalClient.approve(approvalId, approvalRequest);
    }

}
