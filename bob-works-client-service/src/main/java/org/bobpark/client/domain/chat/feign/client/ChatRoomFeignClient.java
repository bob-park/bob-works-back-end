package org.bobpark.client.domain.chat.feign.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.bobpark.client.common.page.Page;
import org.bobpark.client.common.page.PageInfo;
import org.bobpark.client.domain.chat.model.AddChatRoomUserRequest;
import org.bobpark.client.domain.chat.model.ChatResponse;
import org.bobpark.client.domain.chat.model.ChatRoomResponse;
import org.bobpark.client.domain.chat.model.ChatRoomUserResponse;
import org.bobpark.client.domain.chat.model.SearchChatRoomRequest;
import org.bobpark.client.domain.maintenance.model.CreateChatRoomRequest;

@FeignClient(name = "bob-chats-service", contextId = "bob-chats-service")
public interface ChatRoomFeignClient {

    @PostMapping(path = "chat/room")
    ChatRoomResponse createRoom(@RequestBody CreateChatRoomRequest createRequest);

    @GetMapping(path = "chat/room/all")
    List<ChatRoomResponse> getAll(@SpringQueryMap SearchChatRoomRequest searchRequest);

    @GetMapping(path = "chat/room/{roomId}")
    ChatRoomResponse getById(@PathVariable long roomId);

    @GetMapping(path = "chat/room/{roomId}/chats")
    Page<ChatResponse> getAllChats(@PathVariable long roomId, Pageable pageable);

    @PostMapping(path = "chat/room/{roomId}/users")
    List<ChatRoomUserResponse> addRoomUser(@PathVariable long roomId, @RequestBody AddChatRoomUserRequest addRequest);
}
