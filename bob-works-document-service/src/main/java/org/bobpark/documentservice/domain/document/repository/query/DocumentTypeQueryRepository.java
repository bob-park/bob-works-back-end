package org.bobpark.documentservice.domain.document.repository.query;

import java.util.List;
import java.util.Optional;

import org.bobpark.core.model.common.Id;
import org.bobpark.documentservice.domain.document.entity.DocumentType;
import org.bobpark.documentservice.domain.document.model.SearchDocumentTypeRequest;

public interface DocumentTypeQueryRepository {

    List<DocumentType> search(SearchDocumentTypeRequest searchRequest);

    Optional<DocumentType> findApprovalByTeam(Id<DocumentType, Long> typeId, long teamId);

}
