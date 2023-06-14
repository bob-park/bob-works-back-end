package org.bobpark.client.domain.document.service;

import org.springframework.data.domain.Pageable;

import org.bobpark.client.common.page.Page;
import org.bobpark.client.domain.document.model.DocumentResponse;
import org.bobpark.client.domain.document.model.SearchDocumentResponse;

public interface DocumentService {

    Page<SearchDocumentResponse> search(Pageable pageable);

    DocumentResponse cancel(long documentId);
}
