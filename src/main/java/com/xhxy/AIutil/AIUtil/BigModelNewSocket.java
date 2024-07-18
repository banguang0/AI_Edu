package com.xhxy.AIutil.AIUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import okhttp3.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class BigModelNewSocket extends WebSocketListener {
    public static final String hostUrl = "https://spark-api.xf-yun.com/v4.0/chat";
    public static final String appid = "3d8f5835";//3d8f5835
    public static final String apiSecret = "NDgyNmM1ZTIyZGJjZWEyYzg1ZDE5ZDdm";//NDgyNmM1ZTIyZGJjZWEyYzg1ZDE5ZDdm
    public static final String apiKey = "479dbcbdb2e858edc08e8547e97839d1";//479dbcbdb2e858edc08e8547e97839d1

    public static List<RoleContent> historyList = new ArrayList<>(); // 对话历史存储集合

    public static String totalAnswer = ""; // 大模型的答案汇总

    public static String NewQuestion = "";

    public static final Gson gson = new Gson();

    private String userId;
    public Boolean wsCloseFlag;

    private static Boolean totalFlag = true; // 控制提示用户是否输入

    private WebSocket webSocket;
    private OkHttpClient client;
    private Consumer<String> partialResponseHandler;

    public BigModelNewSocket(String userId, Boolean wsCloseFlag) {
        this.userId = userId;
        this.wsCloseFlag = wsCloseFlag;
    }

    public void connect(Consumer<String> partialResponseHandler) throws Exception {
        this.partialResponseHandler = partialResponseHandler;
        String authUrl = getAuthUrl(hostUrl, apiKey, apiSecret);
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS) // 设置连接超时时间为10秒
                .readTimeout(0, TimeUnit.SECONDS)    // 设置读取超时时间，对于WebSocket通常设置为0，即无限制
                .writeTimeout(10, TimeUnit.SECONDS)  // 设置写入超时时间为10秒
                .build();
        String url = authUrl.toString().replace("http://", "ws://").replace("https://", "wss://");
        Request request = new Request.Builder().url(url).build();
        webSocket = client.newWebSocket(request, this);
    }

    public void processChat(String question) {
        NewQuestion = question;
        totalAnswer = "";
        sendMessage(webSocket, question);
    }

    private void sendMessage(WebSocket webSocket, String question) {
        JSONObject requestJson = new JSONObject();

        JSONObject header = new JSONObject();  // header参数
        header.put("app_id", appid);
        header.put("uid", UUID.randomUUID().toString().substring(0, 10));

        JSONObject parameter = new JSONObject(); // parameter参数
        JSONObject chat = new JSONObject();
        chat.put("domain", "4.0Ultra");
        chat.put("temperature", 0.5);
        chat.put("max_tokens", 8192);
        parameter.put("chat", chat);

        JSONObject payload = new JSONObject(); // payload参数
        JSONObject message = new JSONObject();
        JSONArray text = new JSONArray();

        if (historyList.size() > 0) {
            for (RoleContent tempRoleContent : historyList) {
                System.out.println("========"+JSON.toJSON(tempRoleContent)+"============");
                text.add(JSON.toJSON(tempRoleContent));
            }
        }

        RoleContent roleContent = new RoleContent();
        roleContent.role = "user";
        roleContent.content = NewQuestion;
        text.add(JSON.toJSON(roleContent));
        historyList.add(roleContent);

        message.put("text", text);
        payload.put("message", message);

        requestJson.put("header", header);
        requestJson.put("parameter", parameter);
        requestJson.put("payload", payload);


        webSocket.send(requestJson.toString());
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        System.out.println(text);
        JsonParse myJsonParse = gson.fromJson(text, JsonParse.class);
        if (myJsonParse.header.code != 0) {
            System.out.println("发生错误，错误码为：" + myJsonParse.header.code);
            System.out.println("本次请求的sid为：" + myJsonParse.header.sid);
            webSocket.close(1000, "");
        }
        List<Text> textList = myJsonParse.payload.choices.text;
        for (Text temp : textList) {
            System.out.print(temp.content);
            totalAnswer = totalAnswer + temp.content;
            //// 实时传送部分回答结果给前端，包含 end 字段
//            String response = buildResponse(temp.content, myJsonParse.header.status == 2);
            String response = buildResponse(totalAnswer, myJsonParse.header.status == 2);
            partialResponseHandler.accept(response);
//            partialResponseHandler.accept(totalAnswer);
        }
        if (myJsonParse.header.status == 2) {
            wsCloseFlag = true;
            totalFlag = true;
        }
    }
    private String buildResponse(String content, boolean isEnd) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("answer", content);
        responseMap.put("end", isEnd ? "1" : "0");
        return gson.toJson(responseMap);
    }
    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        super.onFailure(webSocket, t, response);
        t.printStackTrace();
    }

    public static String getAuthUrl(String hostUrl, String apiKey, String apiSecret) throws Exception {
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

        String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"",
                apiKey, "hmac-sha256", "host date request-line", sha);

        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse("https://" + url.getHost() + url.getPath())).newBuilder()
                .addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization.getBytes(StandardCharsets.UTF_8)))
                .addQueryParameter("date", date)
                .addQueryParameter("host", url.getHost())
                .build();

        return httpUrl.toString();
    }

    class JsonParse {
        Header header;
        Payload payload;
    }

    class Header {
        int code;
        int status;
        String sid;
    }

    class Payload {
        Choices choices;
    }

    class Choices {
        List<Text> text;
    }

    class Text {
        String role;
        String content;
    }

    class RoleContent {
        String role;
        String content;

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
