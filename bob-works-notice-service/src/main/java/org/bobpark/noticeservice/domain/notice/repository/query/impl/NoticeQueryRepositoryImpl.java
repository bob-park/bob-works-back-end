package org.bobpark.noticeservice.domain.notice.repository.query.impl;

import lombok.RequiredArgsConstructor;

import com.querydsl.jpa.impl.JPAQueryFactory;

import org.bobpark.noticeservice.domain.notice.repository.query.NoticeQueryRepository;

@RequiredArgsConstructor
public class NoticeQueryRepositoryImpl implements NoticeQueryRepository {

    private final JPAQueryFactory query;

}
