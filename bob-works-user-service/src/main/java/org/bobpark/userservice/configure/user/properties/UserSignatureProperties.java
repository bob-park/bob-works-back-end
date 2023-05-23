package org.bobpark.userservice.configure.user.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.core.io.Resource;

@ToString
@Getter
@Setter
public class UserSignatureProperties {

    private Resource location;



}
