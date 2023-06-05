package org.bobpark.client.domain.document.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.bobpark.client.common.page.Page;
import org.bobpark.client.domain.document.feign.DocumentClient;
import org.bobpark.client.domain.document.model.DocumentResponse;
import org.bobpark.client.domain.document.service.DocumentService;

@Slf4j
@RequiredArgsConstructor
@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentClient documentClient;

    @Override
    public Page<DocumentResponse> search(Pageable pageable) {
        return documentClient.search(pageable);
    }

    @Override
    public DocumentResponse cancel(long documentId) {
        return documentClient.cancel(documentId);
    }
}
