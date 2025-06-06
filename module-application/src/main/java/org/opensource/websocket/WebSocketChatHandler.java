package org.opensource.websocket;

import org.opensource.message.MessageService;
import org.opensource.message.entity.MessageEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketChatHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketChatHandler.class);

    private final MessageService messageService;

    // sessionMap Key - UserInfo chatRoomId
    private final Map<Long, Set<UserInfo>> sessionMap = new ConcurrentHashMap<>();

    @Autowired
    public WebSocketChatHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        UserInfo userInfo = extractUserInfo(session);

        if (userInfo != null) {
            addUserToChatRoom(userInfo);
            notifyUserJoined(userInfo);
        }
    }

    private void addUserToChatRoom(UserInfo userInfo) {
        sessionMap.computeIfAbsent(userInfo.getChatRoomId(), k -> new HashSet<>()).add(userInfo);
    }

    private void notifyUserJoined(UserInfo userInfo) {
        String joinMessage = userInfo.getName() + "님이 대화방에 들어오셨습니다.";
        sendSystemMessageToChatRoom(userInfo.getChatRoomId(), joinMessage);
    }

    @Override
    protected void handleTextMessage(
            @NonNull WebSocketSession session,
            @NonNull TextMessage textMessage) throws Exception {
        super.handleTextMessage(session, textMessage);
        UserInfo userInfo = extractUserInfo(session);

        if (userInfo != null) {
            String textMessagePayload = textMessage.getPayload();
            processUserMessage(userInfo, textMessagePayload);
        }
    }

    private void processUserMessage(UserInfo userInfo, String messageContent) {
        try {
            broadcastMessageToChatRoom(userInfo, messageContent);
            saveMessageAsync(userInfo, messageContent);
        } catch (IOException e) {
            logger.error("Error processing message: {}", e.getMessage(), e);
        }
    }

    private void broadcastMessageToChatRoom(UserInfo userInfo, String messageContent) throws IOException {
        String formattedMessage = formatMessage(userInfo.getName(), messageContent);
        sendMessageToChatRoomExceptSelf(userInfo.getChatRoomId(), formattedMessage, userInfo);
    }

    private String formatMessage(String userName, String content) {
        return userName + " : " + content;
    }

    private void saveMessageAsync(UserInfo userInfo, String messageContent) {
        MessageEntity messageEntity = createMessageDocument(userInfo, messageContent);

        CompletableFuture.runAsync(() -> {
            try {
                messageService.saveMessage(messageEntity);
            } catch (Exception e) {
                logger.error("Failed to save message: {}", e.getMessage(), e);
            }
        });
    }

    private MessageEntity createMessageDocument(UserInfo userInfo, String content) {
        return MessageEntity.builder()
                .senderId(userInfo.getUserId())
                .chatroomId(userInfo.getChatRoomId())
                .content(content)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Override
    public void handleTransportError(
            @NonNull WebSocketSession session,
            @NonNull Throwable exception) throws Exception {
        logger.error("WebSocket transport error: {}", exception.getMessage(), exception);
        super.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(
            @NonNull WebSocketSession session,
            @NonNull CloseStatus status) throws Exception {
        UserInfo userInfo = extractUserInfo(session);

        if (userInfo != null) {
            removeUserFromChatRoom(userInfo);
            notifyUserLeft(userInfo);
        }

        super.afterConnectionClosed(session, status);
    }

    private void removeUserFromChatRoom(UserInfo userInfo) {
        Set<UserInfo> usersInChatRoom = sessionMap.get(userInfo.getChatRoomId());
        if (usersInChatRoom != null) {
            usersInChatRoom.removeIf(user -> user.getUserId().equals(userInfo.getUserId()));

            // 채팅방이 비었다면 맵에서 제거
            if (usersInChatRoom.isEmpty()) {
                sessionMap.remove(userInfo.getChatRoomId());
            }
        }
    }

    private void notifyUserLeft(UserInfo userInfo) {
        String leaveMessage = userInfo.getName() + "님이 대화방을 나가셨습니다.";
        sendSystemMessageToChatRoom(userInfo.getChatRoomId(), leaveMessage);
    }

    private void sendSystemMessageToChatRoom(Long chatRoomId, String message) {
        try {
            sendMessageToChatRoom(chatRoomId, message);
        } catch (IOException e) {
            logger.error("Failed to send system message: {}", e.getMessage(), e);
        }
    }

    private void sendMessageToChatRoom(Long chatRoomId, String message) throws IOException {
        Set<UserInfo> users = sessionMap.get(chatRoomId);

        if (users == null || users.isEmpty()) {
            return;
        }

        IOException lastException = null;

        for (UserInfo user : users) {
            try {
                user.getSession().sendMessage(new TextMessage(message));
            } catch (IOException e) {
                logger.warn("Failed to send message to user {}: {}", user.getUserId(), e.getMessage());
                lastException = e;
            }
        }

        // 모든 사용자에게 메시지 전송을 시도한 후, 마지막 예외가 있으면 throw
        if (lastException != null) {
            throw lastException;
        }
    }

    private void sendMessageToChatRoomExceptSelf(Long chatRoomId, String message, UserInfo sender) throws IOException {
        Set<UserInfo> users = sessionMap.get(chatRoomId);

        if (users == null || users.isEmpty()) {
            return;
        }

        String senderId = sender.getSession().getId();
        IOException lastException = null;

        for (UserInfo user : users) {
            try {
                if (!senderId.equals(user.getSession().getId())) {
                    user.getSession().sendMessage(new TextMessage(message));
                }
            } catch (IOException e) {
                logger.warn("Failed to send message to user {}: {}", user.getUserId(), e.getMessage());
                lastException = e;
            }
        }

        // 모든 사용자에게 메시지 전송을 시도한 후, 마지막 예외가 있으면 throw
        if (lastException != null) {
            throw lastException;
        }
    }

    private UserInfo extractUserInfo(WebSocketSession session) {
        try {
            // 헤더와 쿼리 파라미터에서 필수 값들 추출
            Map<String, String> params = extractRequiredParams(session);
            if (params == null) {
                return null; // extractRequiredParams에서 이미 오류 메시지를 보냈음
            }

            // 숫자 값 파싱
            Long userId = parseNumericId(session, params.get("userId"), "userId");
            Long chatroomId = parseNumericId(session, params.get("chatroomId"), "chatroomId");

            if (userId == null || chatroomId == null) {
                return null; // parseNumericId에서 이미 오류 메시지를 보냈음
            }

            return new UserInfo(params.get("name"), userId, chatroomId, session);

        } catch (Exception e) {
            logger.error("WebSocket parameter processing failed", e);
            try {
                sendErrorAndClose(session, "Unexpected server error.");
            } catch (IOException ioe) {
                logger.error("Failed to send error message", ioe);
            }
            return null;
        }
    }


    private Map<String, String> extractRequiredParams(WebSocketSession session) throws IOException {
        // 1. 먼저 헤더에서 시도 (기존 방식 - Postman 등)
        String name = session.getHandshakeHeaders().getFirst("name");
        String userIdString = session.getHandshakeHeaders().getFirst("userId");
        String chatroomIdString = session.getHandshakeHeaders().getFirst("chatRoomId");

        // 2. 헤더에 값이 없으면 세션 속성에서 시도 (쿼리 파라미터 - 브라우저용)
        if (name == null || userIdString == null || chatroomIdString == null) {
            name = name != null ? name : (String) session.getAttributes().get("name");
            userIdString = userIdString != null ? userIdString : (String) session.getAttributes().get("userId");
            chatroomIdString = chatroomIdString != null ? chatroomIdString : (String) session.getAttributes().get("chatRoomId");
        }

        // 3. 여전히 값이 없으면 에러
        if (name == null || userIdString == null || chatroomIdString == null) {
            sendErrorAndClose(session, "Missing required headers or query parameters (name, userId, chatRoomId).");
            return null;
        }

        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("userId", userIdString);
        params.put("chatroomId", chatroomIdString);
        return params;
    }

    private Long parseNumericId(WebSocketSession session, String idString, String fieldName) throws IOException {
        try {
            return Long.parseLong(idString);
        } catch (NumberFormatException e) {
            sendErrorAndClose(session, "Invalid " + fieldName + " format: " + idString);
            return null;
        }
    }

    private void sendErrorAndClose(WebSocketSession session, String errorMessage) throws IOException {
        if (session.isOpen()) {
            session.sendMessage(new TextMessage("Error: " + errorMessage));
            session.close();
        }
    }
}