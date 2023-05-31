package org.bobpark.userservice.domain.user.service.document;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.model.UserResponse;

public interface UserDocumentSignatureService {

    Resource getSignature(Id<User, Long> userId);

    UserResponse updateSignature(Id<User, Long> userId, MultipartFile signatureFile);

}
