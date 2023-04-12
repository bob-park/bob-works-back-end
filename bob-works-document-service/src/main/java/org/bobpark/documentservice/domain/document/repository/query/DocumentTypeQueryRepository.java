package org.bobpark.documentservice.domain.document.repository.query;

import java.util.List;

import org.bobpark.documentservice.domain.document.entity.DocumentType;
import org.bobpark.documentservice.domain.document.model.SearchDocumentTypeRequest;

public interface DocumentTypeQueryRepository {

    List<DocumentType> search(SearchDocumentTypeRequest searchRequest);

}
