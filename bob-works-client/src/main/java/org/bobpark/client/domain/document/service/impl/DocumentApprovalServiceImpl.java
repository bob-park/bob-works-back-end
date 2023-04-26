package org.bobpark.client.domain.document.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.bobpark.client.common.page.Page;
import org.bobpark.client.domain.document.feign.DocumentApprovalClient;
import org.bobpark.client.domain.document.model.DocumentApprovalResponse;
import org.bobpark.client.domain.document.model.SearchDocumentApprovalRequest;
import org.bobpark.client.domain.document.service.DocumentApprovalService;

@Slf4j
@RequiredArgsConstructor
@Service
public class DocumentApprovalServiceImpl implements DocumentApprovalService {

    private final DocumentApprovalClient documentApprovalClient;

    @Override
    public Page<DocumentApprovalResponse> search(SearchDocumentApprovalRequest searchRequest, Pageable pageable) {
        return documentApprovalClient.search(searchRequest, pageable);
    }

    @Override
    public DocumentApprovalResponse getApproval(long approvalId) {
        return documentApprovalClient.getApproval(approvalId);
    }
}
