package org.bobpark.documentservice.domain.document.service;

import org.bobpark.core.model.common.Id;
import org.bobpark.documentservice.domain.document.entity.DocumentType;
import org.bobpark.documentservice.domain.document.model.CreateDocumentTypeApproveLineRequest;
import org.bobpark.documentservice.domain.document.model.DocumentTypeResponse;

public interface DocumentTypeApproveLineService {

    DocumentTypeResponse addApproveLine(Id<DocumentType, Long> typeId, CreateDocumentTypeApproveLineRequest createRequest);

}
