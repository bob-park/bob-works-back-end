package org.bobpark.documentservice.domain.document.controller.vacation;

import java.security.Principal;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.core.model.common.Id;
import org.bobpark.documentservice.domain.document.entity.Document;
import org.bobpark.documentservice.domain.document.entity.vacation.VacationDocument;
import org.bobpark.documentservice.domain.document.model.vacation.CreateVacationDocumentRequest;
import org.bobpark.documentservice.domain.document.model.vacation.SearchVacationDocumentRequest;
import org.bobpark.documentservice.domain.document.model.vacation.VacationDocumentResponse;
import org.bobpark.documentservice.domain.document.service.vacation.VacationDocumentService;

@RequiredArgsConstructor
@RestController
@RequestMapping("document/vacation")
public class VacationDocumentController {

    private final VacationDocumentService vacationDocumentService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "")
    public VacationDocumentResponse addDocument(Principal principal,
        @RequestBody CreateVacationDocumentRequest createRequest) {
        return vacationDocumentService.addDocument(principal, createRequest);
    }

    @GetMapping(path = "{documentId:\\d+}")
    public VacationDocumentResponse getVacation(@PathVariable long documentId) {
        return vacationDocumentService.getDocument(Id.of(Document.class, documentId));
    }

    @GetMapping(path = "search")
    public Page<VacationDocumentResponse> search(SearchVacationDocumentRequest searchRequest,
        @PageableDefault(size = 30) Pageable pageable) {
        return vacationDocumentService.search(searchRequest, pageable);
    }

}
