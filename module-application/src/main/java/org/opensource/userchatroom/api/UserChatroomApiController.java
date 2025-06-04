package org.opensource.userchatroom.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "User-Chatroom API", description = "사용자-채팅방 관계 관리 API")
public class UserChatroomApiController implements UserChatroomApi {

    private final UserChatroomFacade userChatroomFacade;

    @Operation(
            summary = "채팅방 참여",
            description = "사용자를 특정 채팅방에 참여시킵니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "채팅방 참여 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "404", description = "사용자 또는 채팅방을 찾을 수 없음"),
            @ApiResponse(responseCode = "409", description = "이미 참여 중인 채팅방")
    })
    @Override @PostMapping
    public ResponseEntity<JoinUserInChatroomResponse> joinUserInChatroom(
            @Parameter(description = "채팅방 참여 요청 정보", required = true)
            @Valid @RequestBody JoinUserInChatroomRequest request) {
        Long userChatroomId = userChatroomFacade.joinUserInChatRoom(
                request.getUserId(), request.getChatroomId(), true);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new JoinUserInChatroomResponse(userChatroomId));
    }

    @Operation(
            summary = "사용자-채팅방 관계 조회",
            description = "특정 사용자와 채팅방 간의 관계 정보를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "관계 정보 조회 성공"),
            @ApiResponse(responseCode = "404", description = "사용자, 채팅방 또는 관계를 찾을 수 없음")
    })
    @Override @GetMapping("/user/{userId}/chatroom/{chatroomId}")
    public ResponseEntity<UserChatroom> getUserChatroom(
            @Parameter(description = "사용자 ID", required = true)
            @PathVariable Long userId,
            @Parameter(description = "채팅방 ID", required = true)
            @PathVariable Long chatroomId) {
        UserChatroom userChatroom = userChatroomFacade.findUserInChatRoom(userId, chatroomId);
        return ResponseEntity.ok(userChatroom);
    }

    @Operation(
            summary = "사용자 참여 채팅방 목록 조회",
            description = "특정 사용자가 참여하고 있는 모든 채팅방 목록을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "채팅방 목록 조회 성공"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    @Override @GetMapping("/user/{userId}/chatrooms")
    public ResponseEntity<List<Chatroom>> getUserChatRooms(
            @Parameter(description = "사용자 ID", required = true)
            @PathVariable Long userId) {
        List<Chatroom> chatRooms = userChatroomFacade.findChatRoomsUserParticipatesIn(userId);
        return ResponseEntity.ok(chatRooms);
    }

    @Operation(
            summary = "채팅방 참여자 목록 조회",
            description = "특정 채팅방에 참여하고 있는 모든 사용자 목록을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "참여자 목록 조회 성공"),
            @ApiResponse(responseCode = "404", description = "채팅방을 찾을 수 없음")
    })
    @Override @GetMapping("/chatroom/{chatroomId}/users")
    public ResponseEntity<List<User>> getUsersInChatroom(
            @Parameter(description = "채팅방 ID", required = true)
            @PathVariable Long chatroomId) {
        List<User> users = userChatroomFacade.findParticipantsInChatRoom(chatroomId);
        return ResponseEntity.ok(users);
    }

    @Operation(
            summary = "채팅방 참여 여부 확인",
            description = "특정 사용자가 특정 채팅방에 참여하고 있는지 확인합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "참여 여부 확인 성공"),
            @ApiResponse(responseCode = "404", description = "사용자 또는 채팅방을 찾을 수 없음")
    })
    @Override @GetMapping("/chatrooms/{chatroomId}/users/{userId}/exists")
    public ResponseEntity<Boolean> doesUserExistInChatroom(
            @Parameter(description = "사용자 ID", required = true)
            @PathVariable Long userId,
            @Parameter(description = "채팅방 ID", required = true)
            @PathVariable Long chatroomId) {
        Boolean doesUserExist = userChatroomFacade.doesUserExistInChatRoom(userId, chatroomId);
        return ResponseEntity.ok(doesUserExist);
    }

    @Operation(
            summary = "채팅방에서 사용자 제거",
            description = "특정 사용자를 특정 채팅방에서 제거합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "사용자 제거 성공"),
            @ApiResponse(responseCode = "404", description = "사용자, 채팅방 또는 관계를 찾을 수 없음"),
            @ApiResponse(responseCode = "403", description = "제거 권한 없음")
    })
    @Override @DeleteMapping("/user/{userId}/chatroom/{chatroomId}")
    public ResponseEntity<?> deleteUserInChatroom(
            @Parameter(description = "사용자 ID", required = true)
            @PathVariable Long userId,
            @Parameter(description = "채팅방 ID", required = true)
            @PathVariable Long chatroomId) {
        userChatroomFacade.deleteUserAtChatRoomByChatRoomId(userId, chatroomId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "채팅방 전체 참여자 제거",
            description = "특정 채팅방에 참여하고 있는 모든 사용자를 제거합니다. (채팅방 삭제 시 사용)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "전체 참여자 제거 성공"),
            @ApiResponse(responseCode = "404", description = "채팅방을 찾을 수 없음"),
            @ApiResponse(responseCode = "403", description = "제거 권한 없음")
    })
    @Override @DeleteMapping("/chatroom/{chatroomId}/users")
    public ResponseEntity<?> deleteAllUsersInChatroom(
            @Parameter(description = "채팅방 ID", required = true)
            @PathVariable Long chatroomId) {
        userChatroomFacade.deleteAllUsersWhenChatRoomDeleted(chatroomId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "사용자 전체 채팅방 탈퇴",
            description = "특정 사용자가 참여하고 있는 모든 채팅방에서 탈퇴시킵니다. (사용자 삭제 시 사용)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "전체 채팅방 탈퇴 성공"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음"),
            @ApiResponse(responseCode = "403", description = "탈퇴 권한 없음")
    })
    @Override @DeleteMapping("/user/{userId}/chatrooms")
    public ResponseEntity<?> leaveAllChatRoomsThatUserIn(
            @Parameter(description = "사용자 ID", required = true)
            @PathVariable Long userId) {
        userChatroomFacade.deleteAllChatRoomsWhenUserDeleted(userId);
        return ResponseEntity.noContent().build();
    }
}