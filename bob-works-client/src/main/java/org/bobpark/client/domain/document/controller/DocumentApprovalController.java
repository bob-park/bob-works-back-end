package org.bobpark.client.domain.document.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.client.common.page.Page;
import org.bobpark.client.domain.document.model.DocumentApprovalResponse;
import org.bobpark.client.domain.document.model.SearchDocumentApprovalRequest;
import org.bobpark.client.domain.document.service.DocumentApprovalService;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "document/approval")
public class DocumentApprovalController {

    private final DocumentApprovalService documentApprovalService;

    @GetMapping(path = "search")
    public Page<DocumentApprovalResponse> search(SearchDocumentApprovalRequest searchRequest, Pageable pageable) {
        return documentApprovalService.search(searchRequest, pageable);
    }

    @GetMapping(path = "{approvalId}")
    public DocumentApprovalResponse getApproval(@PathVariable long approvalId){
        return documentApprovalService.getApproval(approvalId);
    }
}
