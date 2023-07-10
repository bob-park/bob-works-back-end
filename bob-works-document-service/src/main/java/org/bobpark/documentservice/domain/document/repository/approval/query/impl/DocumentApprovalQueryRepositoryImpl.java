package org.bobpark.documentservice.domain.document.repository.approval.query.impl;

import static org.bobpark.documentservice.domain.document.entity.QDocument.document;
import static org.bobpark.documentservice.domain.document.entity.QDocumentApproval.documentApproval;
import static org.bobpark.documentservice.domain.document.entity.QDocumentTypeApprovalLine.documentTypeApprovalLine;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.bobpark.core.model.common.Id;
import org.bobpark.documentservice.domain.document.entity.Document;
import org.bobpark.documentservice.domain.document.entity.DocumentApproval;
import org.bobpark.documentservice.domain.document.model.approval.SearchDocumentApprovalRequest;
import org.bobpark.documentservice.domain.document.repository.approval.query.DocumentApprovalQueryRepository;
import org.bobpark.documentservice.domain.document.type.DocumentStatus;
import org.bobpark.documentservice.domain.document.type.DocumentTypeName;

@RequiredArgsConstructor
public class DocumentApprovalQueryRepositoryImpl implements DocumentApprovalQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Optional<DocumentApproval> findById(Id<DocumentApproval, Long> approvalId) {
        return Optional.ofNullable(
            query.selectFrom(documentApproval)
                .join(documentApproval.document, document).fetchJoin()
                .join(documentApproval.approvalLine, documentTypeApprovalLine).fetchJoin()
                .where(documentApproval.id.eq(approvalId.getValue()))
                .fetchOne());
    }

    @Override
    public Page<DocumentApproval> search(SearchDocumentApprovalRequest searchRequest, Pageable pageable) {
        List<DocumentApproval> content =
            query.selectFrom(documentApproval)
                .join(documentApproval.document, document).fetchJoin()
                .join(documentApproval.approvalLine, documentTypeApprovalLine).fetchJoin()
                .where(mappingCondition(searchRequest))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(documentApproval.createdDate.desc())
                .fetch();

        JPAQuery<Long> countQuery =
            query.select(documentApproval.id.count())
                .from(documentApproval)
                .join(documentApproval.document, document)
                .join(documentApproval.approvalLine, documentTypeApprovalLine)
                .where(mappingCondition(searchRequest));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public List<DocumentApproval> getAllApprovals(Id<? extends Document, Long> documentId) {
        return query.selectFrom(documentApproval)
            .where(eqDocumentId(documentId.getValue()))
            .fetch();
    }

    private Predicate mappingCondition(SearchDocumentApprovalRequest searchRequest) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(eqType(searchRequest.type()))
            .and(eqWriterId(searchRequest.writerId()))
            .and(eqStatus(searchRequest.status()))
            .and(eqApprovalLineUserId(searchRequest.approvalLineUserId()))
            .and(eqApprovalLineTeamId(searchRequest.approvalLineTeamId()));

        return builder;
    }

    private BooleanExpression eqDocumentId(Long documentId) {
        return documentId != null ? documentApproval.document.id.eq(documentId) : null;
    }

    private BooleanExpression eqType(DocumentTypeName type) {
        return type != null ? document.type.eq(type) : null;
    }

    private BooleanExpression eqWriterId(Long writerId) {
        return writerId != null ? document.writerId.eq(writerId) : null;
    }

    private BooleanExpression eqStatus(DocumentStatus status) {
        return status != null ? documentApproval.status.eq(status) : null;
    }

    private BooleanExpression eqApprovalLineUserId(Long approvalLineUserId) {
        return approvalLineUserId != null ? documentTypeApprovalLine.userId.eq(approvalLineUserId) : null;
    }

    private BooleanExpression eqApprovalLineTeamId(Long teamId) {
        return teamId != null ? documentTypeApprovalLine.teamId.eq(teamId) : null;
    }
}
