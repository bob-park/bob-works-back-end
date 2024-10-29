package org.bobpark.documentservice.domain.document.repository.holiday;

import org.springframework.data.jpa.repository.JpaRepository;

import org.bobpark.documentservice.domain.document.entity.holiday.HolidayWorkTimeLog;
import org.bobpark.documentservice.domain.document.repository.holiday.query.HolidayWorkTimeLogQueryRepository;

public interface HolidayWorkTimeLogRepository extends JpaRepository<HolidayWorkTimeLog, Long>,
    HolidayWorkTimeLogQueryRepository {
}
