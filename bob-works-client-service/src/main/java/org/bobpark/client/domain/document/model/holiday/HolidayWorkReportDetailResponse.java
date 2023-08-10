package org.bobpark.client.domain.document.model.holiday;

import java.util.List;

import org.bobpark.client.domain.document.model.response.DocumentTypeApprovalLineStatusResponse;

public record HolidayWorkReportDetailResponse(HolidayWorkReportResponse document,
                                              List<DocumentTypeApprovalLineStatusResponse> lines) {
}
