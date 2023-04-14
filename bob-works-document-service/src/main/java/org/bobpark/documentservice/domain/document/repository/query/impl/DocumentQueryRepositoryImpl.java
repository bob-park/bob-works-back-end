package org.bobpark.documentservice.domain.document.repository.query.impl;

import static org.bobpark.documentservice.domain.document.entity.QDocument.*;
import static org.bobpark.documentservice.domain.document.entity.QDocumentType.*;
import static org.bobpark.documentservice.domain.user.entity.QUser.*;
import static org.springframework.util.StringUtils.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.bobpark.documentservice.common.utils.repository.QueryDslPath;
import org.bobpark.documentservice.common.utils.repository.RepositoryUtils;
import org.bobpark.documentservice.domain.document.entity.Document;
import org.bobpark.documentservice.domain.document.model.SearchDocumentRequest;
import org.bobpark.documentservice.domain.document.repository.query.DocumentQueryRepository;
import org.bobpark.documentservice.domain.document.type.DocumentTypeName;

@RequiredArgsConstructor
public class DocumentQueryRepositoryImpl implements DocumentQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<Document> search(SearchDocumentRequest searchRequest, Pageable pageable) {

        List<Document> content =
            query.selectFrom(document)
                .join(document.documentType, documentType).fetchJoin()
                .join(document.writer, user).fetchJoin()
                .where(mappingCondition(searchRequest))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(sort(pageable))
                .fetch();

        JPAQuery<Long> countQuery =
            query.select(document.id.count())
                .from(document)
                .join(document.documentType, documentType)
                .join(document.writer, user)
                .where(mappingCondition(searchRequest));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private Predicate mappingCondition(SearchDocumentRequest searchRequest) {

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(eqTypeName(searchRequest.typeName()))
            .and(eqTypeId(searchRequest.typeId()))
            .and(eqWriter(searchRequest.writer()))
            .and(afterCreatedDate(searchRequest.createdDateFrom()))
            .and(beforeCreatedDate(searchRequest.createdDateTo()));

        return builder;
    }

    private BooleanExpression eqTypeName(DocumentTypeName typeName) {
        return typeName != null ? document.type.eq(typeName) : null;
    }

    private BooleanExpression eqTypeId(Long typeId) {
        return typeId != null ? documentType.id.eq(typeId) : null;
    }

    private BooleanExpression eqWriter(String writer) {
        return hasText(writer) ? user.userId.eq(writer) : null;
    }

    private BooleanExpression afterCreatedDate(LocalDate from) {
        return from != null ? document.createdDate.goe(from.atStartOfDay()) : null;
    }

    private BooleanExpression beforeCreatedDate(LocalDate to) {
        return to != null ? document.createdDate.loe(to.atTime(LocalTime.MAX)) : null;
    }

    // == sort == //
    private OrderSpecifier<?>[] sort(Pageable pageable) {
        return Stream.of(
                RepositoryUtils.sort(
                    pageable,
                    List.of(
                        new QueryDslPath<>("id", document.id),
                        new QueryDslPath<>("type", document.type),
                        new QueryDslPath<>("writer", user.userId),
                        new QueryDslPath<>("status", document.status),
                        new QueryDslPath<>("createdDate", document.createdDate))),
                new OrderSpecifier<?>[] {document.createdDate.desc()})
            .flatMap(Stream::of)
            .toArray(OrderSpecifier[]::new);
    }

}
