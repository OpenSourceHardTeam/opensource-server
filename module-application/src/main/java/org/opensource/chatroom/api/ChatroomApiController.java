package org.opensource.chatroom.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Chatroom API", description = "채팅방 관리 API")
public class ChatroomApiController implements ChatroomApi {

    private final ChatroomFacade chatroomFacade;

    @Operation(
            summary = "채팅방 생성",
            description = "새로운 채팅방을 생성합니다. 도서와 연결된 채팅방이 만들어집니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "채팅방 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "404", description = "연결할 도서를 찾을 수 없음"),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 채팅방")
    })
    @Override @PostMapping
    public ResponseEntity<CreateChatroomResponse> createChatroom(
            @Parameter(description = "채팅방 생성 요청 정보", required = true)
            @Valid @RequestBody CreateChatroomRequest request
    ) {
        Long chatroomId = chatroomFacade.createChatroom(request);
        return ResponseEntity.ok(new CreateChatroomResponse(chatroomId));
    }

    @Operation(
            summary = "채팅방 상세 조회",
            description = "채팅방 ID로 특정 채팅방의 상세 정보를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "채팅방 조회 성공"),
            @ApiResponse(responseCode = "404", description = "채팅방을 찾을 수 없음")
    })
    @Override @GetMapping("/{id}")
    public ResponseEntity<ChatroomResponse> getChatroom(
            @Parameter(description = "채팅방 ID", required = true, example = "1")
            @PathVariable Long id
    ) {
        Chatroom chatroom = chatroomFacade.findChatroomById(id);
        return ResponseEntity.ok(new ChatroomResponse(chatroom.getTopic(), chatroom.getBook().getBookId()));
    }

    @Operation(
            summary = "채팅방 삭제",
            description = "채팅방을 삭제합니다. 연관된 모든 메시지와 참여자 정보도 함께 삭제됩니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "채팅방 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "채팅방을 찾을 수 없음"),
            @ApiResponse(responseCode = "403", description = "삭제 권한 없음"),
            @ApiResponse(responseCode = "409", description = "활성 참여자가 있어 삭제할 수 없음")
    })
    @Override @DeleteMapping("/{id}")
    public ResponseEntity<CommonApiResult> deleteChatroom(
            @Parameter(description = "삭제할 채팅방 ID", required = true, example = "1")
            @PathVariable Long id
    ) {
        chatroomFacade.deleteChatroom(id);
        return ResponseEntity.ok(CommonApiResult.createOk());
    }
}