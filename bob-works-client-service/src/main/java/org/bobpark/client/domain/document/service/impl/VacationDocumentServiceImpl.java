package org.bobpark.client.domain.document.service.impl;

import java.util.Collections;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import org.bobpark.client.common.utils.DocumentUtils;
import org.bobpark.client.domain.document.feign.DocumentClient;
import org.bobpark.client.domain.document.feign.DocumentTypeClient;
import org.bobpark.client.domain.document.model.AddVacationDocumentRequest;
import org.bobpark.client.domain.document.model.DocumentResponse;
import org.bobpark.client.domain.document.model.DocumentTypeResponse;
import org.bobpark.client.domain.document.model.VacationDocumentResponse;
import org.bobpark.client.domain.document.model.response.DocumentTypeApprovalLineStatusResponse;
import org.bobpark.client.domain.document.model.response.VacationDocumentDetailResponse;
import org.bobpark.client.domain.document.service.VacationDocumentService;
import org.bobpark.client.domain.user.feign.UserClient;
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

}
