package org.bobpark.client.domain.document.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;

import org.bobpark.client.domain.document.model.DocumentResponse;

@FeignClient(name = "document-service", contextId = "document-service")
public interface DocumentClient {

    @GetMapping(path = "document/search")
    Page<DocumentResponse> search();
}
