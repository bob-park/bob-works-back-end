package org.bobpark.userservice.domain.user.repository.vacation.query.impl;

import static org.bobpark.userservice.domain.user.entity.QUser.*;
import static org.bobpark.userservice.domain.user.entity.vacation.QUserAlternativeVacation.*;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.bobpark.userservice.domain.user.entity.QUser;
import org.bobpark.userservice.domain.user.entity.vacation.QUserAlternativeVacation;
import org.bobpark.userservice.domain.user.entity.vacation.UserAlternativeVacation;
import org.bobpark.userservice.domain.user.repository.vacation.query.UserAlternativeVacationQueryRepository;

@RequiredArgsConstructor
public class UserAlternativeVacationQueryRepositoryImpl implements UserAlternativeVacationQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public List<UserAlternativeVacation> findUsableAllByIds() {
        return query.selectFrom(userAlternativeVacation)
            .join(userAlternativeVacation.user, user).fetchJoin()
            .where(
                userAlternativeVacation.isExpired.eq(false),
                userAlternativeVacation.effectiveCount.gt(userAlternativeVacation.usedCount))
            .orderBy(userAlternativeVacation.effectiveDate.asc())
            .fetch();
    }

    @Override
    public List<UserAlternativeVacation> findAllByIds(List<Long> ids) {
        return query.selectFrom(userAlternativeVacation)
            .join(userAlternativeVacation.user, user).fetchJoin()
            .where(
                userAlternativeVacation.isExpired.eq(false),
                userAlternativeVacation.effectiveCount.gt(userAlternativeVacation.usedCount),
                eqIds(ids))
            .orderBy(userAlternativeVacation.effectiveDate.asc())
            .fetch();
    }

    private BooleanExpression eqIds(List<Long> ids) {
        return ids.isEmpty() ? null : userAlternativeVacation.id.in(ids);
    }
}
