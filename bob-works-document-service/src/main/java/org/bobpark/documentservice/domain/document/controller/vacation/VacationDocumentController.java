package org.bobpark.documentservice.domain.document.controller.vacation;

import java.security.Principal;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.documentservice.domain.document.model.vacation.CreateVacationDocumentRequest;
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

}
