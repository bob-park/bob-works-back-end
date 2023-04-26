package org.bobpark.client.domain.document.service;

import org.bobpark.client.domain.document.model.AddVacationDocumentRequest;
import org.bobpark.client.domain.document.model.DocumentResponse;
import org.bobpark.client.domain.document.model.VacationDocumentResponse;

public interface VacationDocumentService {

    DocumentResponse addVacation(AddVacationDocumentRequest addRequest);

    VacationDocumentResponse getVacationDocument(long documentId);

}
