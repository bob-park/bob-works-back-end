package org.bobpark.client.domain.chat.service;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import org.bobpark.client.domain.chat.feign.client.ChatRoomFeignClient;
import org.bobpark.client.domain.chat.model.AddChatRoomUserRequest;
import org.bobpark.client.domain.chat.model.ChatRoomResponse;
import org.bobpark.client.domain.chat.model.ChatRoomUserResponse;
import org.bobpark.client.domain.chat.model.SearchChatRoomRequest;
import org.bobpark.client.domain.maintenance.model.CreateChatRoomRequest;

@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatRoomFeignClient chatRoomClient;

    public ChatRoomResponse createRoom(CreateChatRoomRequest createRequest) {
        return chatRoomClient.createRoom(createRequest);
    }

    public List<ChatRoomResponse> getAll(SearchChatRoomRequest searchRequest) {
        return chatRoomClient.getAll(searchRequest);
    }

    public ChatRoomResponse getRoom(long roomId) {
        return chatRoomClient.getById(roomId);
    }

    public List<ChatRoomUserResponse> addUsers(long roomId, AddChatRoomUserRequest addRequest) {
        return chatRoomClient.addRoomUser(roomId, addRequest);
    }
}
