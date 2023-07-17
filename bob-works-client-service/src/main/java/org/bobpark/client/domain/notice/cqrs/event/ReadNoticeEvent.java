package org.bobpark.client.domain.notice.cqrs.event;

public record ReadNoticeEvent(String authToken, String id) {
}
