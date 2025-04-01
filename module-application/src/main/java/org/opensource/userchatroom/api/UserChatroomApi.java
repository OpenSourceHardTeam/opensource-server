package org.opensource.userchatroom.api;

import jakarta.validation.Valid;
import org.opensource.chatroom.domain.Chatroom;
import org.opensource.common.CommonApiResult;
import org.opensource.user.UserResponse;
import org.opensource.chatroom.dto.response.ChatroomResponse;
import org.opensource.user.domain.User;
import org.opensource.userchatroom.domain.UserChatroom;
import org.opensource.userchatroom.dto.request.JoinUserInChatroomRequest;
import org.opensource.userchatroom.dto.response.JoinUserInChatroomResponse;
import org.opensource.userchatroom.dto.response.UserChatroomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserChatroomApi {

    ResponseEntity<JoinUserInChatroomResponse> joinUserInChatroom(
            @Valid @RequestBody JoinUserInChatroomRequest request);

    ResponseEntity<UserChatroom> getUserChatroom(
            @PathVariable Long userId, @PathVariable Long chatroomId);

    ResponseEntity<List<Chatroom>> getUserChatRooms(
            @PathVariable Long userId);

    ResponseEntity<List<User>> getUsersInChatroom(
            @PathVariable Long chatroomId);

    ResponseEntity<Boolean> doesUserExistInChatroom(
            @PathVariable Long userId, @PathVariable Long chatroomId);

    ResponseEntity<?> deleteUserInChatroom(
            @PathVariable Long userId, @PathVariable Long chatroomId);

    ResponseEntity<?> deleteAllUsersInChatroom(
            @PathVariable Long chatroomId);

    ResponseEntity<?> leaveAllChatRoomsThatUserIn(
            @PathVariable Long userId);
}
