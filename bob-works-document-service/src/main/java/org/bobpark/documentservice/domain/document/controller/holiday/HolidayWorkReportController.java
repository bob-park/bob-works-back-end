package org.bobpark.documentservice.domain.document.controller.holiday;

import java.security.Principal;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.documentservice.domain.document.model.holiday.CreateHolidayWorkReportRequest;
import org.bobpark.documentservice.domain.document.model.holiday.HolidayWorkReportResponse;
import org.bobpark.documentservice.domain.document.service.holiday.HolidayWorkReportService;

@RequiredArgsConstructor
@RestController
@RequestMapping("document/holiday")
public class HolidayWorkReportController {

    private final HolidayWorkReportService holidayWorkReportService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "")
    public HolidayWorkReportResponse createDocument(Principal principal,
        @RequestBody CreateHolidayWorkReportRequest createRequest) {
        return holidayWorkReportService.createReport(principal, createRequest);
    }
}
