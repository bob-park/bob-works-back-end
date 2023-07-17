package org.bobpark.client.domain.notice.fegin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;

import org.bobpark.client.common.page.Page;
import org.bobpark.client.domain.notice.model.v1.NoticeV1Response;

@FeignClient(name = "notice-service", contextId = "notice-v1-client", path="v1/notice")
public interface NoticeV1Client {

    @GetMapping(path = "search")
    Page<NoticeV1Response> search(Pageable pageable);
}
