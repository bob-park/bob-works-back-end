package org.bobpark.client.domain.document.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.bobpark.client.domain.document.model.holiday.CreateHolidayWorkReportRequest;
import org.bobpark.client.domain.document.model.holiday.HolidayWorkReportResponse;

@FeignClient(name = "document-service", contextId = "document-holiday-work-report-service", path = "document/holiday")
public interface DocumentHolidayWorkReportClient {

    @PostMapping(path = "")
    HolidayWorkReportResponse createDocument(@RequestBody CreateHolidayWorkReportRequest createRequest);
}
