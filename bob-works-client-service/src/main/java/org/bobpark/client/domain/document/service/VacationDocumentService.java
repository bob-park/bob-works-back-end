package org.bobpark.client.domain.document.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import org.bobpark.client.common.page.Page;
import org.bobpark.client.domain.document.model.AddVacationDocumentRequest;
import org.bobpark.client.domain.document.model.DocumentResponse;
import org.bobpark.client.domain.document.model.SearchVacationDocumentRequest;
import org.bobpark.client.domain.document.model.UsageVacationResponse;
import org.bobpark.client.domain.document.model.VacationDocumentResponse;
import org.bobpark.client.domain.document.model.response.VacationDocumentDetailResponse;

public interface VacationDocumentService {

    DocumentResponse addVacation(AddVacationDocumentRequest addRequest);

    VacationDocumentDetailResponse getVacationDocument(long documentId);

    Page<VacationDocumentResponse> search(SearchVacationDocumentRequest searchRequest, Pageable pageable);

    List<UsageVacationResponse> usage();

}
