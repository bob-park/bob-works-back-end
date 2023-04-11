package org.bobpark.documentservice.configure.security;

import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyUtils;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import org.bobpark.documentservice.configure.security.converter.JwtRoleGrantAuthoritiesConverter;
import org.bobpark.documentservice.domain.role.service.RoleHierarchyService;

@Slf4j
@RequiredArgsConstructor
@EnableMethodSecurity
@Configuration
public class OAuth2ResourceServerConfiguration {

    private final RoleHierarchyService roleHierarchyService;

    @Bean
    public SecurityFilterChain resourceSecurityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(
            requests ->
                requests.anyRequest().authenticated());

        http.oauth2ResourceServer(
            resourceServer ->
                resourceServer.jwt(
                    jwtConfigurer ->
                        jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter())));

        return http.build();
    }

    @Bean
    static MethodSecurityExpressionHandler expressionHandler(RoleHierarchy roleHierarchy) {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();

        expressionHandler.setRoleHierarchy(roleHierarchy);

        return expressionHandler;
    }

    /*
      role hierarchy
     */
    @Bean
    public AuthorityAuthorizationManager<RequestAuthorizationContext> authorizationManager() {

        AuthorityAuthorizationManager<RequestAuthorizationContext> authorizationManager =
            AuthorityAuthorizationManager.hasRole("USER");

        authorizationManager.setRoleHierarchy(roleHierarchy());

        return authorizationManager;
    }

    @Bean
    public RoleHierarchyImpl roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();

        Map<String, List<String>> roleHierarchyMap = roleHierarchyService.getRoleHierarchyToMap();

        String rolesHierarchyStr = RoleHierarchyUtils.roleHierarchyFromMap(roleHierarchyMap);

        log.debug("role hierarchy={}", rolesHierarchyStr);

        roleHierarchy.setHierarchy(rolesHierarchyStr);

        return roleHierarchy;
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();

        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new JwtRoleGrantAuthoritiesConverter());

        return jwtAuthenticationConverter;
    }
}
