package org.bobpark.client.domain.document.service;

import java.util.List;

import org.bobpark.client.domain.document.model.DocumentTypeResponse;

public interface DocumentTypeService {

    List<DocumentTypeResponse> search();

    DocumentTypeResponse getType(long typeId);

}
