package org.bobpark.authorizationservice.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.bobpark.authorizationservice.common.entity.BaseEntity;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(name = "password")
    private String encryptPassword;

    private String name;
    private String email;
    private String phone;
    private String description;

    @Builder
    private User(Long id, String username, String encryptPassword, String name, String email, String phone,
        String description) {
        this.id = id;
        this.username = username;
        this.encryptPassword = encryptPassword;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.description = description;
    }
}
