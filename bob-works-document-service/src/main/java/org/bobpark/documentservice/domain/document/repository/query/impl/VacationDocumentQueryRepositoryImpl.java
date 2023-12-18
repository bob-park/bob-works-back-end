package org.bobpark.documentservice.domain.document.repository.query.impl;

import static org.bobpark.documentservice.domain.document.entity.vacation.QVacationDocument.*;

import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import org.apache.commons.lang.StringUtils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.bobpark.documentservice.domain.document.entity.vacation.VacationDocument;
import org.bobpark.documentservice.domain.document.model.vacation.SearchVacationDocumentRequest;
import org.bobpark.documentservice.domain.document.repository.query.VacationDocumentQueryRepository;

@RequiredArgsConstructor
public class VacationDocumentQueryRepositoryImpl implements VacationDocumentQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<VacationDocument> search(Long writerId, SearchVacationDocumentRequest searchRequest,
        Pageable pageable) {

        List<VacationDocument> contents =
            query.selectFrom(vacationDocument)
                .where(
                    mappingCondition(searchRequest),
                    writerId != null ? vacationDocument.writerId.eq(writerId) : null)
                .orderBy(vacationDocument.vacationDateFrom.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> countQuery =
            query.select(vacationDocument.id.count())
                .from(vacationDocument)
                .where(
                    mappingCondition(searchRequest),
                    writerId != null ? vacationDocument.writerId.eq(writerId) : null);

        return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchOne);
    }

    private Predicate mappingCondition(SearchVacationDocumentRequest searchRequest) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(goeStartDate(searchRequest.startDate()))
            .and(loeStartDate(searchRequest.endDate()));

        return builder;
    }

    private BooleanExpression goeStartDate(LocalDate startDate) {
        return startDate != null ? vacationDocument.vacationDateFrom.goe(startDate) : null;
    }

    private BooleanExpression loeStartDate(LocalDate endDate) {
        return endDate != null ? vacationDocument.vacationDateTo.loe(endDate) : null;
    }
}
