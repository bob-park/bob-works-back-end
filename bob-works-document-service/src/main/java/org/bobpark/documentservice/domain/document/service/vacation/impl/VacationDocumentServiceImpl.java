package org.bobpark.documentservice.domain.document.service.vacation.impl;

import static com.google.common.base.Preconditions.*;
import static org.apache.commons.lang3.ObjectUtils.*;
import static org.bobpark.documentservice.domain.document.model.vacation.VacationDocumentResponse.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Collections;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.core.model.common.Id;
import org.bobpark.documentservice.common.utils.authentication.AuthenticationUtils;
import org.bobpark.documentservice.domain.document.entity.Document;
import org.bobpark.documentservice.domain.document.entity.DocumentType;
import org.bobpark.documentservice.domain.document.entity.vacation.VacationDocument;
import org.bobpark.documentservice.domain.document.model.vacation.CreateVacationDocumentRequest;
import org.bobpark.documentservice.domain.document.model.vacation.SearchVacationDocumentRequest;
import org.bobpark.documentservice.domain.document.model.vacation.VacationDocumentResponse;
import org.bobpark.documentservice.domain.document.repository.DocumentTypeRepository;
import org.bobpark.documentservice.domain.document.repository.VacationDocumentRepository;
import org.bobpark.documentservice.domain.document.service.vacation.VacationDocumentService;
import org.bobpark.documentservice.domain.document.type.VacationSubType;
import org.bobpark.documentservice.domain.user.feign.client.UserClient;
import org.bobpark.documentservice.domain.user.model.UserResponse;
import org.bobpark.documentservice.domain.user.model.notification.SendUserNotificationV1Request;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class VacationDocumentServiceImpl implements VacationDocumentService {

    private static final List<Integer> FAMILY_DAY_WEEKS = List.of(1, 3);

    private final DocumentTypeRepository documentTypeRepository;
    private final VacationDocumentRepository vacationDocumentRepository;

    @Transactional
    @Override
    public VacationDocumentResponse addDocument(Principal principal, CreateVacationDocumentRequest createRequest) {

        checkArgument(isNotEmpty(createRequest.typeId()), "typeId must be provided.");

        DocumentType documentType =
            documentTypeRepository.findById(createRequest.typeId())
                .orElseThrow(() -> new NotFoundException(DocumentType.class, createRequest.typeId()));

        UserResponse writer = AuthenticationUtils.getInstance().getUser(principal.getName());

        VacationDocument createDocument =
            VacationDocument.builder()
                .documentType(documentType)
                .writerId(writer.id())
                .teamId(writer.team().id())
                .vacationType(createRequest.vacationType())
                .vacationSubType(createRequest.vacationSubType())
                .vacationDateFrom(createRequest.vacationDateFrom())
                .vacationDateTo(createRequest.vacationDateTo())
                .daysCount(
                    calculateDaysCount
                        (createRequest.vacationSubType(),
                            createRequest.vacationDateFrom(),
                            createRequest.vacationDateTo()))
                .reason(createRequest.reason())
                .useAlternativeVacationIds(createRequest.useAlternativeVacationIds())
                .build();

        vacationDocumentRepository.save(createDocument);

        log.debug("added vacation document. (id={})", createDocument.getId());

        return toResponse(createDocument);
    }

    @Override
    public VacationDocumentResponse getDocument(Id<? extends Document, Long> documentId) {

        VacationDocument vacationDocument =
            vacationDocumentRepository.findById(documentId.getValue())
                .orElseThrow(() -> new NotFoundException(documentId));

        return toResponse(vacationDocument);
    }

    @Override
    public Page<VacationDocumentResponse> search(SearchVacationDocumentRequest searchRequest, Pageable pageable) {

        Long writerId = null;

        Authentication auth = AuthenticationUtils.getInstance().getAuthentication();
        UserResponse user = AuthenticationUtils.getInstance().getUser(auth.getName());
        writerId = user.id();

        Page<VacationDocument> result = vacationDocumentRepository.search(writerId, searchRequest, pageable);

        return result.map(VacationDocumentResponse::toResponse);
    }

    private void sendNotification(UserResponse writer, VacationDocument document) {

        String vacationType =
            switch (document.getVacationType()) {
                case GENERAL -> "연차";
                case ALTERNATIVE -> "대체휴가";
            };

        String message =
            String.format(
                "%s(%s-%s)이(가) %s을(를) 신청하였습니다.",
                writer.name(),
                writer.team().name(),
                writer.position().name(),
                vacationType);

    }

    private double calculateDaysCount(VacationSubType subType, LocalDate dateFrom, LocalDate dateTo) {

        checkArgument(isNotEmpty(dateFrom), "dateFrom must be provided.");
        checkArgument(isNotEmpty(dateTo), "dateTo must be provided.");
        checkArgument(dateFrom.compareTo(dateTo) <= 0, "dateFrom must be less than or equal to dateTo.");
        checkArgument(dateFrom.getYear() == dateTo.getYear(), "dateFrom must be equal to dateTo.");

        if (subType != null) {
            return 0.5;
        }

        double result = 0.0;

        int dayCount = dateTo.getDayOfYear() - dateFrom.getDayOfYear();

        for (int i = 0; i <= dayCount; i++) {

            LocalDate date = dateFrom.plusDays(i);

            switch (date.getDayOfWeek()) {
                case FRIDAY -> {
                    int weekCountOfMonth = date.get(ChronoField.ALIGNED_WEEK_OF_MONTH);

                    if (FAMILY_DAY_WEEKS.contains(weekCountOfMonth)) {
                        result += 0.5;
                    } else {
                        result += 1;
                    }

                }
                case SATURDAY, SUNDAY -> {
                    // ignore
                }
                default -> result += 1;
            }

        }

        return result;
    }
}
