package org.bobpark.client.domain.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import org.bobpark.client.domain.user.model.UpdateUserPasswordRequest;
import org.bobpark.client.domain.user.model.UserResponse;

@FeignClient(name = "user-service", path = "user")
public interface UserClient {

    @GetMapping(path = "")
    UserResponse getUser(@RequestParam("userId") String userId);

    @GetMapping(path = "{id}")
    UserResponse getUserById(@PathVariable long id);

    @PutMapping(path = "{id}/password")
    UserResponse updatePassword(@PathVariable long id, UpdateUserPasswordRequest updateRequest);

    @PostMapping(path = "{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    UserResponse updateAvatar(@PathVariable long id, @RequestPart("avatar") MultipartFile avatar);

    @GetMapping(path = "{id}/document/signature")
    Resource getDocumentSignature(@PathVariable long id);

    @PostMapping(path = "{id}/document/signature", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    UserResponse updateSignature(@PathVariable long id, @RequestPart("signature") MultipartFile signatureFile);
}
