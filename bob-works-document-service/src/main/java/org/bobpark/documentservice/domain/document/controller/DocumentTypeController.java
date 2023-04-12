package org.bobpark.documentservice.domain.document.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.core.model.common.Id;
import org.bobpark.documentservice.domain.document.entity.DocumentType;
import org.bobpark.documentservice.domain.document.model.CreateDocumentTypeRequest;
import org.bobpark.documentservice.domain.document.model.DocumentTypeResponse;
import org.bobpark.documentservice.domain.document.model.SearchDocumentTypeRequest;
import org.bobpark.documentservice.domain.document.service.DocumentTypeService;

@RequiredArgsConstructor
@RestController
@RequestMapping("document/type")
@PreAuthorize("hasRole('MANAGER')")
public class DocumentTypeController {

    private final DocumentTypeService documentTypeService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "")
    public DocumentTypeResponse createType(@RequestBody CreateDocumentTypeRequest createRequest) {
        return documentTypeService.createDocumentType(createRequest);
    }

    @GetMapping(path = "{typeId:\\d+}")
    public DocumentTypeResponse getType(@PathVariable long typeId) {
        return documentTypeService.getDocumentType(Id.of(DocumentType.class, typeId));
    }

    @GetMapping(path = "search")
    public List<DocumentTypeResponse> search(SearchDocumentTypeRequest searchRequest) {
        return documentTypeService.search(searchRequest);
    }

}
