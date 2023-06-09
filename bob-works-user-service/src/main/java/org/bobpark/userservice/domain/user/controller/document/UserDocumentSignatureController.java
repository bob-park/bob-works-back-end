package org.bobpark.userservice.domain.user.controller.document;

import lombok.RequiredArgsConstructor;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.model.UserResponse;
import org.bobpark.userservice.domain.user.service.document.UserDocumentSignatureService;

@RequiredArgsConstructor
@RestController
@RequestMapping("user/{id:\\d+}/document/signature")
public class UserDocumentSignatureController {

    private final UserDocumentSignatureService userDocumentSignatureService;

    @GetMapping(path = "")
    public ResponseEntity<Resource> getSignature(@PathVariable long id) {

        Resource signature = userDocumentSignatureService.getSignature(Id.of(User.class, id));

        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_PNG)
            .body(signature);
    }

    @PostMapping(path = "")
    public UserResponse updateSignature(@PathVariable long id, @RequestPart("signature") MultipartFile signatureFile) {
        return userDocumentSignatureService.updateSignature(Id.of(User.class, id), signatureFile);
    }

}
