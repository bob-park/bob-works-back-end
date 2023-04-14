package org.bobpark.documentservice.domain.document.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.bobpark.documentservice.domain.document.entity.Document;
import org.bobpark.documentservice.domain.document.model.DocumentResponse;
import org.bobpark.documentservice.domain.document.model.SearchDocumentRequest;
import org.bobpark.documentservice.domain.document.model.SearchDocumentRequest.SearchDocumentRequestBuilder;
import org.bobpark.documentservice.domain.document.repository.DocumentRepository;
import org.bobpark.documentservice.domain.document.service.DocumentService;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class DocumentServiceImpl implements DocumentService {

    private static final String ROLE_NAME_MANAGER = "ROLE_MANAGER";

    private final DocumentRepository documentRepository;

    private final RoleHierarchy roleHierarchy;

    @Override
    public Page<DocumentResponse> search(SearchDocumentRequest searchRequest, Pageable pageable) {

        Authentication authentication = getAuthentication();

        boolean isManager =
            roleHierarchy.getReachableGrantedAuthorities(authentication.getAuthorities())
                .contains(new SimpleGrantedAuthority(ROLE_NAME_MANAGER));

        SearchDocumentRequestBuilder searchRequestBuilder = SearchDocumentRequest.withoutWriter(searchRequest);

        if (!isManager) {
            searchRequestBuilder.writer(authentication.getName());
        }

        Page<Document> result = documentRepository.search(searchRequestBuilder.build(), pageable);

        return result.map(DocumentResponse::toResponse);
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
