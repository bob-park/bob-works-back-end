package org.bobpark.userservice.domain.user.service.document;

import org.springframework.core.io.Resource;

import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.domain.user.entity.User;

public interface UserDocumentSignatureService {

    Resource getSignature(Id<User, Long> userId);

}
