package org.bobpark.authorizationservice.configure.properties;

import lombok.ToString;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ToString
@ConfigurationProperties("authorization")
public class AuthorizationProperties {

    private String issuer;

    public AuthorizationProperties() {
    }

    public AuthorizationProperties(String issuer) {
        this.issuer = issuer;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}
