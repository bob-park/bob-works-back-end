package org.bobpark.userservice.configure.user.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class UserAvatarProperties {

    private String prefix;

    public UserAvatarProperties() {
        this.prefix = "http://localhost:8080/user/avatar";
    }
}
