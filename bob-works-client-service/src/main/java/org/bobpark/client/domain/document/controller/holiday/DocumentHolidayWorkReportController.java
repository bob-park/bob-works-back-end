package org.bobpark.client.domain.document.controller.holiday;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.client.domain.document.feign.DocumentHolidayWorkReportClient;
import org.bobpark.client.domain.document.model.HolidayWorkTimeLogResponse;
import org.bobpark.client.domain.document.model.holiday.CreateHolidayWorkReportRequest;
import org.bobpark.client.domain.document.model.holiday.HolidayWorkReportDetailResponse;
import org.bobpark.client.domain.document.model.holiday.HolidayWorkReportResponse;
import org.bobpark.client.domain.document.service.HolidayWorkReportService;

@RequiredArgsConstructor
@RestController
@RequestMapping("document/holiday")
public class DocumentHolidayWorkReportController {
    private final DocumentHolidayWorkReportClient holidayWorkReportClient;
    private final HolidayWorkReportService holidayWorkReportService;

    @PostMapping(path = "")
    public HolidayWorkReportResponse createReport(@RequestBody CreateHolidayWorkReportRequest createRequest) {
        return holidayWorkReportService.createReport(createRequest);
    }

    @GetMapping(path = "{documentId}")
    public HolidayWorkReportDetailResponse getReport(@PathVariable long documentId) {
        return holidayWorkReportService.getReport(documentId);
    }

    @GetMapping(path = "work/time/{workTimeId}/logs")
    public List<HolidayWorkTimeLogResponse> getWorkTimeLogs(@PathVariable long workTimeId) {
        return holidayWorkReportClient.getWorkTimeLogs(workTimeId);
    }
}
