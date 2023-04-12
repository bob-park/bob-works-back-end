package org.bobpark.documentservice.domain.document.repository.query.impl;

import static org.bobpark.documentservice.domain.document.entity.QDocumentType.*;
import static org.springframework.util.StringUtils.*;

import java.util.List;

import lombok.RequiredArgsConstructor;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.bobpark.documentservice.domain.document.entity.DocumentType;
import org.bobpark.documentservice.domain.document.model.SearchDocumentTypeRequest;
import org.bobpark.documentservice.domain.document.repository.query.DocumentTypeQueryRepository;
import org.bobpark.documentservice.domain.document.type.DocumentTypeName;

@RequiredArgsConstructor
public class DocumentTypeQueryRepositoryImpl implements DocumentTypeQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public List<DocumentType> search(SearchDocumentTypeRequest searchRequest) {
        return query.selectFrom(documentType)
            .where(mappingCondition(searchRequest))
            .orderBy(documentType.name.asc())
            .fetch();
    }

    private Predicate mappingCondition(SearchDocumentTypeRequest searchRequest) {

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(eqType(searchRequest.type()))
            .and(containName(searchRequest.name()));

        return builder;
    }

    private BooleanExpression eqType(DocumentTypeName type) {
        return type != null ? documentType.type.eq(type) : null;
    }

    private BooleanExpression containName(String name) {
        return hasText(name) ? documentType.name.containsIgnoreCase(name) : null;
    }
}
