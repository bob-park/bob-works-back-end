package org.bobpark.documentservice.domain.document.service.impl;

import static com.google.common.base.Preconditions.*;
import static org.apache.commons.lang3.ObjectUtils.*;
import static org.bobpark.documentservice.domain.document.model.DocumentTypeResponse.*;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.core.model.common.Id;
import org.bobpark.documentservice.common.utils.authentication.AuthenticationUtils;
import org.bobpark.documentservice.domain.document.entity.DocumentType;
import org.bobpark.documentservice.domain.document.entity.DocumentTypeApprovalLine;
import org.bobpark.documentservice.domain.document.model.CreateDocumentTypeApprovalLineRequest;
import org.bobpark.documentservice.domain.document.model.DocumentTypeResponse;
import org.bobpark.documentservice.domain.document.repository.DocumentTypeApproveLineRepository;
import org.bobpark.documentservice.domain.document.repository.DocumentTypeRepository;
import org.bobpark.documentservice.domain.document.service.DocumentTypeApproveLineService;
import org.bobpark.documentservice.domain.team.feign.client.TeamClient;
import org.bobpark.documentservice.domain.team.model.TeamResponse;
import org.bobpark.documentservice.domain.user.model.UserResponse;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class DocumentTypeApproveLineServiceImpl implements DocumentTypeApproveLineService {

    private final DocumentTypeRepository documentTypeRepository;
    private final DocumentTypeApproveLineRepository documentTypeApproveLineRepository;

    private final TeamClient teamClient;

    @Transactional
    @Override
    public DocumentTypeResponse addApproveLine(Id<DocumentType, Long> typeId,
        CreateDocumentTypeApprovalLineRequest createRequest) {

        checkArgument(isNotEmpty(createRequest.userId()), "userId must be provided.");
        checkArgument(isNotEmpty(createRequest.teamId()), "teamId must be provided.");

        DocumentType documentType =
            documentTypeRepository.findById(typeId.getValue())
                .orElseThrow(() -> new NotFoundException(typeId));

        List<UserResponse> users = AuthenticationUtils.getInstance().getUsersByPrincipal();

        DocumentTypeApprovalLine parent = null;

        if (createRequest.parentId() != null) {
            parent = documentTypeApproveLineRepository.findById(
                    createRequest.parentId())
                .orElseThrow(() -> new NotFoundException(DocumentTypeApprovalLine.class, createRequest.parentId()));
        }

        TeamResponse team = getTeam(createRequest.teamId());

        documentType.addApproveLine(parent, createRequest.userId(), team.id());

        log.debug("added document type approve line. (documentType={}, parent={}, userId={})",
            documentType,
            parent,
            createRequest.userId());

        return toResponse(documentType, users);
    }

    private TeamResponse getTeam(long teamId) {
        return teamClient.getById(teamId);
    }
}
