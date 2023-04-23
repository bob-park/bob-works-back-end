package org.bobpark.client.domain.document.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import org.bobpark.client.domain.document.feign.DocumentClient;
import org.bobpark.client.domain.document.model.AddVacationDocumentRequest;
import org.bobpark.client.domain.document.model.DocumentResponse;
import org.bobpark.client.domain.document.service.VacationDocumentService;

@Slf4j
@RequiredArgsConstructor
@Service
public class VacationDocumentServiceImpl implements VacationDocumentService {

    private final DocumentClient documentClient;

    @Override
    public DocumentResponse addVacation(AddVacationDocumentRequest addRequest) {
        return documentClient.addVacation(addRequest);
    }
}
