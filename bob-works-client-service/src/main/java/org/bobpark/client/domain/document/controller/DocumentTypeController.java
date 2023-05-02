package org.bobpark.client.domain.document.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.client.domain.document.model.DocumentTypeResponse;
import org.bobpark.client.domain.document.service.DocumentTypeService;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "document/type")
public class DocumentTypeController {

    private final DocumentTypeService documentTypeService;

    @GetMapping(path = "search")
    public List<DocumentTypeResponse> search() {
        return documentTypeService.search();
    }
}
