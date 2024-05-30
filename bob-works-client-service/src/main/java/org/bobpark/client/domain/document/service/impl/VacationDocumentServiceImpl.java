package org.bobpark.client.domain.document.service.impl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import org.bobpark.client.common.page.Page;
import org.bobpark.client.common.utils.DocumentUtils;
import org.bobpark.client.domain.document.feign.DocumentClient;
import org.bobpark.client.domain.document.feign.DocumentTypeClient;
import org.bobpark.client.domain.document.model.AddVacationDocumentRequest;
import org.bobpark.client.domain.document.model.DocumentResponse;
import org.bobpark.client.domain.document.model.DocumentTypeResponse;
import org.bobpark.client.domain.document.model.SearchVacationDocumentRequest;
import org.bobpark.client.domain.document.model.UsageVacationResponse;
import org.bobpark.client.domain.document.model.VacationDocumentResponse;
import org.bobpark.client.domain.document.model.response.DocumentTypeApprovalLineStatusResponse;
import org.bobpark.client.domain.document.model.response.VacationDocumentDetailResponse;
import org.bobpark.client.domain.document.service.VacationDocumentService;
import org.bobpark.client.domain.user.feign.UserV1AlternativeVacationClient;
import org.bobpark.client.domain.user.model.UserResponse;
import org.bobpark.client.domain.user.model.vacation.UserAlternativeVacationResponse;

@Slf4j
@RequiredArgsConstructor
@Service
public class VacationDocumentServiceImpl implements VacationDocumentService {

    private final DocumentClient documentClient;
    private final DocumentTypeClient documentTypeClient;
    private final UserV1AlternativeVacationClient userAlternativeVacationClient;

    @Override
    public DocumentResponse addVacation(AddVacationDocumentRequest addRequest) {
        return documentClient.addVacation(addRequest);
    }

    @Override
    public VacationDocumentDetailResponse getVacationDocument(long documentId) {
        VacationDocumentResponse document = documentClient.getVacationDocument(documentId);

        UserResponse writer = DocumentUtils.getInstance().getUser(document.writerId());

        DocumentTypeResponse type =
            documentTypeClient.getApprovalByTeam(
                document.typeId(),
                writer.team().id());

        List<DocumentTypeApprovalLineStatusResponse> lines = Lists.newArrayList();
        DocumentUtils.getInstance().extractLines(type.approvalLine(), document.approvals(), lines);

        List<UserAlternativeVacationResponse> usedAlternativeVacations = Collections.emptyList();

        if (document.vacationType().equals("ALTERNATIVE")) {
            usedAlternativeVacations =
                userAlternativeVacationClient.findAllByIds(writer.id(), document.useAlternativeVacationIds());
        }

        return VacationDocumentDetailResponse.builder()
            .document(
                document.toBuilder()
                    .writer(writer)
                    .build())
            .lines(lines)
            .useAlternativeVacations(usedAlternativeVacations)
            .build();
    }

    @Override
    public Page<VacationDocumentResponse> search(SearchVacationDocumentRequest searchRequest, Pageable pageable) {

        Page<VacationDocumentResponse> result =
            documentClient.searchVacation(searchRequest, pageable);

        List<VacationDocumentResponse> contents =
            result.content().stream()
                .map((item) ->
                    item.toBuilder()
                        .writer(DocumentUtils.getInstance().getUser(item.writerId()))
                        .build())
                .toList();

        return Page.<VacationDocumentResponse>builder()
            .content(contents)
            .total(result.total())
            .pageable(result.pageable())
            .build();
    }

    @Override
    public List<UsageVacationResponse> usage(long userId) {

        LocalDate now = LocalDate.now();

        SearchVacationDocumentRequest searchRequest =
            SearchVacationDocumentRequest.builder()
                .startDate(LocalDate.of(now.getYear(), 1, 1))
                .endDate(LocalDate.of(now.getYear(), 12, 31))
                .build();

        Pageable pageable = PageRequest.of(0, 100);

        List<VacationDocumentResponse> result = documentClient.searchVacation(searchRequest, pageable).content();

        List<UserAlternativeVacationResponse> allAlternative = userAlternativeVacationClient.getAll(userId);

        return result.stream()
            .map(item -> {

                List<Long> alterIds = item.useAlternativeVacationIds();

                List<UserAlternativeVacationResponse> alterList =
                    allAlternative.stream()
                        .filter(alter -> alterIds.contains(alter.id()))
                        .toList();

                return UsageVacationResponse.builder()
                    .id(item.id())
                    .type(item.type())
                    .typeId(item.typeId())
                    .writer(DocumentUtils.getInstance().getUser(item.writerId()))
                    .status(item.status())
                    .createdDate(item.createdDate())
                    .createdBy(item.createdBy())
                    .lastModifiedDate(item.lastModifiedDate())
                    .lastModifiedBy(item.lastModifiedBy())
                    .vacationType(item.vacationType())
                    .vacationSubType(item.vacationSubType())
                    .vacationDateFrom(item.vacationDateFrom())
                    .vacationDateTo(item.vacationDateTo())
                    .daysCount(item.daysCount())
                    .reason(item.reason())
                    .alternativeVacations(alterList)
                    .build();
            })
            .toList();
    }

}
