package org.bobpark.client.domain.notice.service.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.bobpark.client.common.page.Page;
import org.bobpark.client.domain.notice.fegin.client.NoticeV1Client;
import org.bobpark.client.domain.notice.model.v1.NoticeV1Response;
import org.bobpark.client.domain.notice.model.v1.UnReadNoticeCountV1Response;

@Slf4j
@RequiredArgsConstructor
@Service
public class NoticeQueryV1Service {

    private final NoticeV1Client noticeClient;

    public Page<NoticeV1Response> search(Pageable pageable) {
        return noticeClient.search(pageable);
    }

    public UnReadNoticeCountV1Response countOfUnread() {
        return noticeClient.countOfUnread();
    }

    public NoticeV1Response getNotice(String id) {
        return noticeClient.getNotice(id);
    }

}
