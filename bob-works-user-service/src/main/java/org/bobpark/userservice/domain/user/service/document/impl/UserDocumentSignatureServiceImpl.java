package org.bobpark.userservice.domain.user.service.document.impl;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.core.exception.ServiceRuntimeException;
import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.configure.user.properties.UserProperties;
import org.bobpark.userservice.configure.user.properties.UserSignatureProperties;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.entity.UserDocumentSignature;
import org.bobpark.userservice.domain.user.repository.UserRepository;
import org.bobpark.userservice.domain.user.service.document.UserDocumentSignatureService;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserDocumentSignatureServiceImpl implements UserDocumentSignatureService {

    private final UserProperties properties;

    private final UserRepository userRepository;

    @Override
    public Resource getSignature(Id<User, Long> userId) {

        String absolutePath = null;
        User user =
            userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(userId));

        UserDocumentSignature signature = user.getSignature();

        if (signature == null) {
            throw new NotFoundException("No exist signature.");
        }

        try {
            absolutePath = getSignatureProperties().getLocation().getFile().getAbsolutePath();
        } catch (IOException e) {
            throw new ServiceRuntimeException(e);
        }

        return new FileSystemResource(absolutePath + File.separatorChar + signature.getSignaturePath());
    }

    private UserSignatureProperties getSignatureProperties() {
        return properties.getSignature();
    }

}
