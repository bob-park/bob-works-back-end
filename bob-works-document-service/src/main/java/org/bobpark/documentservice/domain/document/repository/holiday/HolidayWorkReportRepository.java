package org.bobpark.documentservice.domain.document.repository.holiday;

import org.springframework.data.jpa.repository.JpaRepository;

import org.bobpark.documentservice.domain.document.entity.holiday.HolidayWorkReport;

public interface HolidayWorkReportRepository extends JpaRepository<HolidayWorkReport, Long> {
}
