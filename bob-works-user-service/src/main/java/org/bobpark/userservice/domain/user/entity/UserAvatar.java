package org.bobpark.userservice.domain.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.ToString;
import lombok.ToString.Exclude;

import org.bobpark.userservice.common.entity.BaseEntity;

@ToString
@Getter
@Entity
@Table(name = "users_avatars")
public class UserAvatar extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String avatarPath;

    public void setUser(User user) {
        this.user = user;
    }

    public void updateAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }
}
