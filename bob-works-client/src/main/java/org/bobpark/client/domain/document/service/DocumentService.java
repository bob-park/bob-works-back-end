package org.bobpark.client.domain.document.service;

import org.springframework.data.domain.Page;

import org.bobpark.client.domain.document.model.DocumentResponse;

public interface DocumentService {

    Page<DocumentResponse> search();
}
