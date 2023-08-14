package org.bobpark.maintenanceservice.domain.maintenance.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.maintenanceservice.domain.maintenance.entity.CustomerChatRoomId;
import org.bobpark.maintenanceservice.domain.maintenance.model.CreateChatRequest;
import org.bobpark.maintenanceservice.domain.maintenance.model.CreateChatRoomRequest;
import org.bobpark.maintenanceservice.domain.maintenance.model.CustomerChatResponse;
import org.bobpark.maintenanceservice.domain.maintenance.model.CustomerChatRoomResponse;
import org.bobpark.maintenanceservice.domain.maintenance.service.CustomerChatCommandService;
import org.bobpark.maintenanceservice.domain.maintenance.service.CustomerChatQueryService;
import org.bobpark.maintenanceservice.domain.maintenance.service.CustomerChatRoomCommandService;
import org.bobpark.maintenanceservice.domain.maintenance.service.CustomerChatRoomQueryService;

@RequiredArgsConstructor
@RestController
@RequestMapping("maintenance/customer/chat")
public class CustomerChatController {

    private final CustomerChatRoomQueryService chatRoomQueryService;
    private final CustomerChatRoomCommandService chatRoomCommandService;
    private final CustomerChatCommandService chatCommandService;
    private final CustomerChatQueryService chatQueryService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "{roomId}")
    public CustomerChatResponse createChat(@PathVariable String roomId, @RequestBody CreateChatRequest createRequest) {
        return chatCommandService.createChat(new CustomerChatRoomId(roomId), createRequest);
    }

    @GetMapping(path = "{roomId}")
    public Page<CustomerChatResponse> searchByRoom(@PathVariable String roomId,
        @PageableDefault Pageable pageable) {
        return chatQueryService.searchChatByRoom(new CustomerChatRoomId(roomId), pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "room")
    public CustomerChatRoomResponse createRoom(@RequestBody CreateChatRoomRequest createRequest) {
        return chatRoomCommandService.createRoom(createRequest);
    }

    @GetMapping(path = "room/latest")
    public CustomerChatRoomResponse getLatestChatRoom() {
        return chatRoomQueryService.getLatestChatRoom();
    }

}
