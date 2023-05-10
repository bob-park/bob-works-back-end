package org.bobpark.userservice.configure.user;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import org.bobpark.userservice.configure.user.properties.UserProperties;

@RequiredArgsConstructor
@EnableConfigurationProperties(UserProperties.class)
@Configuration
public class UserConfiguration {
}
