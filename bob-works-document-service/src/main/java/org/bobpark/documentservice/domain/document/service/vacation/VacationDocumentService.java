package org.bobpark.documentservice.domain.document.service.vacation;

import java.security.Principal;

import org.bobpark.core.model.common.Id;
import org.bobpark.documentservice.domain.document.entity.Document;
import org.bobpark.documentservice.domain.document.model.vacation.CreateVacationDocumentRequest;
import org.bobpark.documentservice.domain.document.model.vacation.VacationDocumentResponse;

public interface VacationDocumentService {

    VacationDocumentResponse addDocument(Principal principal, CreateVacationDocumentRequest createRequest);

    VacationDocumentResponse getDocument(Id<? extends Document, Long> documentId);

}
