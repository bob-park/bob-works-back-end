package org.bobpark.authorizationservice.domain.authorization.entity;

import java.util.List;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

import org.bobpark.authorizationservice.domain.authorization.converter.ScopeListConverter;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "authorization_clients_sessions")
public class AuthorizationClientSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private AuthorizationClient client;

    private String principalName;

    private String authorizationGrantType;

    @Convert(converter = ScopeListConverter.class)
    private List<String> authorizedScopes;

    // TODO 나머지 진행

}
