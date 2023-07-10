package org.bobpark.documentservice.domain.document.service.holiday;

import java.security.Principal;

import org.bobpark.documentservice.domain.document.model.holiday.CreateHolidayWorkReportRequest;
import org.bobpark.documentservice.domain.document.model.holiday.HolidayWorkReportResponse;

public interface HolidayWorkReportService {

    HolidayWorkReportResponse createReport(Principal principal, CreateHolidayWorkReportRequest createRequest);

}
