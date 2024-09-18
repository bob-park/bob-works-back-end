package org.bobpark.client.domain.maintenance.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.client.common.page.Page;
import org.bobpark.client.domain.maintenance.feign.client.CustomerChatClient;
import org.bobpark.client.domain.maintenance.model.CreateChatRequest;
import org.bobpark.client.domain.maintenance.model.CreateChatRoomRequest;
import org.bobpark.client.domain.maintenance.model.CustomerChatResponse;
import org.bobpark.client.domain.maintenance.model.CustomerChatRoomResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "maintenance/customer/chat")
public class CustomerChatController {

    private final CustomerChatClient chatClient;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "{roomId}")
    public CustomerChatResponse createChat(@PathVariable String roomId, @RequestBody CreateChatRequest createRequest) {
        return chatClient.createChat(roomId, createRequest);
    }

    @GetMapping(path = "{roomId}")
    public Page<CustomerChatResponse> searchByRoom(@PathVariable String roomId) {
        return chatClient.searchByRoom(roomId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "room")
    public CustomerChatRoomResponse createRoom(@RequestBody CreateChatRoomRequest createRequest) {
        return chatClient.createRoom(createRequest);
    }

    @GetMapping(path = "room/all")
    public Page<CustomerChatRoomResponse> searchByRoomAll(Pageable pageable) {
        return chatClient.getAll(pageable);
    }

    @GetMapping(path = "room/latest")
    public CustomerChatRoomResponse getLatestChatRoom() {
        return chatClient.getLatestChatRoom();
    }

    @GetMapping(path = "room/{roomId}")
    public CustomerChatRoomResponse getLatestChatRoom(@PathVariable String roomId) {
        return chatClient.getChatRoom(roomId);
    }

}
