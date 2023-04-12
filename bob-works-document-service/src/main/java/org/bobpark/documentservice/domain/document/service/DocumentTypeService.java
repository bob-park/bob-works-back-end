package org.bobpark.documentservice.domain.document.service;

import java.util.List;

import org.bobpark.core.model.common.Id;
import org.bobpark.documentservice.domain.document.entity.DocumentType;
import org.bobpark.documentservice.domain.document.model.CreateDocumentTypeRequest;
import org.bobpark.documentservice.domain.document.model.DocumentTypeResponse;
import org.bobpark.documentservice.domain.document.model.SearchDocumentTypeRequest;

public interface DocumentTypeService {

    DocumentTypeResponse createDocumentType(CreateDocumentTypeRequest createRequest);

    List<DocumentTypeResponse> search(SearchDocumentTypeRequest searchRequest);

    DocumentTypeResponse getDocumentType(Id<DocumentType, Long> documentTypeId);
}
