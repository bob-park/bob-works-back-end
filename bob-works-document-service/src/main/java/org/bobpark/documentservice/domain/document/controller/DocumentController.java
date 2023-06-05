package org.bobpark.documentservice.domain.document.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.core.model.common.Id;
import org.bobpark.documentservice.domain.document.entity.Document;
import org.bobpark.documentservice.domain.document.model.DocumentResponse;
import org.bobpark.documentservice.domain.document.model.SearchDocumentRequest;
import org.bobpark.documentservice.domain.document.service.DocumentService;

@RequiredArgsConstructor
@RestController
@RequestMapping("document")
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping(path = "search")
    public Page<DocumentResponse> search(SearchDocumentRequest searchRequest,
        @PageableDefault(sort = "createdDate", direction = Direction.DESC) Pageable pageable) {
        return documentService.search(searchRequest, pageable);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "{documentId:\\d+}/cancel")
    public DocumentResponse cancel(@PathVariable long documentId) {
        return documentService.cancel(Id.of(Document.class, documentId));
    }

}
