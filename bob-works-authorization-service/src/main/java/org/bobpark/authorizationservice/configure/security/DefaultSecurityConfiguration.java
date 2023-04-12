package org.bobpark.authorizationservice.configure.security;

import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyUtils;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.bobpark.authorizationservice.common.security.handler.RestAccessDeniedHandler;
import org.bobpark.authorizationservice.domain.role.service.RoleHierarchyService;

@Slf4j
@RequiredArgsConstructor
@EnableMethodSecurity
@Configuration
public class DefaultSecurityConfiguration {

    private final RoleHierarchyService roleHierarchyService;
    private final ObjectMapper om;

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(
            requests ->
                requests.anyRequest().authenticated());

        http.formLogin();

        http.exceptionHandling(
            exceptionHandler ->
                exceptionHandler.accessDeniedHandler(accessDeniedHandler()));

        http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    /*
      role hierarchy
     */
    @Bean
    public static MethodSecurityExpressionHandler expressionHandler(RoleHierarchy roleHierarchy) {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();

        expressionHandler.setRoleHierarchy(roleHierarchy);

        return expressionHandler;
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

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new RestAccessDeniedHandler(om);
    }

}
