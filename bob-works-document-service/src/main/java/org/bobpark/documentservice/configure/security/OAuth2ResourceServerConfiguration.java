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
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.bobpark.documentservice.common.security.handler.RestAuthenticationEntryPoint;
import org.bobpark.documentservice.configure.security.converter.JwtRoleGrantAuthoritiesConverter;
import org.bobpark.documentservice.domain.role.service.RoleHierarchyService;

@Slf4j
@RequiredArgsConstructor
@EnableMethodSecurity
@Configuration
public class OAuth2ResourceServerConfiguration {

    private final RoleHierarchyService roleHierarchyService;
    private final ObjectMapper om;

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

        http.exceptionHandling(
            exceptionHandler ->
                exceptionHandler.authenticationEntryPoint(authenticationEntryPoint()));

        return http.build();
    }

    /*
      role hierarchy
     */

    /**
     * Spring Security 6 의 api 를 사용할 경우 더이상 {@link EnableGlobalMethodSecurity} 를 사용하여 method security (ex:) @PreAuthorize(..) 등) 에 사용이 되지 않는다.
     * <p>
     * Spring Security 6 부터 {@link EnableMethodSecurity} 를 사용해야하며, method security 를 custom 하려면 (ex:) role hierarchy) 아래와 같이 써야한다.
     * <p>
     * ! 반드시 static method 로 선언해주어야 한다.
     *
     * @param roleHierarchy role 계층
     * @return void
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

    /*
    authentication entry point
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new RestAuthenticationEntryPoint(om);
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();

        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new JwtRoleGrantAuthoritiesConverter());

        return jwtAuthenticationConverter;
    }
}
