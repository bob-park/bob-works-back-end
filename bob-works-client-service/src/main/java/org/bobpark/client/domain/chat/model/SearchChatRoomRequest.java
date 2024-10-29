package org.bobpark.client.domain.chat.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class SearchChatRoomRequest {

    private String userId;

    @Builder
    private SearchChatRoomRequest(String userId) {
        this.userId = userId;
    }
}
