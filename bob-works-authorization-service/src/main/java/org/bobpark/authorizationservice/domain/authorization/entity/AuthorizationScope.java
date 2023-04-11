package org.bobpark.authorizationservice.domain.authorization.entity;

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

import org.bobpark.authorizationservice.common.entity.BaseTimeEntity;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "authorization_scopes")
public class AuthorizationScope extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String scope;
    private String description;

    @Builder
    private AuthorizationScope(Long id, String scope, String description) {
        this.id = id;
        this.scope = scope;
        this.description = description;
    }
}
