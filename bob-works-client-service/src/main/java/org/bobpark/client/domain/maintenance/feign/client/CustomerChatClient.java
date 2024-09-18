package org.bobpark.client.domain.maintenance.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.bobpark.client.common.page.Page;
import org.bobpark.client.domain.maintenance.model.CreateChatRequest;
import org.bobpark.client.domain.maintenance.model.CreateChatRoomRequest;
import org.bobpark.client.domain.maintenance.model.CustomerChatResponse;
import org.bobpark.client.domain.maintenance.model.CustomerChatRoomResponse;

@FeignClient(name = "maintenance-service", contextId = "customer-chat-service")
public interface CustomerChatClient {

    @PostMapping(path = "maintenance/customer/chat/{roomId}")
    CustomerChatResponse createChat(@PathVariable String roomId, @RequestBody CreateChatRequest createRequest);

    @GetMapping(path = "maintenance/customer/chat/{roomId}")
    Page<CustomerChatResponse> searchByRoom(@PathVariable String roomId);

    @PostMapping(path = "maintenance/customer/chat/room")
    CustomerChatRoomResponse createRoom(@RequestBody CreateChatRoomRequest createRequest);

    @GetMapping(path = "maintenance/customer/chat/room/all")
    Page<CustomerChatRoomResponse> getAll(Pageable pageable);

    @GetMapping(path = "maintenance/customer/chat/room/latest")
    CustomerChatRoomResponse getLatestChatRoom();

    @GetMapping(path = "maintenance/customer/chat/room/{roomId}")
    CustomerChatRoomResponse getChatRoom(@PathVariable String roomId);
}

