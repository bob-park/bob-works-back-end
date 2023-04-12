package org.bobpark.authorizationservice.domain.authorization.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.authorizationservice.domain.authorization.model.request.CreateAuthorizationClientRequest;
import org.bobpark.authorizationservice.domain.authorization.model.response.AuthorizationClientResponse;
import org.bobpark.authorizationservice.domain.authorization.service.AuthorizationClientService;

@RequiredArgsConstructor
@RestController
@RequestMapping("authorization/client")
@PreAuthorize("hasRole('MANAGER')")
public class AuthorizationClientController {

    private final AuthorizationClientService clientService;

    @PostMapping(path = "")
    public AuthorizationClientResponse addClient(@RequestBody CreateAuthorizationClientRequest createRequest) {
        return clientService.createClient(createRequest);
    }

}
