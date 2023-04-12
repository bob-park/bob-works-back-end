package org.bobpark.documentservice.domain.document.service.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.core.model.common.Id;
import org.bobpark.documentservice.domain.document.entity.DocumentType;
import org.bobpark.documentservice.domain.document.model.CreateDocumentTypeRequest;
import org.bobpark.documentservice.domain.document.model.DocumentTypeResponse;
import org.bobpark.documentservice.domain.document.model.SearchDocumentTypeRequest;
import org.bobpark.documentservice.domain.document.model.UpdateDocumentTypeRequest;
import org.bobpark.documentservice.domain.document.repository.DocumentTypeRepository;
import org.bobpark.documentservice.domain.document.service.DocumentTypeService;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class DocumentTypeServiceImpl implements DocumentTypeService {

    private final DocumentTypeRepository documentTypeRepository;

    @Transactional
    @Override
    public DocumentTypeResponse createDocumentType(CreateDocumentTypeRequest createRequest) {

        DocumentType createdType =
            DocumentType.builder()
                .type(createRequest.type())
                .name(createRequest.name())
                .description(createRequest.description())
                .build();

        documentTypeRepository.save(createdType);

        log.debug("added document type. (id={})", createdType.getId());

        return toResponse(createdType);
    }

    @Override
    public List<DocumentTypeResponse> search(SearchDocumentTypeRequest searchRequest) {

        List<DocumentType> result = documentTypeRepository.search(searchRequest);

        return result.stream()
            .map(this::toResponse)
            .toList();
    }

    @Override
    public DocumentTypeResponse getDocumentType(Id<DocumentType, Long> documentTypeId) {

        DocumentType documentType = getType(documentTypeId);

        return toResponse(documentType);
    }

    @Transactional
    @Override
    public DocumentTypeResponse updateDocumentType(Id<DocumentType, Long> documentTypeId,
        UpdateDocumentTypeRequest updateRequest) {

        DocumentType documentType = getType(documentTypeId);

        documentType.updateName(updateRequest.name());

        log.debug("updated document type. (id={})", documentType.getId());

        return toResponse(documentType);
    }

    @Transactional
    @Override
    public DocumentTypeResponse deleteDocumentType(Id<DocumentType, Long> documentTypeId) {

        DocumentType documentType = getType(documentTypeId);

        documentTypeRepository.delete(documentType);

        return DocumentTypeResponse.builder()
            .id(documentTypeId.getValue())
            .build();
    }

    private DocumentType getType(Id<DocumentType, Long> documentTypeId) {
        return documentTypeRepository.findById(documentTypeId.getValue())
            .orElseThrow(() -> new NotFoundException(documentTypeId));
    }

    private DocumentTypeResponse toResponse(DocumentType entity) {
        return DocumentTypeResponse.builder()
            .id(entity.getId())
            .type(entity.getType())
            .name(entity.getName())
            .description(entity.getDescription())
            .createdDate(entity.getCreatedDate())
            .createdBy(entity.getCreatedBy())
            .lastModifiedDate(entity.getLastModifiedDate())
            .lastModifiedBy(entity.getLastModifiedBy())
            .build();
    }
}
