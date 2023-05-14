package org.bobpark.client.domain.document.service.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import org.bobpark.client.domain.document.feign.DocumentClient;
import org.bobpark.client.domain.document.feign.DocumentTypeClient;
import org.bobpark.client.domain.document.model.AddVacationDocumentRequest;
import org.bobpark.client.domain.document.model.DocumentApprovalResponse;
import org.bobpark.client.domain.document.model.DocumentResponse;
import org.bobpark.client.domain.document.model.DocumentTypeApprovalLineResponse;
import org.bobpark.client.domain.document.model.DocumentTypeResponse;
import org.bobpark.client.domain.document.model.VacationDocumentResponse;
import org.bobpark.client.domain.document.model.response.DocumentTypeApprovalLineStatusResponse;
import org.bobpark.client.domain.document.model.response.VacationDocumentDetailResponse;
import org.bobpark.client.domain.document.service.VacationDocumentService;

@Slf4j
@RequiredArgsConstructor
@Service
public class VacationDocumentServiceImpl implements VacationDocumentService {

    private final DocumentClient documentClient;
    private final DocumentTypeClient documentTypeClient;

    @Override
    public DocumentResponse addVacation(AddVacationDocumentRequest addRequest) {
        return documentClient.addVacation(addRequest);
    }

    @Override
    public VacationDocumentDetailResponse getVacationDocument(long documentId) {
        VacationDocumentResponse document = documentClient.getVacationDocument(documentId);
        DocumentTypeResponse type =
            documentTypeClient.getApprovalByTeam(
                document.typeId(),
                document.writer().team().id());

        List<DocumentTypeApprovalLineStatusResponse> lines = Lists.newArrayList();
        extractLines(type.approvalLine(), document.approvals(), lines);

        return VacationDocumentDetailResponse.builder()
            .document(document)
            .lines(lines)
            .build();
    }

    private void extractLines(DocumentTypeApprovalLineResponse line, List<DocumentApprovalResponse> approvals,
        List<DocumentTypeApprovalLineStatusResponse> list) {

        DocumentApprovalResponse approval =
            approvals.stream()
                .filter(item -> item.lineId().equals(line.id()))
                .findAny()
                .orElse(null);

        DocumentTypeApprovalLineStatusResponse lineStatus =
            DocumentTypeApprovalLineStatusResponse.builder()
                .id(line.id())
                .userId(line.user().userId())
                .username(line.user().name())
                .positionId(line.user().position().id())
                .positionName(line.user().position().name())
                .status(approval != null ? approval.status() : "WAITING")
                .approvedDateTime(approval != null ? approval.approvedDateTime() : null)
                .reason(approval != null ? approval.reason() : null)
                .build();

        list.add(lineStatus);

        if (line.next() != null) {
            extractLines(line.next(), approvals, list);
        }
    }

}
