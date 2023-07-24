package org.bobpark.noticeservice.domain.notice.repository.query.impl;

import static org.apache.commons.lang3.ObjectUtils.*;
import static org.bobpark.noticeservice.domain.notice.entity.QNotice.*;
import static org.bobpark.noticeservice.domain.notice.entity.QNoticeReadUser.*;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.bobpark.noticeservice.domain.notice.entity.Notice;
import org.bobpark.noticeservice.domain.notice.entity.NoticeId;
import org.bobpark.noticeservice.domain.notice.entity.QNoticeReadUser;
import org.bobpark.noticeservice.domain.notice.query.v1.SearchNoticeV1Query;
import org.bobpark.noticeservice.domain.notice.repository.query.NoticeQueryRepository;

@RequiredArgsConstructor
public class NoticeQueryRepositoryImpl implements NoticeQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<Notice> search(SearchNoticeV1Query searchQuery, Pageable pageable) {

        List<Notice> content =
            query.selectFrom(notice)
                .where()
                .orderBy(notice.createdDate.desc())
                .fetch();

        JPAQuery<Long> countQuery =
            query.select(notice.id.count())
                .where()
                .from(notice);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Optional<Notice> findIncludeReadUser(NoticeId id, long userId) {

        QNoticeReadUser subNru = new QNoticeReadUser("sub_nru");

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(notice.id.eq(id));
        builder.and(
            JPAExpressions.select(subNru.id.count())
                .from(subNru)
                .where(subNru.notice.id.eq(id), subNru.userId.eq(userId)).goe(0L));

        return Optional.ofNullable(
            query.selectFrom(notice)
                .leftJoin(notice.readUsers, noticeReadUser).fetchJoin()
                .where(builder)
                .fetchOne());
    }

    @Override
    public List<NoticeId> getUnreadIds(long userId, List<NoticeId> ids) {
        return query.select(notice.id)
            .from(notice)
            .where(eqIds(ids), unreadUserId(userId))
            .fetch();
    }

    @Override
    public long countOfUnread(long userId) {

        QNoticeReadUser subNru = new QNoticeReadUser("subNru");

        Long count = query.select(notice.id.count())
            .from(notice)
            .leftJoin(notice.readUsers, noticeReadUser)
            .where(
                JPAExpressions.select(subNru.id.count())
                    .from(subNru)
                    .where(subNru.userId.eq(userId), subNru.notice.id.eq(notice.id))
                    .eq(0L))
            .fetchOne();

        return defaultIfNull(count, 0L);
    }

    private BooleanExpression unreadUserId(Long userId) {
        return userId != null ?
            notice.id.notIn(
                JPAExpressions.select(noticeReadUser.notice.id)
                    .from(noticeReadUser)
                    .where(noticeReadUser.userId.eq(userId)))
            : null;
    }

    private BooleanExpression eqIds(List<NoticeId> ids) {
        return ids.isEmpty() ? null : notice.id.in(ids);
    }
}
