package org.bobpark.documentservice.domain.document.repository.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.bobpark.documentservice.domain.document.entity.Document;
import org.bobpark.documentservice.domain.document.model.SearchDocumentRequest;

public interface DocumentQueryRepository {

    Page<Document> search(SearchDocumentRequest searchRequest, Pageable pageable);

}
