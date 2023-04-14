package org.bobpark.documentservice.domain.document.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.bobpark.documentservice.domain.document.entity.Document;
import org.bobpark.documentservice.domain.document.model.DocumentResponse;
import org.bobpark.documentservice.domain.document.model.SearchDocumentRequest;
import org.bobpark.documentservice.domain.document.repository.DocumentRepository;
import org.bobpark.documentservice.domain.document.service.DocumentService;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    @Override
    public Page<DocumentResponse> search(SearchDocumentRequest searchRequest, Pageable pageable) {

        Page<Document> result = documentRepository.search(searchRequest, pageable);

        return result.map(DocumentResponse::toResponse);
    }
}
