package org.bobpark.maintenanceservice.common.utils.user;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.bobpark.maintenanceservice.domain.user.feign.client.UserClient;
import org.bobpark.maintenanceservice.domain.user.model.UserResponse;

@Slf4j
public class UserProvider {

    private UserClient userClient;

    private static UserProvider instance;

    public static UserProvider getInstance() {
        if (instance == null) {
            instance = new UserProvider();
        }

        return instance;
    }

    public long getUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserResponse user = userClient.getUser(auth.getName());

        return user.id();
    }

    public UserResponse getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return userClient.getUser(auth.getName());
    }

    public String getName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return auth.getName();
    }

    @Autowired
    public void setUserClient(UserClient userClient) {
        this.userClient = userClient;
    }
}
