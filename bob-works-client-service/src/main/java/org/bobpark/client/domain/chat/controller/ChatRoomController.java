package org.bobpark.client.domain.chat.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.client.domain.chat.model.AddChatRoomUserRequest;
import org.bobpark.client.domain.chat.model.ChatRoomResponse;
import org.bobpark.client.domain.chat.model.ChatRoomUserResponse;
import org.bobpark.client.domain.chat.model.SearchChatRoomRequest;
import org.bobpark.client.domain.chat.service.ChatRoomService;
import org.bobpark.client.domain.maintenance.model.CreateChatRoomRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "chat/room")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "")
    public ChatRoomResponse createRoom(@RequestBody CreateChatRoomRequest request) {
        return chatRoomService.createRoom(request);
    }

    @GetMapping(path = "all")
    public List<ChatRoomResponse> getAll(SearchChatRoomRequest searchRequest) {
        return chatRoomService.getAll(searchRequest);
    }

    @GetMapping(path = "{roomId}")
    public ChatRoomResponse getAll(@PathVariable long roomId) {
        return chatRoomService.getRoom(roomId);
    }

    @PostMapping(path = "{roomId}/users")
    public List<ChatRoomUserResponse> getUsers(@PathVariable long roomId,
        @RequestBody AddChatRoomUserRequest addRequest) {
        return chatRoomService.addUsers(roomId, addRequest);
    }

}
