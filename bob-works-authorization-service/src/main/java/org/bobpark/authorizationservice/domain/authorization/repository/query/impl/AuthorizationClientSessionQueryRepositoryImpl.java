package org.bobpark.authorizationservice.domain.authorization.repository.query.impl;

import static org.bobpark.authorizationservice.domain.authorization.entity.QAuthorizationClientSession.*;
import static org.springframework.util.StringUtils.*;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.bobpark.authorizationservice.domain.authorization.entity.AuthorizationClientSession;
import org.bobpark.authorizationservice.domain.authorization.model.SearchAuthorizationSessionCondition;
import org.bobpark.authorizationservice.domain.authorization.repository.query.AuthorizationClientSessionQueryRepository;

@RequiredArgsConstructor
public class AuthorizationClientSessionQueryRepositoryImpl implements AuthorizationClientSessionQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Optional<AuthorizationClientSession> findBy(SearchAuthorizationSessionCondition condition) {
        return Optional.ofNullable(
            query.selectFrom(authorizationClientSession)
                .where(mapping(condition))
                .fetchOne());
    }

    private Predicate mapping(SearchAuthorizationSessionCondition condition) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(eqAuthorizationCode(condition.authorizationCodeValue()))
            .and(eqAccessToken(condition.accessToken()))
            .and(eqRefreshToken(condition.refreshToken()))
            .and(eqState(condition.state()));

        return builder;
    }

    private BooleanExpression eqAuthorizationCode(String authorizationCode) {
        return hasText(authorizationCode) ?
            authorizationClientSession.authorizationCodeValue.eq(authorizationCode) : null;
    }

    private BooleanExpression eqAccessToken(String accessToken) {
        return hasText(accessToken) ?
            authorizationClientSession.accessToken.token.value.eq(accessToken) : null;
    }

    private BooleanExpression eqRefreshToken(String refreshToken) {
        return hasText(refreshToken) ?
            authorizationClientSession.refreshToken.token.value.eq(refreshToken) : null;
    }

    private BooleanExpression eqState(String state) {
        return hasText(state) ?
            authorizationClientSession.state.eq(state) : null;
    }
}
