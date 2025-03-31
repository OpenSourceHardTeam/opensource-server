package org.opensource.userchatroom.api;

import org.opensource.common.CommonApiResult;
import org.opensource.user.UserResponse;
import org.opensource.chatroom.dto.response.ChatroomResponse;
import org.opensource.userchatroom.dto.request.JoinUserInChatroomRequest;
import org.opensource.userchatroom.dto.response.JoinUserInChatroomResponse;
import org.opensource.userchatroom.dto.response.UserChatroomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserChatroomApi {

    ResponseEntity<JoinUserInChatroomResponse> joinUserInChatroom(
            @RequestBody JoinUserInChatroomRequest request);

    ResponseEntity<UserChatroomResponse> getUserChatroom(
            @PathVariable Long userId, @PathVariable Long chatroomId);

    ResponseEntity<List<ChatroomResponse>> getUserChatrooms(
            @PathVariable Long userId);

    ResponseEntity<List<UserResponse>> getUsersInChatroom(
            @PathVariable Long chatroomId);

    ResponseEntity<Boolean> doesUserExistInChatroom(
            @PathVariable Long userId, @PathVariable Long chatroomId);

    ResponseEntity<CommonApiResult> deleteUserInChatroom(
            @PathVariable Long userId, @PathVariable Long chatroomId);

    ResponseEntity<CommonApiResult> deleteAllUsersInChatroom(
            @PathVariable Long chatroomId);

    ResponseEntity<CommonApiResult> deleteAllChatroomsThatUserIn(
            @PathVariable Long userId);
}
