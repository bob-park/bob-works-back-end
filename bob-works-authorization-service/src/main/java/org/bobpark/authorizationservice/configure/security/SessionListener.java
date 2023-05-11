package org.bobpark.authorizationservice.configure.security;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import org.springframework.stereotype.Component;

@Component
public class SessionListener implements HttpSessionListener {

    private static final int MAX_SESSION_TIMEOUT = 5;

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        event.getSession().setMaxInactiveInterval(MAX_SESSION_TIMEOUT);
    }
}
