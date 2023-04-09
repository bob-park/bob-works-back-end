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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

import org.bobpark.authorizationservice.domain.authorization.converter.ScopeListConverter;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "authorization_consents")
public class AuthorizationConsent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private AuthorizationClient client;

    private String principalName;

    @Convert(converter = ScopeListConverter.class)
    private List<String> authorities;

    @Builder
    private AuthorizationConsent(Long id, AuthorizationClient client, String principalName, List<String> authorities) {
        this.id = id;
        this.client = client;
        this.principalName = principalName;
        this.authorities = authorities;
    }

    public void updateAuthorities(List<String> authorities){
        this.authorities = authorities;
    }
}
