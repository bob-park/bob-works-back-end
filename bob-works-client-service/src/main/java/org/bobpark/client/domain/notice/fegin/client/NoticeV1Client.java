package org.bobpark.client.domain.notice.fegin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import org.bobpark.client.common.page.Page;
import org.bobpark.client.domain.notice.model.v1.NoticeV1Response;
import org.bobpark.client.domain.notice.model.v1.UnReadNoticeCountV1Response;

@FeignClient(name = "notice-service", contextId = "notice-v1-client", path="v1/notice")
public interface NoticeV1Client {

    @GetMapping(path = "search")
    Page<NoticeV1Response> search(Pageable pageable);

    @GetMapping(path = "count/unread")
    UnReadNoticeCountV1Response countOfUnread();

    @GetMapping(path = "{id}")
    NoticeV1Response getNotice(@PathVariable String id);

    @PutMapping(path = "{id}/read")
    NoticeV1Response readNotice(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String id);

}
