package org.bobpark.documentservice.domain.document.controller.approval;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.core.model.common.Id;
import org.bobpark.documentservice.domain.document.entity.DocumentApproval;
import org.bobpark.documentservice.domain.document.model.DocumentResponse;
import org.bobpark.documentservice.domain.document.model.approval.ApprovalDocumentRequest;
import org.bobpark.documentservice.domain.document.service.approval.DocumentApprovalService;

@RequiredArgsConstructor
@RestController
@RequestMapping("document/approval")
public class DocumentApprovalController {

    private final DocumentApprovalService documentApprovalService;

    @PutMapping(path = "{approvalId}")
    public DocumentResponse approvalDocument(@PathVariable long approvalId,
        @RequestBody ApprovalDocumentRequest approvalRequest) {
        return documentApprovalService.approveDocument(Id.of(DocumentApproval.class, approvalId), approvalRequest);
    }

}
