package org.bobpark.noticeservice.domain.notice.entity;

import static com.google.common.base.Preconditions.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.apache.commons.lang3.StringUtils;

import org.bobpark.noticeservice.common.entity.BaseEntity;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "notices")
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String description;

    @Builder
    private Notice(Long id, String title, String description) {

        checkArgument(StringUtils.isNotBlank(title), "title must be provided.");

        this.id = id;
        this.title = title;
        this.description = description;
    }
}
