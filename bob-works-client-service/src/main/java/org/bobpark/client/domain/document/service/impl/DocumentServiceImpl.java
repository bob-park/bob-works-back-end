package org.bobpark.client.domain.document.service.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.bobpark.client.common.page.Page;
import org.bobpark.client.domain.document.feign.DocumentClient;
import org.bobpark.client.domain.document.model.DocumentResponse;
import org.bobpark.client.domain.document.model.SearchDocumentResponse;
import org.bobpark.client.domain.document.service.DocumentService;
import org.bobpark.client.domain.user.feign.UserClient;
import org.bobpark.client.domain.user.model.SearchUserRequest;
import org.bobpark.client.domain.user.model.UserResponse;

@Slf4j
@RequiredArgsConstructor
@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentClient documentClient;
    private final UserClient userClient;

    @Override
    public Page<SearchDocumentResponse> search(Pageable pageable) {

        Page<DocumentResponse> result = documentClient.search(pageable);

        List<DocumentResponse> content = result.content();

        List<Long> writerIds =
            content.stream()
                .map(DocumentResponse::writerId)
                .toList();

        List<UserResponse> writers =
            userClient.searchUsers(
                SearchUserRequest.builder()
                    .ids(writerIds)
                    .build());

        return Page.<SearchDocumentResponse>builder()
            .content(
                content.stream()
                    .map(item -> {

                        UserResponse writer =
                            writers.stream()
                                .filter(w -> w.id().equals(item.writerId()))
                                .findAny()
                                .orElse(null);

                        return SearchDocumentResponse.builder()
                            .id(item.id())
                            .documentType(item.documentType())
                            .writer(writer)
                            .status(item.status())
                            .createdDate(item.createdDate())
                            .createdBy(item.createdBy())
                            .lastModifiedDate(item.lastModifiedDate())
                            .lastModifiedBy(item.lastModifiedBy())
                            .build();
                    })
                    .toList())
            .pageable(result.pageable())
            .total(result.total())
            .build();
    }

    @Override
    public DocumentResponse cancel(long documentId) {
        return documentClient.cancel(documentId);
    }
}
