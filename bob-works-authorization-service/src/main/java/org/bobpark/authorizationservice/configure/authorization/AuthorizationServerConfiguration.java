package org.bobpark.authorizationservice.configure.authorization;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.bobpark.authorizationservice.authorization.client.JpaRegisteredClientRepository;
import org.bobpark.authorizationservice.domain.authorization.repository.AuthorizationClientRepository;
import org.bobpark.authorizationservice.domain.authorization.repository.AuthorizationScopeRepository;

@RequiredArgsConstructor
@Configuration
public class AuthorizationServerConfiguration {

    private final AuthorizationClientRepository clientRepository;
    private final AuthorizationScopeRepository scopeRepository;

    @Bean
    public JpaRegisteredClientRepository registeredClientRepository() {
        return new JpaRegisteredClientRepository(clientRepository, scopeRepository);
    }

}
