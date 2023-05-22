package org.bobpark.userservice.domain.user.service.document.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.entity.UserDocumentSignature;
import org.bobpark.userservice.domain.user.repository.UserRepository;
import org.bobpark.userservice.domain.user.service.document.UserDocumentSignatureService;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserDocumentSignatureServiceImpl implements UserDocumentSignatureService {

    private final UserRepository userRepository;

    @Override
    public Resource getSignature(Id<User, Long> userId) {

        User user =
            userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(userId));

        UserDocumentSignature signature = user.getSignature();

        if (signature == null) {
            throw new NotFoundException("No exist signature.");
        }

        return new InputStreamResource(signature.getSignature());
    }
}
