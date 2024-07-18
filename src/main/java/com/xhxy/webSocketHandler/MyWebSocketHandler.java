package com.xhxy.webSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xhxy.AIutil.AIUtil.BigModelNewSocket;
import com.xhxy.model.ChatRequest;
import com.xhxy.model.ChatResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

    private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 接收到消息
        System.out.println("Received message: " + message.getPayload());

        String payload = message.getPayload();
        // 假设前端发送的关闭指令为 {"command": "close"}
        if (payload.contains("\"command\": \"close\"")) {
            // 关闭 WebSocket 连接
            // 检测到关闭命令，关闭连接
            System.out.println("Received close command, closing connection.");
            session.close(CloseStatus.NORMAL);
//            session.close();
            return;
        }

        ChatRequest chatRequest = objectMapper.readValue(message.getPayload(), ChatRequest.class);
        String userId = chatRequest.getUserId();
        String question = chatRequest.getQuestion();

        // 调用大模型并实时返回部分回答
        BigModelNewSocket bigModelNewSocket = new BigModelNewSocket(userId, false);

        bigModelNewSocket.connect(partialAnswer -> {
            try {
                // 构建实时响应消息
//                ChatResponse chatResponse = new ChatResponse(partialAnswer);
                ChatResponse chatResponse = objectMapper.readValue(partialAnswer, ChatResponse.class);
                String jsonResponse = objectMapper.writeValueAsString(chatResponse);
                // 构建实时响应消息
//                Map<String, Object> responseMap = new HashMap<>();
//                responseMap.put("answer", partialAnswer);
//                responseMap.put("end", bigModelNew.wsCloseFlag ? "1" : "0");
//                String jsonResponse = objectMapper.writeValueAsString(responseMap);
                // 向前端发送实时响应消息
                session.sendMessage(new TextMessage(jsonResponse));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        bigModelNewSocket.processChat(question);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String sessionId = session.getId();
        sessions.put(sessionId, session);
        System.out.println("WebSocket connection established: " + sessionId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String sessionId = session.getId();
        sessions.remove(sessionId);
        System.out.println("WebSocket connection closed: " + sessionId);
    }
}
