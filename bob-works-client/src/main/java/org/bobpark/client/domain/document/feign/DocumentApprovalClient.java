package org.bobpark.client.domain.document.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;

import org.bobpark.client.common.page.Page;
import org.bobpark.client.domain.document.model.DocumentApprovalResponse;
import org.bobpark.client.domain.document.model.SearchDocumentApprovalRequest;

@FeignClient(name="document-service", contextId = "document-approval-service")
public interface DocumentApprovalClient {

    /**
     * ! 여기서 {@link @SpringQueryMap} 이거 record 가 안되네?
     *
     * @param searchRequest
     * @param pageable
     * @return
     */
    @GetMapping(path = "document/approval/search")
    Page<DocumentApprovalResponse> search(@SpringQueryMap SearchDocumentApprovalRequest searchRequest, Pageable pageable);
}
