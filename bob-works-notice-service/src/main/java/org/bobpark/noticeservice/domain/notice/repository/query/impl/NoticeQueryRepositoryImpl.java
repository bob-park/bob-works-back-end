package org.bobpark.noticeservice.domain.notice.repository.query.impl;

import static org.apache.commons.lang3.ObjectUtils.*;
import static org.bobpark.noticeservice.domain.notice.entity.QNotice.*;
import static org.bobpark.noticeservice.domain.notice.entity.QNoticeReadUser.*;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.bobpark.noticeservice.domain.notice.entity.Notice;
import org.bobpark.noticeservice.domain.notice.entity.QNotice;
import org.bobpark.noticeservice.domain.notice.entity.QNoticeReadUser;
import org.bobpark.noticeservice.domain.notice.model.SearchNoticeRequest;
import org.bobpark.noticeservice.domain.notice.repository.query.NoticeQueryRepository;

@RequiredArgsConstructor
public class NoticeQueryRepositoryImpl implements NoticeQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<Notice> search(SearchNoticeRequest searchRequest, Pageable pageable) {

        List<Notice> content =
            query.selectFrom(notice)
                .orderBy(notice.id.asc())
                .fetch();

        JPAQuery<Long> countQuery =
            query.select(notice.id.count())
                .from(notice);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public long countByUnread(long userId) {

        Long result =
            query.select(notice.id.count())
                .from(notice)
                .where(
                    notice.id.notIn(
                        JPAExpressions.select(noticeReadUser.notice.id)
                            .from(noticeReadUser)
                            .where(noticeReadUser.userId.eq(userId))))
                .fetchOne();

        return defaultIfNull(result, 0L);
    }
}
