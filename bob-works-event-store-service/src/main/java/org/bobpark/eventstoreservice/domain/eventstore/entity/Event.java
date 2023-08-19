package org.bobpark.eventstoreservice.domain.eventstore.entity;

import static com.google.common.base.Preconditions.*;
import static org.apache.commons.lang3.ObjectUtils.*;

import java.util.Map;

import jakarta.persistence.Convert;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hibernate.annotations.Type;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import io.micrometer.common.util.StringUtils;

import org.bobpark.eventstoreservice.common.entity.BaseTimeEntity;
import org.bobpark.eventstoreservice.common.type.EventStatus;
import org.bobpark.eventstoreservice.domain.eventstore.entity.converter.EventDataConverter;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "event_store_events")
public class Event extends BaseTimeEntity<EventId> {

    @EmbeddedId
    private EventId id;

    private String eventName;

    @Type(JsonType.class)
    @Convert(converter = EventDataConverter.class)
    private Map<String, Object> eventData;

    @Enumerated(EnumType.STRING)
    private EventStatus status;

    private String createdModuleName;
    private String createdIpAddress;
    private String executedModuleName;
    private String executedIpAddress;

    @Version
    private long version;

    @Builder
    private Event(EventId id, String eventName, Map<String, Object> eventData, EventStatus status,
        String createdModuleName, String createdIpAddress, String executedModuleName, String executedIpAddress) {

        checkArgument(isNotEmpty(id), "id must be provided.");
        checkArgument(StringUtils.isNotBlank(eventName), "eventName must be provided.");
        checkArgument(isNotEmpty(eventData), "eventData must be provided.");
        checkArgument(StringUtils.isNotBlank(createdModuleName), "createdModuleName must be provided.");
        checkArgument(StringUtils.isNotBlank(createdIpAddress), "createdIpAddress must be provided.");

        this.id = id;
        this.eventName = eventName;
        this.eventData = eventData;
        this.status = defaultIfNull(status, EventStatus.WAITING);
        this.createdModuleName = createdModuleName;
        this.createdIpAddress = createdIpAddress;
        this.executedModuleName = executedModuleName;
        this.executedIpAddress = executedIpAddress;
        this.version = 0;
    }
}
