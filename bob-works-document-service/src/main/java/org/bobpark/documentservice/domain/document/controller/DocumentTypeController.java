package org.bobpark.documentservice.domain.document.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.documentservice.domain.document.model.CreateDocumentTypeRequest;
import org.bobpark.documentservice.domain.document.model.DocumentTypeResponse;
import org.bobpark.documentservice.domain.document.service.DocumentTypeService;

@RequiredArgsConstructor
@RestController
@RequestMapping("document/type")
public class DocumentTypeController {

    private final DocumentTypeService documentTypeService;

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping(path = "")
    public DocumentTypeResponse createType(@RequestBody CreateDocumentTypeRequest createRequest) {
        return documentTypeService.createDocumentType(createRequest);
    }

}
