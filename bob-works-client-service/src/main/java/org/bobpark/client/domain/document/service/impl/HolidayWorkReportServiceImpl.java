package org.bobpark.client.domain.document.service.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import org.bobpark.client.common.utils.DocumentUtils;
import org.bobpark.client.domain.document.feign.DocumentHolidayWorkReportClient;
import org.bobpark.client.domain.document.feign.DocumentTypeClient;
import org.bobpark.client.domain.document.model.DocumentTypeResponse;
import org.bobpark.client.domain.document.model.holiday.CreateHolidayWorkReportRequest;
import org.bobpark.client.domain.document.model.holiday.HolidayWorkReportDetailResponse;
import org.bobpark.client.domain.document.model.holiday.HolidayWorkReportResponse;
import org.bobpark.client.domain.document.model.response.DocumentTypeApprovalLineStatusResponse;
import org.bobpark.client.domain.document.service.HolidayWorkReportService;
import org.bobpark.client.domain.user.model.UserResponse;

@Slf4j
@RequiredArgsConstructor
@Service
public class HolidayWorkReportServiceImpl implements HolidayWorkReportService {

    private final DocumentTypeClient documentTypeClient;
    private final DocumentHolidayWorkReportClient holidayWorkReportClient;

    @Override
    public HolidayWorkReportResponse createReport(CreateHolidayWorkReportRequest createRequest) {
        return holidayWorkReportClient.createDocument(createRequest);
    }

    @Override
    public HolidayWorkReportDetailResponse getReport(long documentId) {
        HolidayWorkReportResponse document = holidayWorkReportClient.getDocument(documentId);

        UserResponse writer = DocumentUtils.getInstance().getUser(document.writer().id());

        DocumentTypeResponse type =
            documentTypeClient.getApprovalByTeam(
                document.typeId(),
                writer.team().id());

        List<DocumentTypeApprovalLineStatusResponse> lines = Lists.newArrayList();
        DocumentUtils.getInstance().extractLines(type.approvalLine(), document.approvals(), lines);

        return new HolidayWorkReportDetailResponse(document, lines);
    }

}
