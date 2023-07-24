package org.bobpark.noticeservice.domain.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.bobpark.noticeservice.domain.notice.entity.Notice;
import org.bobpark.noticeservice.domain.notice.entity.NoticeId;
import org.bobpark.noticeservice.domain.notice.repository.query.NoticeQueryRepository;

public interface NoticeRepository extends JpaRepository<Notice, NoticeId>, NoticeQueryRepository {
}
