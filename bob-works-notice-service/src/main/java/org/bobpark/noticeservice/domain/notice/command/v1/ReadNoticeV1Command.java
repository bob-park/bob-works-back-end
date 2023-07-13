package org.bobpark.noticeservice.domain.notice.command.v1;

import static com.google.common.base.Preconditions.*;
import static org.apache.commons.lang3.ObjectUtils.*;

import lombok.Builder;

import org.bobpark.noticeservice.domain.notice.entity.NoticeId;

@Builder
public record ReadNoticeV1Command(NoticeId id,
                                  Long userId) {

    public ReadNoticeV1Command {
        checkArgument(isNotEmpty(id), "id must be provided.");
        checkArgument(isNotEmpty(userId), "userId must be provided.");
    }
}
