package org.bobpark.maintenanceservice.domain.maintenance.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.maintenanceservice.domain.maintenance.model.CreateChatRoomRequest;
import org.bobpark.maintenanceservice.domain.maintenance.model.CustomerChatRoomResponse;
import org.bobpark.maintenanceservice.domain.maintenance.service.CustomerChatRoomCommandService;

@RequiredArgsConstructor
@RestController
@RequestMapping("maintenance/customer/chat/room")
public class CustomerChatRoomController {

    private final CustomerChatRoomCommandService chatRoomCommandService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "")
    public CustomerChatRoomResponse createRoom(@RequestBody CreateChatRoomRequest createRequest){
        return chatRoomCommandService.createRoom(createRequest);
    }

}
