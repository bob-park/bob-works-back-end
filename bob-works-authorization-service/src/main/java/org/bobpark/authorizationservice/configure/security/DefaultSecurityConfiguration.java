package org.bobpark.authorizationservice.configure.security;

import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyUtils;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

import org.bobpark.authorizationservice.common.security.handler.RestAccessDeniedHandler;
import org.bobpark.authorizationservice.domain.role.model.RoleResponse;
import org.bobpark.authorizationservice.domain.role.service.RoleService;

@Slf4j
@RequiredArgsConstructor
@EnableMethodSecurity
@EnableWebSecurity
@Configuration
public class DefaultSecurityConfiguration {

    private final RoleService roleService;
    private final ObjectMapper om;

    @Bean
    @Order
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(
            requests ->
                requests
                    .requestMatchers("/role/**").permitAll()
                    .requestMatchers("/login").permitAll()
                    .anyRequest().authenticated());

        http.formLogin(formLogin -> formLogin.loginPage("/login"));

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

        Map<String, List<String>> roleHierarchyMap = parseRoleHierarchyMap();
        String rolesHierarchyStr = RoleHierarchyUtils.roleHierarchyFromMap(roleHierarchyMap);

        log.debug("role hierarchy={}", rolesHierarchyStr);

        roleHierarchy.setHierarchy(rolesHierarchyStr);

        return roleHierarchy;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new RestAccessDeniedHandler(om);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    private Map<String, List<String>> parseRoleHierarchyMap() {

        Map<String, List<String>> result = Maps.newHashMap();

        List<RoleResponse> roles = roleService.getRoles();

        for (RoleResponse role : roles) {

            if (role.children().isEmpty()) {
                continue;
            }

            result.put(role.roleName(), role.children().stream().map(RoleResponse::roleName).toList());
        }

        return result;
    }

}
