package org.bobpark.eventstoreservice.domain.eventstore.model;

public record CompleteEventRequest(boolean isSuccess,
                                   String message) {
}
