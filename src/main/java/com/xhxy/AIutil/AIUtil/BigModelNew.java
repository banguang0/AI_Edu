package com.xhxy.AIutil.AIUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class BigModelNew {
    private static final String hostUrl = "https://spark-api.xf-yun.com/v4.0/chat";
    private static final String appid = "3d8f5835";
    private static final String apiSecret = "NDgyNmM1ZTIyZGJjZWEyYzg1ZDE5ZDdm";
    private static final String apiKey = "479dbcbdb2e858edc08e8547e97839d1";

    public static String getAnswer(String question) throws Exception {
        CompletableFuture<String> future = new CompletableFuture<>();

        String authUrl = getAuthUrl(hostUrl, apiKey, apiSecret);
        OkHttpClient client = new OkHttpClient.Builder().build();
        String url = authUrl.replace("http://", "ws://").replace("https://", "wss://");
        Request request = new Request.Builder().url(url).build();

        WebSocket webSocket = client.newWebSocket(request, new WebSocketListener() {
            private StringBuilder totalAnswer = new StringBuilder();

            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                JSONObject requestJson = new JSONObject();

                JSONObject header = new JSONObject();
                header.put("app_id", appid);
                header.put("uid", UUID.randomUUID().toString().substring(0, 10));

                JSONObject parameter = new JSONObject();
                JSONObject chat = new JSONObject();
                chat.put("domain", "4.0Ultra");
                chat.put("temperature", 0.5);
                chat.put("max_tokens", 8192);
                parameter.put("chat", chat);

                JSONObject payload = new JSONObject();
                JSONObject message = new JSONObject();
                JSONArray text = new JSONArray();

                JSONObject roleContent = new JSONObject();
                roleContent.put("role", "user");
                roleContent.put("content", question);
                text.add(roleContent);

                message.put("text", text);
                payload.put("message", message);

                requestJson.put("header", header);
                requestJson.put("parameter", parameter);
                requestJson.put("payload", payload);

                webSocket.send(requestJson.toString());
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                JSONObject responseJson = JSON.parseObject(text);
                JSONObject header = responseJson.getJSONObject("header");
                if (header.getInteger("code") != 0) {
                    future.completeExceptionally(new RuntimeException("Error code: " + header.getInteger("code")));
                    webSocket.close(1000, "");
                    return;
                }

                JSONArray choices = responseJson.getJSONObject("payload").getJSONObject("choices").getJSONArray("text");
                for (int i = 0; i < choices.size(); i++) {
                    totalAnswer.append(choices.getJSONObject(i).getString("content"));
                }

                if (header.getInteger("status") == 2) {
                    future.complete(totalAnswer.toString());
                    webSocket.close(1000, "");
                }
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                future.completeExceptionally(t);
            }
        });

        return future.get();
    }

    private static String getAuthUrl(String hostUrl, String apiKey, String apiSecret) throws Exception {
        URL url = new URL(hostUrl);
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = format.format(new Date());
        String preStr = "host: " + url.getHost() + "\n" +
                "date: " + date + "\n" +
                "GET " + url.getPath() + " HTTP/1.1";
        Mac mac = Mac.getInstance("hmacsha256");
        SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "hmacsha256");
        mac.init(spec);
        byte[] hexDigits = mac.doFinal(preStr.getBytes(StandardCharsets.UTF_8));
        String sha = Base64.getEncoder().encodeToString(hexDigits);
        String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", sha);
        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse("https://" + url.getHost() + url.getPath())).newBuilder()
                .addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization.getBytes(StandardCharsets.UTF_8)))
                .addQueryParameter("date", date)
                .addQueryParameter("host", url.getHost())
                .build();
        return httpUrl.toString();
    }
}