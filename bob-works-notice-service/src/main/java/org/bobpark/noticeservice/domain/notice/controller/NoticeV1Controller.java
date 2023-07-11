package org.bobpark.noticeservice.domain.notice.controller;

import static org.apache.commons.lang3.math.NumberUtils.*;

import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.core.model.common.Id;
import org.bobpark.noticeservice.domain.notice.entity.Notice;
import org.bobpark.noticeservice.domain.notice.model.SearchNoticeRequest;
import org.bobpark.noticeservice.domain.notice.model.v1.NoticeV1Response;
import org.bobpark.noticeservice.domain.notice.model.v1.UnReadNoticeCountV1Response;
import org.bobpark.noticeservice.domain.notice.service.v1.NoticeV1Service;
import org.bobpark.noticeservice.domain.user.feign.client.UserClient;
import org.bobpark.noticeservice.domain.user.model.UserResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/notice")
public class NoticeV1Controller {

    private final UserClient userClient;

    private final NoticeV1Service noticeV1Service;

    @GetMapping(path = "{noticeId:\\d+}")
    public NoticeV1Response getNotice(@PathVariable long noticeId) {
        return noticeV1Service.getNotice(Id.of(Notice.class, noticeId));
    }

    @PutMapping(path = "{noticeId:\\d+}/read")
    public NoticeV1Response readNotice(Authentication authentication, @PathVariable long noticeId) {
        return noticeV1Service.read(Id.of(Notice.class, noticeId), extractUserId(authentication));
    }

    @GetMapping(path = "count/unread")
    public UnReadNoticeCountV1Response countOfUnread(Authentication authentication) {
        return noticeV1Service.countOfUnread(extractUserId(authentication));
    }

    @GetMapping(path = "search")
    public Page<NoticeV1Response> search(Authentication authentication, SearchNoticeRequest searchRequest,
        @PageableDefault Pageable pageable) {
        return noticeV1Service.search(extractUserId(authentication), searchRequest, pageable);
    }

    private long extractUserId(Authentication authentication) {
        UserResponse user = userClient.getUser(authentication.getName());

        return user.id();
    }
}
