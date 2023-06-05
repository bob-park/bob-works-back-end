package org.bobpark.documentservice.domain.document.service.impl;

import static org.bobpark.documentservice.domain.document.model.DocumentResponse.*;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.core.model.common.Id;
import org.bobpark.documentservice.common.utils.authentication.AuthenticationUtils;
import org.bobpark.documentservice.domain.document.entity.Document;
import org.bobpark.documentservice.domain.document.listener.DocumentListener;
import org.bobpark.documentservice.domain.document.model.DocumentResponse;
import org.bobpark.documentservice.domain.document.model.SearchDocumentRequest;
import org.bobpark.documentservice.domain.document.repository.DocumentRepository;
import org.bobpark.documentservice.domain.document.service.DocumentService;
import org.bobpark.documentservice.domain.user.model.UserResponse;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    private final DocumentListener documentListener;

    @Override
    public Page<DocumentResponse> search(SearchDocumentRequest searchRequest, Pageable pageable) {

        Authentication authentication = getAuthentication();
        boolean isManager = AuthenticationUtils.getInstance().isManager();
        SearchDocumentRequest condition = searchRequest;

        if (!isManager) {

            UserResponse writer = AuthenticationUtils.getInstance().getUser(authentication.getName());

            condition = SearchDocumentRequest.withoutWriter(searchRequest).writerId(writer.id()).build();
        }

        Page<Document> result = documentRepository.search(condition, pageable);

        List<UserResponse> users = getUsers();

        return result.map(item -> toResponse(item, users));
    }

    @Override
    public DocumentResponse cancel(Id<Document, Long> documentId) {

        Document document =
            documentRepository.findById(documentId.getValue())
                .orElseThrow(() -> new NotFoundException(documentId));

        documentListener.canceled(document);

        return toResponse(document, getUsers());
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public List<UserResponse> getUsers() {
        return AuthenticationUtils.getInstance().getUsersByPrincipal();
    }
}

