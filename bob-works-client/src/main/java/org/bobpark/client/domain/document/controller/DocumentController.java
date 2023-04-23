package org.bobpark.client.domain.document.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.client.domain.document.model.DocumentResponse;
import org.bobpark.client.domain.document.service.DocumentService;

@RequiredArgsConstructor
@RestController
@RequestMapping("document")
public class DocumentController {

    private  final DocumentService documentService;

    @GetMapping(path = "search")
    public Page<DocumentResponse> search(){
        return documentService.search();
    }
}
