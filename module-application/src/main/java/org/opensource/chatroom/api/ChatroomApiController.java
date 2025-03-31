package org.opensource.chatroom.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.opensource.common.CommonApiResult;
import org.opensource.chatroom.domain.Chatroom;
import org.opensource.chatroom.dto.request.CreateChatroomRequest;
import org.opensource.chatroom.dto.response.ChatroomResponse;
import org.opensource.chatroom.dto.response.CreateChatroomResponse;
import org.opensource.chatroom.facade.ChatroomFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chatroom")
public class ChatroomApiController implements ChatroomApi {

    private final ChatroomFacade chatroomFacade;

    @PostMapping()
    public ResponseEntity<CreateChatroomResponse> createChatroom(
           @Valid @RequestBody CreateChatroomRequest request
    ) {
        Long chatroomId = chatroomFacade.createChatroom(request);
        return ResponseEntity.ok(new CreateChatroomResponse(chatroomId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatroomResponse> getChatroom(
            @PathVariable Long id
    ) {
        Chatroom chatroom = chatroomFacade.findChatroomById(id);
        return ResponseEntity.ok(new ChatroomResponse(chatroom.getId(), chatroom.getTopic(), chatroom.getBook().getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonApiResult> deleteChatroom(
            @PathVariable Long id
    ) {
        chatroomFacade.deleteChatroom(id);
        return ResponseEntity.ok(CommonApiResult.createOk());
    }
}
