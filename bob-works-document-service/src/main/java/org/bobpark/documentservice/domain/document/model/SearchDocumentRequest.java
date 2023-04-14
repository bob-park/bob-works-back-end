package org.bobpark.documentservice.domain.document.model;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;

import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import org.bobpark.documentservice.domain.document.type.DocumentStatus;
import org.bobpark.documentservice.domain.document.type.DocumentTypeName;

@Builder
public record SearchDocumentRequest(DocumentTypeName typeName,
                                    Long typeId,
                                    String writer,
                                    DocumentStatus status,
                                    LocalDate createdDateFrom,
                                    LocalDate createdDateTo
) {

    public static SearchDocumentRequestBuilder withoutWriter(SearchDocumentRequest prev) {
        return SearchDocumentRequest.builder()
            .typeName(prev.typeName)
            .typeId(prev.typeId)
            .status(prev.status)
            .createdDateFrom(prev.createdDateFrom)
            .createdDateTo(prev.createdDateTo);
    }

}
