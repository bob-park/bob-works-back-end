package org.bobpark.documentservice.domain.document.service.holiday;

import java.security.Principal;

import org.springframework.data.domain.Page;

import org.bobpark.core.model.common.Id;
import org.bobpark.documentservice.domain.document.entity.Document;
import org.bobpark.documentservice.domain.document.model.holiday.CreateHolidayWorkReportRequest;
import org.bobpark.documentservice.domain.document.model.holiday.HolidayWorkReportResponse;

public interface HolidayWorkReportService {

    HolidayWorkReportResponse createReport(Principal principal, CreateHolidayWorkReportRequest createRequest);

    HolidayWorkReportResponse getReport(Id<Document, Long> documentId);

}
