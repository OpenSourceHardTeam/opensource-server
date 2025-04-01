package org.opensource.userchatroom.api;

import lombok.RequiredArgsConstructor;
import org.opensource.chatroom.domain.Chatroom;
import org.opensource.chatroom.dto.response.ChatroomResponse;
import org.opensource.common.CommonApiResult;
import org.opensource.user.UserResponse;
import org.opensource.user.domain.User;
import org.opensource.userchatroom.domain.UserChatroom;
import org.opensource.userchatroom.dto.request.JoinUserInChatroomRequest;
import org.opensource.userchatroom.dto.response.JoinUserInChatroomResponse;
import org.opensource.userchatroom.dto.response.UserChatroomResponse;
import org.opensource.userchatroom.facade.UserChatroomFacade;
import org.springframework.http.HttpStatus;
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
        Long userChatroomId = userChatroomFacade.joinUserInChatRoom(
                request.getUserId(), request.getChatroomId(), true);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new JoinUserInChatroomResponse(userChatroomId));
    }

    @Override @GetMapping("/user/{userId}/chatroom/{chatroomId}")
    public ResponseEntity<UserChatroom> getUserChatroom(
            @PathVariable Long userId,
            @PathVariable Long chatroomId) {
        UserChatroom userChatroom = userChatroomFacade.findUserInChatRoom(userId, chatroomId);
        return ResponseEntity.ok(userChatroom);
    }

    @Override @GetMapping("/user/{userId}/chatrooms")
    public ResponseEntity<List<Chatroom>> getUserChatRooms(
            @PathVariable Long userId) {
        List<Chatroom> chatRooms = userChatroomFacade.findChatRoomsUserParticipatesIn(userId);
        return ResponseEntity.ok(chatRooms);
    }

    @Override @GetMapping("/chatroom/{chatroomId}/users")
    public ResponseEntity<List<User>> getUsersInChatroom(
            @PathVariable Long chatroomId) {
        List<User> users = userChatroomFacade.findParticipantsInChatRoom(chatroomId);
        return ResponseEntity.ok(users);
    }

    @Override @GetMapping("/chatrooms/{chatroomId}/users/{userId}/exists")
    public ResponseEntity<Boolean> doesUserExistInChatroom(
            @PathVariable Long userId, @PathVariable Long chatroomId) {
        Boolean doesUserExist = userChatroomFacade.doesUserExistInChatRoom(userId, chatroomId);
        return ResponseEntity.ok(doesUserExist);
    }

    @Override @DeleteMapping("/user/{userId}/chatroom/{chatroomId}")
    public ResponseEntity<?> deleteUserInChatroom(
            @PathVariable Long userId, @PathVariable Long chatroomId) {
        userChatroomFacade.deleteUserAtChatRoomByChatRoomId(userId, chatroomId);
        return ResponseEntity.noContent().build();
    }

    @Override @DeleteMapping("/chatroom/{chatroomId}/users")
    public ResponseEntity<?> deleteAllUsersInChatroom(
            @PathVariable Long chatroomId) {
        userChatroomFacade.deleteAllUsersWhenChatRoomDeleted(chatroomId);
        return ResponseEntity.noContent().build();
    }

    @Override @DeleteMapping("/user/{userId}/chatrooms")
    public ResponseEntity<?> leaveAllChatRoomsThatUserIn(
            @PathVariable Long userId) {
        userChatroomFacade.deleteAllChatRoomsWhenUserDeleted(userId);
        return ResponseEntity.noContent().build();
    }
}
