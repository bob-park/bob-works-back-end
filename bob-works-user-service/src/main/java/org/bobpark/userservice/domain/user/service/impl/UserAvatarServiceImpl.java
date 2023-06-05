package org.bobpark.userservice.domain.user.service.impl;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.bobpark.userservice.domain.user.model.UserResponse.toResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.core.exception.ServiceRuntimeException;
import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.common.utils.CommonUtils;
import org.bobpark.userservice.configure.user.properties.UserProperties;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.entity.UserAvatar;
import org.bobpark.userservice.domain.user.model.UserResponse;
import org.bobpark.userservice.domain.user.repository.UserAvatarRepository;
import org.bobpark.userservice.domain.user.repository.UserRepository;
import org.bobpark.userservice.domain.user.service.UserAvatarService;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserAvatarServiceImpl implements UserAvatarService {

    private static final List<String> ALLOW_AVATAR_EXTENSION = List.of("jpg", "png");

    private final UserProperties properties;

    private final UserRepository userRepository;
    private final UserAvatarRepository userAvatarRepository;

    @Transactional
    @Override
    public UserResponse updateAvatar(Id<User, Long> userId, MultipartFile avatar) {

        User user =
            userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(userId));

        String fileName = null;

        if (avatar != null) {

            String extension = FilenameUtils.getExtension(avatar.getOriginalFilename());

            if (extension == null || !ALLOW_AVATAR_EXTENSION.contains(extension.toLowerCase())) {
                throw new ServiceRuntimeException("avatar file extension must be 'jpg' or 'png'.");
            }

            String avatarFileName = UUID.randomUUID() + "." + extension;

            fileName = CommonUtils.generateDateFolderPath() + File.separatorChar + avatarFileName;

            try {

                String filePath =
                    properties.getAvatar().getLocation().getFile().getAbsolutePath() + File.separatorChar
                        + fileName;

                File avatarFile = new File(filePath);

                FileUtils.forceMkdirParent(avatarFile);

                FileCopyUtils.copy(avatar.getInputStream(), new FileOutputStream(avatarFile));

                log.debug("completed copy user avatar. (path={})", filePath);
            } catch (IOException e) {
                throw new ServiceRuntimeException(e);
            }
        }

        if (isNotEmpty(user.getAvatar())) {
            user.setAvatar(fileName);
        } else {
            UserAvatar createdAvatar = new UserAvatar();
            createdAvatar.setUser(user);
            createdAvatar.updateAvatarPath(fileName);

            userAvatarRepository.save(createdAvatar);
        }

        return toResponse(user, properties.getAvatar().getPrefix());
    }
}
