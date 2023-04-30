package org.bobpark.client.domain.document.service.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import org.bobpark.client.domain.document.feign.DocumentTypeClient;
import org.bobpark.client.domain.document.model.DocumentTypeResponse;
import org.bobpark.client.domain.document.service.DocumentTypeService;

@Slf4j
@RequiredArgsConstructor
@Service
public class DocumentTypeServiceImpl implements DocumentTypeService {

    private final DocumentTypeClient documentTypeClient;

    @Override
    public List<DocumentTypeResponse> search() {
        return documentTypeClient.search();
    }

    @Override
    public DocumentTypeResponse getType(long typeId) {
        return documentTypeClient.getType(typeId);
    }
}
