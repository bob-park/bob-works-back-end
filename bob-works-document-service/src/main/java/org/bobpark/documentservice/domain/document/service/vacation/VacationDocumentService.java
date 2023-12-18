package org.bobpark.documentservice.domain.document.service.vacation;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.bobpark.core.model.common.Id;
import org.bobpark.documentservice.domain.document.entity.Document;
import org.bobpark.documentservice.domain.document.model.vacation.CreateVacationDocumentRequest;
import org.bobpark.documentservice.domain.document.model.vacation.SearchVacationDocumentRequest;
import org.bobpark.documentservice.domain.document.model.vacation.VacationDocumentResponse;

public interface VacationDocumentService {

    VacationDocumentResponse addDocument(Principal principal, CreateVacationDocumentRequest createRequest);

    VacationDocumentResponse getDocument(Id<? extends Document, Long> documentId);

    Page<VacationDocumentResponse> search(SearchVacationDocumentRequest searchRequest, Pageable pageable);

}
