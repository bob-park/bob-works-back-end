package org.bobpark.documentservice.domain.document.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.bobpark.core.model.common.Id;
import org.bobpark.documentservice.domain.document.entity.Document;
import org.bobpark.documentservice.domain.document.model.DocumentResponse;
import org.bobpark.documentservice.domain.document.model.SearchDocumentRequest;

public interface DocumentService {

    Page<DocumentResponse> search(SearchDocumentRequest searchRequest, Pageable pageable);

    DocumentResponse cancel(Id<Document, Long> documentId);

}
