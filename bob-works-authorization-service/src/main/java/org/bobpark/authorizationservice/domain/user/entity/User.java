package org.bobpark.authorizationservice.domain.user.entity;

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

import org.bobpark.authorizationservice.common.entity.BaseEntity;
import org.bobpark.authorizationservice.domain.role.entity.Role;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    @Column(name = "password")
    private String encryptPassword;

    private String name;
    private String email;
    private String phone;
    private String description;

    @Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRole> roles = new ArrayList<>();

    @Builder
    private User(Long id, String userId, String encryptPassword, String name, String email, String phone,
        String description) {
        this.id = id;
        this.userId = userId;
        this.encryptPassword = encryptPassword;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.description = description;
    }

    public void addRole(Role role) {
        if (getRoles().stream().anyMatch(item -> item.getRole() == role)) {
            return;
        }

        getRoles().add(
            UserRole.builder()
                .user(this)
                .role(role)
                .build());
    }

    public void removeRole(Role role) {

        getRoles().stream()
            .filter(item -> item.getRole() == role)
            .findAny()
            .ifPresent(item -> getRoles().remove(item));
    }
}
