package org.bobpark.userservice.configure.user.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ToString
@Getter
@Setter
@ConfigurationProperties("user")
public class UserProperties {

    @NestedConfigurationProperty
    private UserAvatarProperties avatar;

    public UserProperties() {
        this.avatar = new UserAvatarProperties();
    }
}
