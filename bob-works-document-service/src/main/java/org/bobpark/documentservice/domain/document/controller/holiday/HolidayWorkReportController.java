package org.bobpark.documentservice.domain.document.controller.holiday;

import java.security.Principal;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.core.model.common.Id;
import org.bobpark.documentservice.domain.document.entity.Document;
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

    @GetMapping(path = "{documentId:\\d+}")
    public HolidayWorkReportResponse getReport(@PathVariable long documentId) {
        return holidayWorkReportService.getReport(Id.of(Document.class, documentId));
    }
}
