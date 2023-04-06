package org.bobpark.authorizationservice.domain.authorization.service;

import org.bobpark.authorizationservice.domain.authorization.model.response.AuthorizationClientResponse;
import org.bobpark.authorizationservice.domain.authorization.model.request.CreateAuthorizationClientRequest;

public interface AuthorizationClientService {

    AuthorizationClientResponse createClient(CreateAuthorizationClientRequest createRequest);


}
