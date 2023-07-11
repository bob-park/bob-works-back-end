package org.bobpark.noticeservice.domain.notice.entity;

import static com.google.common.base.Preconditions.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "text")
    private String description;

    @Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "notice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoticeReadUser> readUsers = new ArrayList<>();

    @Builder
    private Notice(Long id, String title, String description) {

        checkArgument(StringUtils.isNotBlank(title), "title must be provided.");

        this.id = id;
        this.title = title;
        this.description = description;
    }

    public void addReadUser(long readUserId) {

        // TODO 여기서 사용자가 많은 경우 처리가 힘들듯 한데... 나중에 cache 로 처리 해야할 듯
        if (getReadUsers().stream().anyMatch(item -> item.getUserId() == readUserId)) {
            return;
        }

        NoticeReadUser readUser =
            NoticeReadUser.builder()
                .userId(readUserId)
                .build();

        readUser.setNotice(this);

        getReadUsers().add(readUser);
    }
}
