package org.opensource.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

@Getter
@AllArgsConstructor
public class UserInfo {
    private String name;
    private Long userId;
    private String chatRoomId;
    private WebSocketSession session;
}