package org.bobpark.authorizationservice.domain.authorization.repository.query.impl;

import static org.bobpark.authorizationservice.domain.authorization.entity.QAuthorizationClient.*;
import static org.bobpark.authorizationservice.domain.authorization.entity.QAuthorizationConsent.*;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

import com.querydsl.jpa.impl.JPAQueryFactory;

import org.bobpark.authorizationservice.domain.authorization.entity.AuthorizationConsent;
import org.bobpark.authorizationservice.domain.authorization.entity.QAuthorizationClient;
import org.bobpark.authorizationservice.domain.authorization.entity.QAuthorizationConsent;
import org.bobpark.authorizationservice.domain.authorization.repository.query.AuthorizationConsentQueryRepository;

@RequiredArgsConstructor
public class AuthorizationConsentQueryRepositoryImpl implements AuthorizationConsentQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Optional<AuthorizationConsent> findBy(String clientId, String principalName) {
        return Optional.ofNullable(
            query.selectFrom(authorizationConsent)
                .join(authorizationConsent.client, authorizationClient).fetchJoin()
                .where(
                    authorizationClient.clientId.eq(clientId),
                    authorizationConsent.principalName.eq(principalName))
                .fetchOne());
    }
}
