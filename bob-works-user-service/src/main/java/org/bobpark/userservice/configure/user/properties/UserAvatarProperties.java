package org.bobpark.userservice.configure.user.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.core.io.Resource;

@ToString
@Getter
@Setter
public class UserAvatarProperties {

    private String prefix;
    private Resource location;

    public UserAvatarProperties() {
        this.prefix = "http://localhost:8080/user/avatar";
    }
}
