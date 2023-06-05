package org.bobpark.userservice.domain.user.model;

import org.springframework.web.multipart.MultipartFile;

public record UpdateUserAvatarRequest(MultipartFile avatar) {
}
