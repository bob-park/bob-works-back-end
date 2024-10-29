package org.bobpark.client.domain.document.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.bobpark.client.domain.document.model.HolidayWorkTimeLogResponse;
import org.bobpark.client.domain.document.model.holiday.CreateHolidayWorkReportRequest;
import org.bobpark.client.domain.document.model.holiday.HolidayWorkReportResponse;

@FeignClient(name = "document-service", contextId = "document-holiday-work-report-service")
public interface DocumentHolidayWorkReportClient {

    @PostMapping(path = "document/holiday")
    HolidayWorkReportResponse createDocument(@RequestBody CreateHolidayWorkReportRequest createRequest);

    @GetMapping(path = "document/holiday/{documentId}")
    HolidayWorkReportResponse getDocument(@PathVariable long documentId);

    @GetMapping(path = "document/holiday/work/time/{workTimeId}/logs")
    List<HolidayWorkTimeLogResponse> getWorkTimeLogs(@PathVariable long workTimeId);
}
