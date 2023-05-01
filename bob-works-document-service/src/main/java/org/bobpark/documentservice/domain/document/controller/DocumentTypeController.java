package org.bobpark.documentservice.domain.document.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.core.model.common.Id;
import org.bobpark.documentservice.domain.document.entity.DocumentType;
import org.bobpark.documentservice.domain.document.model.CreateDocumentTypeApprovalLineRequest;
import org.bobpark.documentservice.domain.document.model.CreateDocumentTypeRequest;
import org.bobpark.documentservice.domain.document.model.DocumentTypeResponse;
import org.bobpark.documentservice.domain.document.model.SearchDocumentTypeRequest;
import org.bobpark.documentservice.domain.document.model.UpdateDocumentTypeRequest;
import org.bobpark.documentservice.domain.document.service.DocumentTypeApproveLineService;
import org.bobpark.documentservice.domain.document.service.DocumentTypeService;

@RequiredArgsConstructor
@RestController
@RequestMapping("document/type")
// @PreAuthorize("hasRole('MANAGER')")
public class DocumentTypeController {

    private final DocumentTypeService documentTypeService;
    private final DocumentTypeApproveLineService documentTypeApproveLineService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "")
    public DocumentTypeResponse createType(@RequestBody CreateDocumentTypeRequest createRequest) {
        return documentTypeService.createDocumentType(createRequest);
    }

    @GetMapping(path = "{typeId:\\d+}")
    public DocumentTypeResponse getType(@PathVariable long typeId) {
        return documentTypeService.getDocumentType(Id.of(DocumentType.class, typeId));
    }

    @PutMapping(path = "{typeId:\\d+}")
    public DocumentTypeResponse updateType(@PathVariable long typeId,
        @RequestBody UpdateDocumentTypeRequest updateRequest) {
        return documentTypeService.updateDocumentType(Id.of(DocumentType.class, typeId), updateRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "{typeId:\\d+}")
    public DocumentTypeResponse deleteType(@PathVariable long typeId) {
        return documentTypeService.deleteDocumentType(Id.of(DocumentType.class, typeId));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "search")
    public List<DocumentTypeResponse> search(SearchDocumentTypeRequest searchRequest) {
        return documentTypeService.search(searchRequest);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "{typeId:\\d+}/approve/line")
    public DocumentTypeResponse addApproveLine(
        @PathVariable long typeId,
        @RequestBody CreateDocumentTypeApprovalLineRequest createRequest) {

        return documentTypeApproveLineService.addApproveLine(Id.of(DocumentType.class, typeId), createRequest);
    }

}
