package org.bobpark.client.domain.chat.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.bobpark.client.domain.maintenance.model.CreateChatRoomRequest;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class SearchChatRoomRequest {

    private String userId;


}
