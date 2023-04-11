package org.bobpark.documentservice.domain.document.service;

import org.bobpark.documentservice.domain.document.model.CreateDocumentTypeRequest;
import org.bobpark.documentservice.domain.document.model.DocumentTypeResponse;

public interface DocumentTypeService {

    DocumentTypeResponse createDocumentType(CreateDocumentTypeRequest createRequest);
}
