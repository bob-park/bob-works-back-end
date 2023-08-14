package org.bobpark.client.domain.maintenance.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.bobpark.client.common.page.Page;
import org.bobpark.client.domain.maintenance.model.CreateChatRequest;
import org.bobpark.client.domain.maintenance.model.CreateChatRoomRequest;
import org.bobpark.client.domain.maintenance.model.CustomerChatResponse;
import org.bobpark.client.domain.maintenance.model.CustomerChatRoomResponse;

@FeignClient(name = "maintenance-service", path = "maintenance/customer/chat")
public interface CustomerChatClient {

    @PostMapping(path = "{roomId}")
    CustomerChatResponse createChat(@PathVariable String roomId, @RequestBody CreateChatRequest createRequest);

    @GetMapping(path = "{roomId}")
    Page<CustomerChatResponse> searchByRoom(@PathVariable String roomId);

    @PostMapping(path = "room")
    CustomerChatRoomResponse createRoom(@RequestBody CreateChatRoomRequest createRequest);

    @GetMapping(path = "room/latest")
    CustomerChatRoomResponse getLatestChatRoom();
}
