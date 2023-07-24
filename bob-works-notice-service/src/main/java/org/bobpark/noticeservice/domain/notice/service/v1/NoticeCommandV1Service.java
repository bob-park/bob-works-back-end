package org.bobpark.noticeservice.domain.notice.service.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.bobpark.noticeservice.common.utils.UserProvider;
import org.bobpark.noticeservice.domain.notice.aggregate.v1.NoticeV1Aggregate;
import org.bobpark.noticeservice.domain.notice.command.v1.CreateNoticeV1Command;
import org.bobpark.noticeservice.domain.notice.command.v1.ReadNoticeV1Command;
import org.bobpark.noticeservice.domain.notice.entity.NoticeId;
import org.bobpark.noticeservice.domain.notice.model.v1.CreateNoticeV1Request;
import org.bobpark.noticeservice.domain.notice.model.v1.NoticeV1Response;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class NoticeCommandV1Service {

    private final NoticeV1Aggregate noticeAggregate;

    public NoticeV1Response createNotice(CreateNoticeV1Request createRequest) {
        return noticeAggregate.handleCreateNotice(
            new CreateNoticeV1Command(createRequest.title(), createRequest.description()));
    }

    public NoticeV1Response readNotice(String noticeId) {

        long userId = UserProvider.getInstance().getUserId();

        return noticeAggregate.handleReadNotice(
            ReadNoticeV1Command.builder()
                .id(new NoticeId(noticeId))
                .userId(userId)
                .build());

    }

}
