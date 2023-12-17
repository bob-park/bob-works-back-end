package org.bobpark.maintenanceservice.domain.maintenance.controller;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.bobpark.maintenanceservice.domain.maintenance.model.CustomerChatResponse;
import org.bobpark.maintenanceservice.domain.maintenance.model.CustomerChatRoomResponse;
import org.bobpark.maintenanceservice.domain.maintenance.service.CustomerChatCommandService;

@AutoConfigureRestDocs
@WebMvcTest(CustomerChatController.class)
class CustomerChatControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerChatCommandService chatCommandService;

    @BeforeEach
    void setup() {
        CustomerChatResponse chat =
            CustomerChatResponse.builder()
                .id("test-chat-id")
                .room(
                    CustomerChatRoomResponse.builder()
                        .id("test-room-id")
                        .build()
                )
                .writerId(1L)
                .contents("test")
                .build();

        when(chatCommandService.createChat(any(), any())).thenReturn(chat);
    }

    @Test
    void createChat() throws Exception {

        mockMvc.perform(
            post("/maintenance/customer/chat"));
    }
}