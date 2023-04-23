package org.bobpark.client.domain.document.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import org.bobpark.client.domain.document.model.DocumentTypeResponse;

@FeignClient(name = "document-service", contextId = "document-type-service")
public interface DocumentTypeClient {

    @GetMapping(path = "document/type/search")
    List<DocumentTypeResponse> search();
}
