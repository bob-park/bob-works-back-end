package org.bobpark.documentservice.domain.document.service.impl;

import static com.google.common.base.Preconditions.*;
import static org.apache.commons.lang3.ObjectUtils.*;
import static org.bobpark.documentservice.domain.document.model.DocumentTypeResponse.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.apache.commons.lang3.ObjectUtils;

import com.google.common.base.Preconditions;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.core.model.common.Id;
import org.bobpark.documentservice.domain.document.entity.DocumentType;
import org.bobpark.documentservice.domain.document.entity.DocumentTypeApproveLine;
import org.bobpark.documentservice.domain.document.model.CreateDocumentTypeApproveLineRequest;
import org.bobpark.documentservice.domain.document.model.DocumentTypeApproveLineResponse;
import org.bobpark.documentservice.domain.document.model.DocumentTypeResponse;
import org.bobpark.documentservice.domain.document.repository.DocumentTypeApproveLineRepository;
import org.bobpark.documentservice.domain.document.repository.DocumentTypeRepository;
import org.bobpark.documentservice.domain.document.service.DocumentTypeApproveLineService;
import org.bobpark.documentservice.domain.position.entity.Position;
import org.bobpark.documentservice.domain.position.repository.PositionRepository;
import org.bobpark.documentservice.domain.user.entity.User;
import org.bobpark.documentservice.domain.user.repository.UserRepository;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class DocumentTypeApproveLineServiceImpl implements DocumentTypeApproveLineService {

    private final DocumentTypeRepository documentTypeRepository;
    private final DocumentTypeApproveLineRepository documentTypeApproveLineRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public DocumentTypeResponse addApproveLine(Id<DocumentType, Long> typeId,
        CreateDocumentTypeApproveLineRequest createRequest) {

        checkArgument(isNotEmpty(createRequest.userId()), "userId must be provided.");

        DocumentType documentType =
            documentTypeRepository.findById(typeId.getValue())
                .orElseThrow(() -> new NotFoundException(typeId));

        User user =
            userRepository.findById(createRequest.userId())
                .orElseThrow(() -> new NotFoundException(User.class, createRequest.userId()));

        DocumentTypeApproveLine parent = null;

        if (createRequest.parentId() != null) {
            parent = documentTypeApproveLineRepository.findById(
                    createRequest.parentId())
                .orElseThrow(() -> new NotFoundException(DocumentTypeApproveLine.class, createRequest.parentId()));
        }

        documentType.addApproveLine(parent, user);

        log.debug("added document type approve line. (documentType={}, parent={}, user={})",
            documentType,
            parent,
            user);

        return toResponse(documentType);
    }
}
