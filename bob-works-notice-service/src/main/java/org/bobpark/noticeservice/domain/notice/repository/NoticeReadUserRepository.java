package org.bobpark.noticeservice.domain.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.bobpark.noticeservice.domain.notice.entity.NoticeReadUser;
import org.bobpark.noticeservice.domain.notice.repository.query.NoticeReadUserQueryRepository;

public interface NoticeReadUserRepository extends JpaRepository<NoticeReadUser, Long>, NoticeReadUserQueryRepository {
}
