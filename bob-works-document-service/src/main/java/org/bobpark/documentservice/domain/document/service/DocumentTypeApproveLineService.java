package org.bobpark.documentservice.domain.document.service;

import org.bobpark.documentservice.domain.document.model.CreateDocumentTypeApproveLineRequest;
import org.bobpark.documentservice.domain.document.model.DocumentTypeApproveLineResponse;

public interface DocumentTypeApproveLineService {

    DocumentTypeApproveLineResponse createApproveLine(CreateDocumentTypeApproveLineRequest createRequest);

}
