package org.bobpark.client.domain.document.service;

import org.bobpark.client.domain.document.model.holiday.CreateHolidayWorkReportRequest;
import org.bobpark.client.domain.document.model.holiday.HolidayWorkReportDetailResponse;
import org.bobpark.client.domain.document.model.holiday.HolidayWorkReportResponse;

public interface HolidayWorkReportService {

    HolidayWorkReportResponse createReport(CreateHolidayWorkReportRequest createRequest);

    HolidayWorkReportDetailResponse getReport(long documentId);
}
