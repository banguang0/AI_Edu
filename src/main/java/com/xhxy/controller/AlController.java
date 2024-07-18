package com.xhxy.controller;

import com.alibaba.fastjson.JSONObject;
import com.xhxy.AIutil.AIUtil.BigModelNew;
import com.xhxy.AIutil.AIUtil.BigModelNewSocket;
import com.xhxy.webSocketHandler.WebSocketConfig;
import com.xhxy.model.ChatRequest;
import com.xhxy.model.ChatResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AlController {

    private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Autowired
    private WebSocketConfig webSocketHandler;

    @PostMapping("/chat")
    public ResponseEntity<Object> chat(@RequestBody ChatRequest chatRequest) {
        try {
            WebSocketSession userSession = sessions.get(chatRequest.getUserId());
            if (userSession == null || !userSession.isOpen()) {
                return new ResponseEntity<>("WebSocket connection not established", HttpStatus.BAD_REQUEST);
            }

            var json = getJson(chatRequest, userSession);
            JSONObject jsonObject = JSONObject.parseObject(json);

            String answer = jsonObject.getString("answer");
            String end = jsonObject.getString("end");
            ChatResponse chatResponse = new ChatResponse(answer,end);
            return new ResponseEntity<>(chatResponse, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to process the request", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static @NotNull String getJson(ChatRequest chatRequest, WebSocketSession userSession) throws Exception {
        StringBuilder partialResponse = new StringBuilder();
        BigModelNewSocket bigModelNewSocket = new BigModelNewSocket(chatRequest.getUserId(), false);

        bigModelNewSocket.connect(partialAnswer -> {
            partialResponse.append(partialAnswer);
            try {
                userSession.sendMessage(new TextMessage(partialResponse.toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        bigModelNewSocket.processChat(chatRequest.getQuestion());

        String json = partialResponse.toString();
        return json;
    }

    @GetMapping("/websocket/{userId}")
    public ResponseEntity<String> connectWebSocket(@PathVariable String userId, WebSocketSession session) {
        sessions.put(userId, session);
        return ResponseEntity.ok("WebSocket connection established for user: " + userId);
    }


    @PostMapping("/ai")
    public ResponseEntity<?> chat1(@RequestBody String question) {
        try {
            String answer = BigModelNew.getAnswer(question);
            return ResponseEntity.ok(answer);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
