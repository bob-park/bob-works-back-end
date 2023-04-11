package org.bobpark.authorizationservice.domain.authorization.repository.query.impl;

import static org.bobpark.authorizationservice.domain.authorization.entity.QAuthorizationClient.*;
import static org.bobpark.authorizationservice.domain.authorization.entity.QAuthorizationClientScope.*;
import static org.bobpark.authorizationservice.domain.authorization.entity.QAuthorizationScope.*;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

import com.querydsl.jpa.impl.JPAQueryFactory;

import org.bobpark.authorizationservice.domain.authorization.entity.AuthorizationClient;
import org.bobpark.authorizationservice.domain.authorization.entity.QAuthorizationClient;
import org.bobpark.authorizationservice.domain.authorization.entity.QAuthorizationClientScope;
import org.bobpark.authorizationservice.domain.authorization.entity.QAuthorizationScope;
import org.bobpark.authorizationservice.domain.authorization.repository.query.AuthorizationClientQueryRepository;

@RequiredArgsConstructor
public class AuthorizationClientQueryRepositoryImpl implements AuthorizationClientQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Optional<AuthorizationClient> findByClientId(String clientId) {
        return Optional.ofNullable(
            query.selectFrom(authorizationClient)
                .leftJoin(authorizationClient.scopes, authorizationClientScope).fetchJoin()
                .leftJoin(authorizationClientScope.scope, authorizationScope).fetchJoin()
                .where(authorizationClient.clientId.eq(clientId))
                .distinct()
                .fetchOne());
    }
}
