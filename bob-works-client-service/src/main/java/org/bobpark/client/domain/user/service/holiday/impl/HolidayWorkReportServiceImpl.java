package org.bobpark.client.domain.user.service.holiday.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import org.bobpark.client.domain.document.feign.DocumentHolidayWorkReportClient;
import org.bobpark.client.domain.document.model.holiday.CreateHolidayWorkReportRequest;
import org.bobpark.client.domain.document.model.holiday.HolidayWorkReportResponse;
import org.bobpark.client.domain.user.service.holiday.HolidayWorkReportService;

@Slf4j
@RequiredArgsConstructor
@Service
public class HolidayWorkReportServiceImpl implements HolidayWorkReportService {

    private final DocumentHolidayWorkReportClient holidayWorkReportClient;

    @Override
    public HolidayWorkReportResponse createReport(CreateHolidayWorkReportRequest createRequest) {
        return holidayWorkReportClient.createDocument(createRequest);
    }
}
