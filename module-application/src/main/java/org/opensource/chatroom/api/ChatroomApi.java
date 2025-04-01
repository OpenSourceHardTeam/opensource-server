package org.opensource.chatroom.api;

import jakarta.validation.Valid;
import org.opensource.CommonApiResult;
import org.opensource.chatroom.dto.request.CreateChatroomRequest;
import org.opensource.chatroom.dto.response.ChatroomResponse;
import org.opensource.chatroom.dto.response.CreateChatroomResponse;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ChatroomApi {

    ResponseEntity<CreateChatroomResponse> createChatroom(
            @Valid @RequestBody CreateChatroomRequest request);

    ResponseEntity<ChatroomResponse> getChatroom(
            @PathVariable Long id);

    ResponseEntity<CommonApiResult> deleteChatroom(
            @PathVariable Long id);
}
