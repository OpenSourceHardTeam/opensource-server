package org.opensource.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.net.URI;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketHandshakeInterceptor.class);

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        try {
            // 쿼리 파라미터 추출
            Map<String, String> queryParams = parseQueryParameters(request.getURI());

            // 필수 파라미터 체크
            String name = queryParams.get("name");
            String userId = queryParams.get("userId");
            String chatRoomId = queryParams.get("chatRoomId");

            if (name != null && userId != null && chatRoomId != null) {
                // 세션 속성에 저장 (나중에 WebSocketChatHandler에서 사용)
                attributes.put("name", name);
                attributes.put("userId", userId);
                attributes.put("chatRoomId", chatRoomId);

                logger.info("WebSocket handshake successful for user: {} in room: {}", name, chatRoomId);
                return true;
            } else {
                logger.warn("Missing required query parameters: name={}, userId={}, chatRoomId={}",
                        name, userId, chatRoomId);
                return false; // 연결 거부
            }

        } catch (Exception e) {
            logger.error("Error during WebSocket handshake", e);
            return false; // 연결 거부
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        // 후처리가 필요하면 여기서
    }

    private Map<String, String> parseQueryParameters(URI uri) {
        Map<String, String> params = new HashMap<>();

        try {
            String query = uri.getQuery();
            if (query != null && !query.isEmpty()) {
                String[] pairs = query.split("&");
                for (String pair : pairs) {
                    int idx = pair.indexOf("=");
                    if (idx > 0 && idx < pair.length() - 1) {
                        String key = URLDecoder.decode(pair.substring(0, idx), "UTF-8");
                        String value = URLDecoder.decode(pair.substring(idx + 1), "UTF-8");
                        params.put(key, value);
                    }
                }
            }
        } catch (Exception e) {
            logger.warn("Failed to parse query parameters", e);
        }

        return params;
    }
}