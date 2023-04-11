package org.bobpark.authorizationservice.authorization.client;

import static org.apache.commons.lang3.math.NumberUtils.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.transaction.annotation.Transactional;

import org.bobpark.authorizationservice.domain.authorization.entity.AuthorizationClient;
import org.bobpark.authorizationservice.domain.authorization.entity.AuthorizationConsent;
import org.bobpark.authorizationservice.domain.authorization.repository.AuthorizationClientRepository;
import org.bobpark.authorizationservice.domain.authorization.repository.AuthorizationConsentRepository;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomOAuth2AuthorizationConsentService implements OAuth2AuthorizationConsentService {

    private final AuthorizationClientRepository clientRepository;
    private final AuthorizationConsentRepository consentRepository;

    @Transactional
    @Override
    public void save(OAuth2AuthorizationConsent authorizationConsent) {

        consentRepository.findBy(authorizationConsent.getRegisteredClientId(),
                authorizationConsent.getPrincipalName())
            .ifPresentOrElse(
                item ->
                    item.updateAuthorities(
                        authorizationConsent.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()),
                () -> consentRepository.save(toCustomConsent(authorizationConsent)));

    }

    @Transactional
    @Override
    public void remove(OAuth2AuthorizationConsent authorizationConsent) {
        AuthorizationConsent consent =
            consentRepository.findBy(authorizationConsent.getRegisteredClientId(),
                    authorizationConsent.getPrincipalName())
                .orElseThrow();

        consentRepository.delete(consent);
    }

    @Override
    public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {

        AuthorizationConsent consent =
            consentRepository.findBy(registeredClientId, principalName)
                .orElse(null);

        if (consent == null) {
            return null;
        }

        return OAuth2AuthorizationConsent.withId(registeredClientId, principalName)
            .authorities(authorities ->
                authorities.addAll(consent.getAuthorities().stream().map(SimpleGrantedAuthority::new).toList()))
            .build();
    }

    private AuthorizationConsent toCustomConsent(OAuth2AuthorizationConsent consent) {

        String registeredClientId = consent.getRegisteredClientId();

        AuthorizationClient client =
            clientRepository.findByClientId(registeredClientId)
                .orElseThrow();

        return AuthorizationConsent.builder()
            .client(client)
            .principalName(consent.getPrincipalName())
            .authorities(consent.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
            .build();
    }
}
