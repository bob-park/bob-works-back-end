package org.bobpark.documentservice.domain.document.service.holiday.impl;

import static org.bobpark.documentservice.domain.document.model.holiday.HolidayWorkReportResponse.toResponse;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.core.model.common.Id;
import org.bobpark.documentservice.common.utils.authentication.AuthenticationUtils;
import org.bobpark.documentservice.domain.document.entity.Document;
import org.bobpark.documentservice.domain.document.entity.DocumentType;
import org.bobpark.documentservice.domain.document.entity.holiday.HolidayWorkReport;
import org.bobpark.documentservice.domain.document.entity.holiday.HolidayWorkTime;
import org.bobpark.documentservice.domain.document.entity.holiday.HolidayWorkUser;
import org.bobpark.documentservice.domain.document.model.holiday.AddHolidayWorkTime;
import org.bobpark.documentservice.domain.document.model.holiday.AddHolidayWorkUserRequest;
import org.bobpark.documentservice.domain.document.model.holiday.CreateHolidayWorkReportRequest;
import org.bobpark.documentservice.domain.document.model.holiday.HolidayWorkReportResponse;
import org.bobpark.documentservice.domain.document.repository.DocumentTypeRepository;
import org.bobpark.documentservice.domain.document.repository.holiday.HolidayWorkReportRepository;
import org.bobpark.documentservice.domain.document.service.holiday.HolidayWorkReportService;
import org.bobpark.documentservice.domain.document.type.DocumentTypeName;
import org.bobpark.documentservice.domain.user.model.UserResponse;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class HolidayWorkReportServiceImpl implements HolidayWorkReportService {

    private final DocumentTypeRepository documentTypeRepository;
    private final HolidayWorkReportRepository holidayWorkReportRepository;

    @Transactional
    @Override
    public HolidayWorkReportResponse createReport(Principal principal, CreateHolidayWorkReportRequest createRequest) {

        DocumentType documentType =
            documentTypeRepository.findById(createRequest.typeId())
                .orElseThrow(() -> new NotFoundException(DocumentType.class, createRequest.typeId()));

        UserResponse writer = AuthenticationUtils.getInstance().getUser(principal.getName());

        HolidayWorkReport createdHolidayWorkReport =
            HolidayWorkReport.builder()
                .documentType(documentType)
                .writerId(writer.id())
                .teamId(writer.team().id())
                .workPurpose(createRequest.workPurpose())
                .build();

        for (AddHolidayWorkUserRequest workUser : createRequest.workUsers()) {
            HolidayWorkUser addWorkUser =
                HolidayWorkUser.builder()
                    .isManualInput(workUser.isManualInput())
                    .workUserId(workUser.workUserId())
                    .workUserName(workUser.workUserName())
                    .workDate(workUser.workDate())
                    .isVacation(workUser.isVacation())
                    .build();

            for (AddHolidayWorkTime workTime : workUser.times()) {
                addWorkUser.addTime(workTime.startTime(), workTime.endTime());
            }

            addWorkUser.calculateWorkTime();

            createdHolidayWorkReport.addUser(addWorkUser);
        }

        holidayWorkReportRepository.save(createdHolidayWorkReport);

        log.debug("created holiday work report. (id={})", createdHolidayWorkReport.getId());

        return toResponse(createdHolidayWorkReport, Collections.singletonList(writer));
    }

    @Override
    public HolidayWorkReportResponse getReport(Id<Document, Long> documentId) {

        HolidayWorkReport holidayWorkReport =
            holidayWorkReportRepository.findById(documentId.getValue())
                .orElseThrow(() -> new NotFoundException(documentId));

        List<UserResponse> users = AuthenticationUtils.getInstance().getUsers();

        return toResponse(holidayWorkReport, users);
    }
}
