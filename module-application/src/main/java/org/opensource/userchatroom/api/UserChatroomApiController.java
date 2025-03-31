package org.opensource.userchatroom.api;

import lombok.RequiredArgsConstructor;
import org.opensource.chatroom.dto.response.ChatroomResponse;
import org.opensource.common.CommonApiResult;
import org.opensource.user.UserResponse;
import org.opensource.userchatroom.dto.request.JoinUserInChatroomRequest;
import org.opensource.userchatroom.dto.response.JoinUserInChatroomResponse;
import org.opensource.userchatroom.dto.response.UserChatroomResponse;
import org.opensource.userchatroom.facade.UserChatroomFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user-chatroom")
public class UserChatroomApiController implements UserChatroomApi {

    private final UserChatroomFacade userChatroomFacade;

    @Override @PostMapping
    public ResponseEntity<JoinUserInChatroomResponse> joinUserInChatroom(
            @RequestBody JoinUserInChatroomRequest request) {
        return null;
    }

    @Override @GetMapping("/user/{userId}/chatroom/{chatroomId}")
    public ResponseEntity<UserChatroomResponse> getUserChatroom(
            @PathVariable Long userId,
            @PathVariable Long chatroomId) {
        return null;
    }

    @Override @GetMapping("/user/{userId}/chatrooms")
    public ResponseEntity<List<ChatroomResponse>> getUserChatrooms(
            @PathVariable Long userId) {
        return null;
    }

    @Override @GetMapping("/chatroom/{chatroomId}/users")
    public ResponseEntity<List<UserResponse>> getUsersInChatroom(
            @PathVariable Long chatroomId) {
        return null;
    }

    @Override @GetMapping("/check-user-in-chatroom/user/{userId}/chatroom/{chatroomId}")
    public ResponseEntity<Boolean> doesUserExistInChatroom(
            @PathVariable Long userId, @PathVariable Long chatroomId) {
        return null;
    }

    @Override @DeleteMapping("/user/{userId}/chatroom/{chatroomId}")
    public ResponseEntity<CommonApiResult> deleteUserInChatroom(
            @PathVariable Long userId, @PathVariable Long chatroomId) {
        return null;
    }

    @Override @DeleteMapping("/chatroom/{chatroomId}/users")
    public ResponseEntity<CommonApiResult> deleteAllUsersInChatroom(
            @PathVariable Long chatroomId) {
        return null;
    }

    @Override @DeleteMapping("/user/{userId}/chatrooms")
    public ResponseEntity<CommonApiResult> deleteAllChatroomsThatUserIn(
            @PathVariable Long userId) {
        return null;
    }
}
