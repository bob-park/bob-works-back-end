package org.bobpark.client.domain.document.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.bobpark.client.common.page.Page;
import org.bobpark.client.domain.document.model.AddVacationDocumentRequest;
import org.bobpark.client.domain.document.model.DocumentResponse;
import org.bobpark.client.domain.document.model.SearchVacationDocumentRequest;
import org.bobpark.client.domain.document.model.VacationDocumentResponse;

@FeignClient(name = "document-service", contextId = "document-service")
public interface DocumentClient {

    @GetMapping(path = "document/search")
    Page<DocumentResponse> search(Pageable pageable);

    @PostMapping(path = "document/vacation")
    DocumentResponse addVacation(@RequestBody AddVacationDocumentRequest addRequest);

    @GetMapping(path = "document/vacation/{documentId}")
    VacationDocumentResponse getVacationDocument(@PathVariable long documentId);

    @DeleteMapping(path = "document/{documentId:\\d+}/cancel")
    DocumentResponse cancel(@PathVariable long documentId);

    @GetMapping(path ="document/vacation/search")
    Page<VacationDocumentResponse> searchVacation(@SpringQueryMap SearchVacationDocumentRequest searchRequest, Pageable pageable);
}
