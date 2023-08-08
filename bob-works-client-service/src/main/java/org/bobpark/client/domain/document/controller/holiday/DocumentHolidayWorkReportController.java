package org.bobpark.client.domain.document.controller.holiday;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.client.domain.document.model.holiday.CreateHolidayWorkReportRequest;
import org.bobpark.client.domain.document.model.holiday.HolidayWorkReportResponse;
import org.bobpark.client.domain.user.service.holiday.HolidayWorkReportService;

@RequiredArgsConstructor
@RestController
@RequestMapping("document/holiday")
public class DocumentHolidayWorkReportController {
    private final HolidayWorkReportService holidayWorkReportService;

    @PostMapping(path = "")
    public HolidayWorkReportResponse createReport(@RequestBody CreateHolidayWorkReportRequest createRequest) {
        return holidayWorkReportService.createReport(createRequest);
    }
}
