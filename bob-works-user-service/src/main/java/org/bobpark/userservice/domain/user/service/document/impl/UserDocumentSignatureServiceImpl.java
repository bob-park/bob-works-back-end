package org.bobpark.userservice.domain.user.service.document.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.bobpark.userservice.domain.user.model.UserResponse.toResponse;

import java.io.File;
import java.io.FileOutputStream;
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
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.core.exception.ServiceRuntimeException;
import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.common.utils.CommonUtils;
import org.bobpark.userservice.configure.user.properties.UserProperties;
import org.bobpark.userservice.configure.user.properties.UserSignatureProperties;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.entity.UserDocumentSignature;
import org.bobpark.userservice.domain.user.model.UserResponse;
import org.bobpark.userservice.domain.user.repository.UserRepository;
import org.bobpark.userservice.domain.user.service.document.UserDocumentSignatureService;
import org.bobpark.userservice.exception.support.NotSupportedException;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserDocumentSignatureServiceImpl implements UserDocumentSignatureService {

    private static final List<String> ALLOW_SIGNATURE_FILE_EXTENSIONS = List.of("jpg", "jpeg", "png");

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

    @Transactional
    @Override
    public UserResponse updateSignature(Id<User, Long> userId, MultipartFile signatureFile) {

        checkArgument(isNotEmpty(signatureFile), "signatureFile must be provided.");

        User user =
            userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(userId));

        String extension = FilenameUtils.getExtension(signatureFile.getOriginalFilename());

        checkSignatureFileExtension(extension);

        String fileName = UUID.randomUUID() + "." + extension;

        String relativeFilePath = CommonUtils.generateDateFolderPath() + File.separatorChar + fileName;

        // saved file.
        try {
            String absolutePath =
                getSignatureProperties().getLocation().getFile().getAbsolutePath()
                    + File.separatorChar
                    + relativeFilePath;

            FileUtils.forceMkdirParent(new File(absolutePath));

            FileCopyUtils.copy(signatureFile.getInputStream(), new FileOutputStream(absolutePath));
        } catch (IOException e) {
            throw new ServiceRuntimeException(e);
        }

        user.updateSignature(relativeFilePath);

        log.debug("updated user document signature files. ({})", relativeFilePath);

        return toResponse(user, properties.getAvatar().getPrefix());
    }

    private UserSignatureProperties getSignatureProperties() {
        return properties.getSignature();
    }

    private void checkSignatureFileExtension(String extension) {

        checkArgument(isNotBlank(extension), "extension must be provided.");

        if (!ALLOW_SIGNATURE_FILE_EXTENSIONS.contains(extension.toLowerCase())) {
            throw new NotSupportedException(extension + " - file extension");
        }
    }

}
