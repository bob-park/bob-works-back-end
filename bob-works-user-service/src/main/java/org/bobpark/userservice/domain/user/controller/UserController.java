package org.bobpark.userservice.domain.user.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.model.CheckExistUserResponse;
import org.bobpark.userservice.domain.user.model.CreateUserRequest;
import org.bobpark.userservice.domain.user.model.SearchUserRequest;
import org.bobpark.userservice.domain.user.model.UpdateUserPasswordRequest;
import org.bobpark.userservice.domain.user.model.UserResponse;
import org.bobpark.userservice.domain.user.service.UserCommandService;
import org.bobpark.userservice.domain.user.service.UserQueryService;
import org.bobpark.userservice.domain.user.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    @GetMapping(path = "{id:\\d+}")
    public UserResponse getUserById(@PathVariable long id) {
        return userService.getUserById(Id.of(User.class, id));
    }

    @GetMapping(path = "search")
    public List<UserResponse> search(SearchUserRequest searchRequest) {
        return userService.searchUsers(searchRequest);
    }

    @GetMapping(path = "")
    public UserResponse getUser(@RequestParam("userId") String userId) {
        return userService.getUser(Id.of(User.class, userId));
    }

    @GetMapping(path = "check")
    public CheckExistUserResponse checkUserId(@RequestParam("userId") String userId) {
        return userQueryService.existUserId(userId);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping(path = "")
    public UserResponse createUser(@RequestBody CreateUserRequest createRequest) {
        return userCommandService.createUser(createRequest);
    }

    // @PreAuthorize("hasRole('MANAGER')")
    @GetMapping(path = "all")
    public List<UserResponse> getUsersAll() {
        return userService.getUsersAll();
    }

    @PutMapping(path = "{id:\\d+}/password")
    public UserResponse updatePassword(@PathVariable long id, @RequestBody UpdateUserPasswordRequest updateRequest) {
        return userService.updatePassword(Id.of(User.class, id), updateRequest);
    }

}
