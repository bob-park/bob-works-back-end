package org.bobpark.authorizationservice.domain.authorization.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.bobpark.authorizationservice.domain.authorization.entity.AuthorizationClient;
import org.bobpark.authorizationservice.domain.authorization.entity.AuthorizationClientScope;
import org.bobpark.authorizationservice.domain.authorization.model.response.AuthorizationScopeResponse;
import org.bobpark.authorizationservice.domain.authorization.repository.AuthorizationClientRepository;
import org.bobpark.authorizationservice.domain.user.entity.User;
import org.bobpark.authorizationservice.domain.user.repository.UserRepository;
import org.bobpark.core.exception.NotFoundException;

@RequiredArgsConstructor
@Controller
@RequestMapping("oauth2/consent")
public class AuthorizationConsentController {

    private final OAuth2AuthorizationConsentService consentService;

    private final AuthorizationClientRepository clientRepository;
    private final UserRepository userRepository;

    @GetMapping(path = "")
    public String consent(Principal principal, Model model,
        @RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId,
        @RequestParam(OAuth2ParameterNames.SCOPE) String scope,
        @RequestParam(OAuth2ParameterNames.STATE) String state) {

        AuthorizationClient client = clientRepository.findByClientId(clientId).orElseThrow();
        OAuth2AuthorizationConsent consent = consentService.findById(clientId, principal.getName());

        User user =
            userRepository.findByUserId(principal.getName())
                .orElseThrow(() -> new NotFoundException(User.class, principal.getName()));

        List<AuthorizationScopeResponse> scopes =
            client.getScopes().stream()
                .map(AuthorizationClientScope::getScope)
                .map(item ->
                    AuthorizationScopeResponse.builder()
                        .id(item.getId())
                        .scope(item.getScope())
                        .description(item.getDescription())
                        .build())
                .toList();

        List<AuthorizationScopeResponse> prevScopes =
            consent == null ?
                Collections.emptyList() :
                scopes.stream()
                    .filter(item -> consent.getScopes().contains(item.scope()))
                    .toList();

        List<AuthorizationScopeResponse> checkableScopes =
            scopes.stream()
                .filter(item -> prevScopes.stream().noneMatch(prev -> prev.scope().equals(item.scope())))
                .toList();

        model.addAttribute("clientName", client.getClientName());
        model.addAttribute("clientId", clientId);
        model.addAttribute("state", state);

        model.addAttribute("userId", user.getUserId());
        model.addAttribute("username", user.getName());

        model.addAttribute("checkableScopes", checkableScopes);
        model.addAttribute("prevScopes", prevScopes);

        return "consent";
    }

}
