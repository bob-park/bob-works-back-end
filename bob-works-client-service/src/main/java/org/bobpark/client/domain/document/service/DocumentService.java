package org.bobpark.client.domain.document.service;

import org.springframework.data.domain.Pageable;

import org.bobpark.client.common.page.Page;
import org.bobpark.client.domain.document.model.DocumentResponse;

public interface DocumentService {

    Page<DocumentResponse> search(Pageable pageable);

    DocumentResponse cancel(long documentId);
}
