package org.opensource.message;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.opensource.message.dto.CountMessagesByChatroomDto;
import org.opensource.message.dto.MessageDocumentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1/api/messages")
@RequiredArgsConstructor
@Tag(name = "Message API", description = "채팅 메시지 관리 API")
public class MessageController {

    private final MessageService messageService;

    @Operation(
            summary = "채팅방 메시지 전체 조회",
            description = "특정 채팅방의 모든 메시지를 시간순으로 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "메시지 조회 성공"),
            @ApiResponse(responseCode = "404", description = "채팅방을 찾을 수 없음")
    })
    @GetMapping("/chatroom/{chatroomId}")
    public ResponseEntity<List<MessageDocumentDto>> getMessagesByChatroom(
            @Parameter(description = "채팅방 ID", required = true)
            @PathVariable Long chatroomId) {

        return ResponseEntity.ok().body(
                messageService.getMessagesByChatroomInOrder(chatroomId));
    }

    @Operation(
            summary = "채팅방 최신 메시지 조회",
            description = "특정 채팅방의 메시지를 최신순으로 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "최신 메시지 조회 성공"),
            @ApiResponse(responseCode = "404", description = "채팅방을 찾을 수 없음")
    })
    @GetMapping("/chatroom/{chatroomId}/recent")
    public ResponseEntity<List<MessageDocumentDto>> getRecentMessagesByChatroom(
            @Parameter(description = "채팅방 ID", required = true)
            @PathVariable Long chatroomId) {

        return ResponseEntity.ok().body(
                messageService.getRecentMessagesByChatroom(chatroomId));
    }

    @Operation(
            summary = "특정 시간 이후 메시지 조회",
            description = "특정 채팅방에서 지정된 시간 이후에 전송된 메시지들을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "메시지 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 시간 형식"),
            @ApiResponse(responseCode = "404", description = "채팅방을 찾을 수 없음")
    })
    @GetMapping("/chatroom/{chatroomId}/after")
    public ResponseEntity<List<MessageDocumentDto>> getMessagesAfterTimestamp(
            @Parameter(description = "채팅방 ID", required = true)
            @PathVariable Long chatroomId,
            @Parameter(description = "기준 시간 (YYYY-MM-DDTHH:mm:ss)", required = true, example = "2024-01-01T10:00:00")
            @RequestParam LocalDateTime timestamp) {

        return ResponseEntity.ok().body(
                messageService.getMessagesAfterTimestamp(chatroomId, timestamp));
    }

    @Operation(
            summary = "특정 시간 이전 메시지 조회",
            description = "특정 채팅방에서 지정된 시간 이전에 전송된 메시지들을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "메시지 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 시간 형식"),
            @ApiResponse(responseCode = "404", description = "채팅방을 찾을 수 없음")
    })
    @GetMapping("/chatroom/{chatroomId}/before")
    public ResponseEntity<List<MessageDocumentDto>> getMessagesBeforeTimestamp(
            @Parameter(description = "채팅방 ID", required = true)
            @PathVariable Long chatroomId,
            @Parameter(description = "기준 시간 (YYYY-MM-DDTHH:mm:ss)", required = true, example = "2024-01-01T10:00:00")
            @RequestParam LocalDateTime timestamp) {

        return ResponseEntity.ok().body(
                messageService.getMessagesBeforeTimestamp(chatroomId, timestamp));
    }

    @Operation(
            summary = "채팅방 메시지 개수 조회",
            description = "특정 채팅방에 있는 메시지의 총 개수를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "메시지 개수 조회 성공"),
            @ApiResponse(responseCode = "404", description = "채팅방을 찾을 수 없음")
    })
    @GetMapping("/chatroom/{chatroomId}/count")
    public ResponseEntity<CountMessagesByChatroomDto> countMessagesByChatroom(
            @Parameter(description = "채팅방 ID", required = true)
            @PathVariable Long chatroomId) {

        return ResponseEntity.ok().body(
                messageService.countMessagesByChatroom(chatroomId));
    }

    @Operation(
            summary = "사용자별 메시지 조회",
            description = "특정 사용자가 보낸 모든 메시지를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "사용자 메시지 조회 성공"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    @GetMapping("/sender/{senderId}")
    public ResponseEntity<List<MessageDocumentDto>> getMessagesBySender(
            @Parameter(description = "발송자 ID", required = true)
            @PathVariable Long senderId) {

        return ResponseEntity.ok().body(
                messageService.getMessagesBySender(senderId));
    }

    @Operation(
            summary = "채팅방 내 사용자 메시지 조회",
            description = "특정 사용자가 특정 채팅방에서 보낸 모든 메시지를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "사용자 채팅방 메시지 조회 성공"),
            @ApiResponse(responseCode = "404", description = "사용자 또는 채팅방을 찾을 수 없음")
    })
    @GetMapping("/chatroom/{chatroomId}/sender/{senderId}")
    public ResponseEntity<List<MessageDocumentDto>> getMessagesBySenderInChatroom(
            @Parameter(description = "발송자 ID", required = true)
            @PathVariable Long senderId,
            @Parameter(description = "채팅방 ID", required = true)
            @PathVariable Long chatroomId
    ) {
        return ResponseEntity.ok().body(
                messageService.getMessagesBySenderIdAndChatroomId(senderId, chatroomId));
    }

    @Operation(
            summary = "채팅방 메시지 검색",
            description = "특정 채팅방에서 키워드가 포함된 메시지를 검색합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "메시지 검색 성공"),
            @ApiResponse(responseCode = "400", description = "검색어가 비어있음"),
            @ApiResponse(responseCode = "404", description = "채팅방을 찾을 수 없음")
    })
    @GetMapping("/chatroom/{chatroomId}/search")
    public ResponseEntity<List<MessageDocumentDto>> searchMessages(
            @Parameter(description = "채팅방 ID", required = true)
            @PathVariable Long chatroomId,
            @Parameter(description = "검색 키워드", required = true, example = "안녕하세요")
            @RequestParam String keyword) {

        return ResponseEntity.ok().body(
                messageService.searchMessagesByChatroomAndKeyword(chatroomId, keyword));
    }

    @Operation(
            summary = "채팅방 최신 메시지 단건 조회",
            description = "특정 채팅방의 가장 최근에 전송된 메시지 하나를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "최신 메시지 조회 성공"),
            @ApiResponse(responseCode = "404", description = "채팅방 또는 메시지를 찾을 수 없음")
    })
    @GetMapping("/chatroom/{chatroomId}/latest")
    public ResponseEntity<MessageDocumentDto> getLatestMessageByChatroom(
            @Parameter(description = "채팅방 ID", required = true)
            @PathVariable Long chatroomId) {

        return ResponseEntity.ok().body(
                messageService.getLatestMessageByChatroom(chatroomId));
    }

    @Operation(
            summary = "채팅방 내 사용자 메시지 삭제",
            description = "특정 사용자가 특정 채팅방에 보낸 모든 메시지를 삭제합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "메시지 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "사용자 또는 채팅방을 찾을 수 없음"),
            @ApiResponse(responseCode = "403", description = "삭제 권한 없음")
    })
    @DeleteMapping("/chatroom/{chatroomId}/sender/{senderId}")
    public ResponseEntity<?> deleteMessagesBySenderInChatroom(
            @Parameter(description = "채팅방 ID", required = true)
            @PathVariable Long chatroomId,
            @Parameter(description = "발송자 ID", required = true)
            @PathVariable Long senderId) {

        messageService.deleteMessagesBySenderInChatroom(chatroomId, senderId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "채팅방 전체 메시지 삭제",
            description = "특정 채팅방의 모든 메시지를 삭제합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "전체 메시지 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "채팅방을 찾을 수 없음"),
            @ApiResponse(responseCode = "403", description = "삭제 권한 없음")
    })
    @DeleteMapping("/chatroom/{chatroomId}")
    public ResponseEntity<?> deleteAllMessagesByChatroom(
            @Parameter(description = "채팅방 ID", required = true)
            @PathVariable Long chatroomId) {

        messageService.deleteAllMessagesByChatroom(chatroomId);
        return ResponseEntity.noContent().build();
    }
}