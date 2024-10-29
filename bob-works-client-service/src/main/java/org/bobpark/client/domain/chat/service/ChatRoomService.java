package org.bobpark.client.domain.chat.service;

import static org.bobpark.client.common.utils.CommonUtils.*;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import org.bobpark.client.common.page.Page;
import org.bobpark.client.domain.chat.feign.client.ChatRoomFeignClient;
import org.bobpark.client.domain.chat.model.AddChatRoomUserRequest;
import org.bobpark.client.domain.chat.model.ChatResponse;
import org.bobpark.client.domain.chat.model.ChatRoomResponse;
import org.bobpark.client.domain.chat.model.ChatRoomUserResponse;
import org.bobpark.client.domain.chat.model.SearchChatRoomRequest;
import org.bobpark.client.domain.maintenance.model.CreateChatRoomRequest;
import org.bobpark.client.domain.user.model.UserRequest;
import org.bobpark.client.domain.user.model.UserResponse;

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

    public Page<ChatResponse> getChats(long roomId, Pageable pageable) {
        return chatRoomClient.getAllChats(roomId, pageable);
    }

    public ChatRoomResponse getByRoom(UserResponse user) {

        SearchChatRoomRequest searchRequest =
            SearchChatRoomRequest.builder()
                .userId(user.userId())
                .build();

        List<ChatRoomResponse> rooms = chatRoomClient.getAll(searchRequest);

        if (!rooms.isEmpty()) {
            return rooms.get(0);
        }

        // create room & add user
        ChatRoomResponse createdRoom =
            chatRoomClient.createRoom(
                CreateChatRoomRequest.builder()
                    .name(String.format("%s (%s) 고객의 소리", user.name(), user.userId()))
                    .build());

        chatRoomClient.addRoomUser(
            createdRoom.id(),
            AddChatRoomUserRequest.builder()
                .users(
                    List.of(
                        UserRequest.builder()
                            .userId(user.userId())
                            .build()))
                .build());

        return createdRoom;

    }
}
