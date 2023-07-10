package org.bobpark.noticeservice.domain.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.bobpark.noticeservice.domain.notice.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
