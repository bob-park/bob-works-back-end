package org.bobpark.documentservice.domain.document.repository.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.bobpark.documentservice.domain.document.entity.vacation.VacationDocument;
import org.bobpark.documentservice.domain.document.model.vacation.SearchVacationDocumentRequest;

public interface VacationDocumentQueryRepository {

    Page<VacationDocument> search(Long writerId, SearchVacationDocumentRequest searchRequest, Pageable pageable);

}
