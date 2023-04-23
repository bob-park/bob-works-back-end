package org.bobpark.client.domain.document.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.bobpark.client.domain.document.feign.DocumentClient;
import org.bobpark.client.domain.document.model.DocumentResponse;
import org.bobpark.client.domain.document.service.DocumentService;

@Slf4j
@RequiredArgsConstructor
@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentClient documentClient;

    @Override
    public Page<DocumentResponse> search() {
        return documentClient.search();
    }
}
