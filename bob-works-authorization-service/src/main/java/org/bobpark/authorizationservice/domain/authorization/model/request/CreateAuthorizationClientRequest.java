package org.bobpark.authorizationservice.domain.authorization.model.request;

import static org.apache.commons.lang3.ObjectUtils.*;

import java.util.Collections;
import java.util.List;

public record CreateAuthorizationClientRequest(
    String clientName,
    List<String> redirectUris,
    List<String> scopes) {

    public CreateAuthorizationClientRequest {
        redirectUris = defaultIfNull(redirectUris, Collections.emptyList());
        scopes = defaultIfNull(scopes, Collections.emptyList());
    }
}
