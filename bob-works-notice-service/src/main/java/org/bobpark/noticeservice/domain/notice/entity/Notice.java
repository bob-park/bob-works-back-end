package org.bobpark.noticeservice.domain.notice.entity;

import static com.google.common.base.Preconditions.*;
import static org.apache.commons.lang3.ObjectUtils.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

import org.apache.commons.lang3.StringUtils;

import org.bobpark.noticeservice.common.entity.BaseEntity;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "notices")
public class Notice extends BaseEntity<NoticeId> {

    @EmbeddedId
    private NoticeId id;

    private String title;

    @Column(columnDefinition = "text")
    private String description;

    @Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "notice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoticeReadUser> readUsers = new ArrayList<>();

    @Builder
    private Notice(NoticeId id, String title, String description) {

        checkArgument(isNotEmpty(id), "id must be provided.");
        checkArgument(StringUtils.isNotBlank(title), "title must be provided.");

        this.id = id;
        this.title = title;
        this.description = description;
    }

    public void addReadUser(long readUserId) {

        NoticeReadUser readUser =
            NoticeReadUser.builder()
                .userId(readUserId)
                .build();

        readUser.setNotice(this);

        getReadUsers().add(readUser);
    }
}
