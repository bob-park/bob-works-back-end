package org.bobpark.client.domain.user.service.holiday;

import org.bobpark.client.domain.document.model.holiday.CreateHolidayWorkReportRequest;
import org.bobpark.client.domain.document.model.holiday.HolidayWorkReportResponse;

public interface HolidayWorkReportService {

    HolidayWorkReportResponse createReport(CreateHolidayWorkReportRequest createRequest);
}
