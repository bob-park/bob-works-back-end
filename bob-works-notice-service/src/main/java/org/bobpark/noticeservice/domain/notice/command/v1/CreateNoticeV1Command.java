package org.bobpark.noticeservice.domain.notice.command.v1;

public record CreateNoticeV1Command(String title,
                                    String description) {
}
