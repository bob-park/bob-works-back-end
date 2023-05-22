package org.bobpark.userservice.domain.user.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;

import org.bobpark.userservice.domain.user.entity.UserDocumentSignature;

public interface UserDocumentSignatureRepository extends JpaRepository<UserDocumentSignature, Long> {
}
