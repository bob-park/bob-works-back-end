package org.bobpark.noticeservice.domain.notice.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
@Embeddable
public class NoticeId implements Serializable {

    private String id;

    public NoticeId() {
        this.id = generateId();
    }

    public NoticeId(String id) {
        this.id = id;
    }

    private String generateId() {
        int year = LocalDate.now().getYear();

        return String.format("%04d-%s", year, UUID.randomUUID());
    }

}
