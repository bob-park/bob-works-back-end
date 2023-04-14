package org.bobpark.documentservice.common.utils.authentication;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.bobpark.documentservice.domain.user.feign.client.UserClient;
import org.bobpark.documentservice.domain.user.model.UserResponse;

public class AuthenticationUtils {

    private static final String ROLE_NAME_MANAGER = "ROLE_MANAGER";

    private static AuthenticationUtils instance;

    private RoleHierarchy roleHierarchy;

    private UserClient userClient;

    public static AuthenticationUtils getInstance() {
        if (instance == null) {
            instance = new AuthenticationUtils();
        }

        return instance;
    }

    public boolean isManager() {
        Authentication authentication = getAuthentication();

        return roleHierarchy.getReachableGrantedAuthorities(authentication.getAuthorities())
            .contains(new SimpleGrantedAuthority(ROLE_NAME_MANAGER));
    }

    public List<UserResponse> getUsersByPrincipal() {

        if (isManager()) {
            return getUsers();
        }

        Authentication authentication = getAuthentication();

        return Collections.singletonList(getUser(authentication.getName()));

    }

    public List<UserResponse> getUsers() {
        return userClient.getUserAll();
    }

    public UserResponse getUser(String userId) {
        return userClient.getUser(userId);
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Autowired
    public void setRoleHierarchy(RoleHierarchy roleHierarchy) {
        this.roleHierarchy = roleHierarchy;
    }

    @Autowired
    public void setUserClient(UserClient userClient) {
        this.userClient = userClient;
    }
}
