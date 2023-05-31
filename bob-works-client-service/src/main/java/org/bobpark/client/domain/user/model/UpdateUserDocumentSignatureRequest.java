package org.bobpark.client.domain.user.model;

import org.springframework.web.multipart.MultipartFile;

public record UpdateUserDocumentSignatureRequest(MultipartFile signature) {
}
