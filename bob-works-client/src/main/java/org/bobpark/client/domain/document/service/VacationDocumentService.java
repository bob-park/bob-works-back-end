package org.bobpark.client.domain.document.service;

import org.bobpark.client.domain.document.model.AddVacationDocumentRequest;
import org.bobpark.client.domain.document.model.DocumentResponse;

public interface VacationDocumentService {

    DocumentResponse addVacation(AddVacationDocumentRequest addRequest);

}
