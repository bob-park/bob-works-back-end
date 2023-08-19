package org.bobpark.eventstoreservice.exception;

import org.bobpark.eventstoreservice.common.type.EventStatus;
import org.bobpark.eventstoreservice.domain.eventstore.entity.EventId;

public class InvalidEventStatusException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Invalid event status.";

    public InvalidEventStatusException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidEventStatusException(EventId id, EventStatus status) {
        super(DEFAULT_MESSAGE + "(id=" + id + ", status=" + status + ")");
    }
}
