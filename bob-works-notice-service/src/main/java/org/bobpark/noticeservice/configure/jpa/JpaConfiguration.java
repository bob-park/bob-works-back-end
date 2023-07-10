package org.bobpark.noticeservice.configure.jpa;

import java.util.Optional;

import jakarta.persistence.EntityManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import com.querydsl.jpa.impl.JPAQueryFactory;

@EnableJpaAuditing
@Configuration
public class JpaConfiguration {

    @Bean
    public JPAQueryFactory queryFactory(EntityManager em) {
        return new JPAQueryFactory(em);
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> {

            JwtAuthenticationToken authentication = (JwtAuthenticationToken)SecurityContextHolder.getContext()
                .getAuthentication();

            if (!authentication.isAuthenticated()) {
                throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT);
            }

            return Optional.of(authentication.getName());
        };
    }
}
