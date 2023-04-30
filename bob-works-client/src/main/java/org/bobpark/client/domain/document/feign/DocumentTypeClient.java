package org.bobpark.client.domain.document.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.bobpark.client.domain.document.model.DocumentTypeResponse;

@FeignClient(name = "document-service", contextId = "document-type-service")
public interface DocumentTypeClient {

    @GetMapping(path = "document/type/search")
    List<DocumentTypeResponse> search();

    @GetMapping(path = "document/type/{typeId}")
    DocumentTypeResponse getType(@PathVariable long typeId);
}
