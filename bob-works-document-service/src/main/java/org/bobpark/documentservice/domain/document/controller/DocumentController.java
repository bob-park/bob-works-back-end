package org.bobpark.documentservice.domain.document.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.documentservice.domain.document.model.DocumentResponse;
import org.bobpark.documentservice.domain.document.model.SearchDocumentRequest;
import org.bobpark.documentservice.domain.document.service.DocumentService;

@RequiredArgsConstructor
@RestController
@RequestMapping("document")
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping(path = "search")
    public Page<DocumentResponse> search(SearchDocumentRequest searchRequest,
        @PageableDefault(size = 25, sort = "createdDate", direction = Direction.DESC) Pageable pageable) {
        return documentService.search(searchRequest, pageable);
    }

}
