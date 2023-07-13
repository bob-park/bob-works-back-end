package org.bobpark.noticeservice.domain.notice.repository.query.impl;

import static org.apache.commons.lang3.ObjectUtils.*;
import static org.bobpark.noticeservice.domain.notice.entity.QNotice.*;
import static org.bobpark.noticeservice.domain.notice.entity.QNoticeReadUser.*;

import lombok.RequiredArgsConstructor;

import com.querydsl.jpa.impl.JPAQueryFactory;

import org.bobpark.noticeservice.domain.notice.entity.NoticeId;
import org.bobpark.noticeservice.domain.notice.entity.QNoticeReadUser;
import org.bobpark.noticeservice.domain.notice.repository.query.NoticeReadUserQueryRepository;

@RequiredArgsConstructor
public class NoticeReadUserQueryRepositoryImpl implements NoticeReadUserQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public boolean isRead(NoticeId noticeId, long userId) {

        Long count =
            query.select(noticeReadUser.id.count())
                .from(noticeReadUser)
                .join(noticeReadUser.notice, notice)
                .where(notice.id.eq(noticeId), noticeReadUser.userId.eq(userId))
                .fetchOne();

        return defaultIfNull(count, 0L) > 0;
    }
}
