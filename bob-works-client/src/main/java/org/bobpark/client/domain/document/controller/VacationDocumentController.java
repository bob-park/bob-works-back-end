package org.bobpark.client.domain.document.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.client.domain.document.model.AddVacationDocumentRequest;
import org.bobpark.client.domain.document.model.DocumentResponse;
import org.bobpark.client.domain.document.model.response.VacationDocumentDetailResponse;
import org.bobpark.client.domain.document.service.DocumentTypeService;
import org.bobpark.client.domain.document.service.VacationDocumentService;

@RequiredArgsConstructor
@RestController
@RequestMapping("document/vacation")
public class VacationDocumentController {

    private final VacationDocumentService vacationDocumentService;

    @PostMapping(path = "")
    public DocumentResponse addVacation(@RequestBody AddVacationDocumentRequest addRequest) {
        return vacationDocumentService.addVacation(addRequest);
    }

    @GetMapping(path = "{documentId}")
    public VacationDocumentDetailResponse getVacationDocument(@PathVariable long documentId) {
        return vacationDocumentService.getVacationDocument(documentId);
    }
}
