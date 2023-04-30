package org.bobpark.documentservice.domain.document.service.vacation.impl;

import static com.google.common.base.Preconditions.*;
import static org.apache.commons.lang3.ObjectUtils.*;
import static org.bobpark.documentservice.domain.document.model.vacation.VacationDocumentResponse.*;

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
import org.bobpark.documentservice.domain.document.entity.vacation.VacationDocument;
import org.bobpark.documentservice.domain.document.model.vacation.CreateVacationDocumentRequest;
import org.bobpark.documentservice.domain.document.model.vacation.VacationDocumentResponse;
import org.bobpark.documentservice.domain.document.repository.DocumentTypeRepository;
import org.bobpark.documentservice.domain.document.repository.VacationDocumentRepository;
import org.bobpark.documentservice.domain.document.service.vacation.VacationDocumentService;
import org.bobpark.documentservice.domain.user.model.UserResponse;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class VacationDocumentServiceImpl implements VacationDocumentService {

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
                .vacationType(createRequest.vacationType())
                .vacationSubType(createRequest.vacationSubType())
                .vacationDateFrom(createRequest.vacationDateFrom())
                .vacationDateTo(createRequest.vacationDateTo())
                .reason(createRequest.reason())
                .build();

        vacationDocumentRepository.save(createDocument);

        log.debug("added vacation document. (id={})", createDocument.getId());

        return toResponse(createDocument, Collections.singletonList(writer));
    }

    @Override
    public VacationDocumentResponse getDocument(Id<? extends Document, Long> documentId) {

        VacationDocument vacationDocument =
            vacationDocumentRepository.findById(documentId.getValue())
                .orElseThrow(() -> new NotFoundException(documentId));

        List<UserResponse> users = AuthenticationUtils.getInstance().getUsers();

        return toResponse(vacationDocument, users);
    }
}
