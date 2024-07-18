package com.xhxy.AIutil.videoUtil;

import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;
import com.xhxy.AIutil.videoUtil.sign.LfasrSignature;
import com.xhxy.AIutil.videoUtil.utils.HttpUtil;
import org.apache.commons.lang.StringEscapeUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.security.SignatureException;
import java.util.HashMap;

public class Ifasrdemo {
    private static final String HOST = "https://raasr.xfyun.cn";
    private static String AUDIO_FILE_PATH;
    private static final String appid = "3d8f5835";
    private static final String keySecret = "af3d813941c693a2d07b160f21345b1e";

    private static String text;
    private static final Gson gson = new Gson();

    static {
        try {
            AUDIO_FILE_PATH = Ifasrdemo.class.getResource("/").toURI().getPath() + "/audio/合成音频.wav";
            System.out.println("AUDIO_FILE_PATH----------:"+AUDIO_FILE_PATH);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


//    public static void main(String[] args) throws Exception {
//        String result = upload();
//        String jsonStr = StringEscapeUtils.unescapeJavaScript(result);
//        String orderId = String.valueOf(JSONUtil.getByPath(JSONUtil.parse(jsonStr), "content.orderId"));
//        getResult(orderId);
//    }

    private static String upload() throws SignatureException, FileNotFoundException {
        HashMap<String, Object> map = new HashMap<>(16);
        File audio = new File(AUDIO_FILE_PATH);
        String fileName = audio.getName();
        long fileSize = audio.length();
        map.put("appId", appid);
        map.put("fileSize", fileSize);
        map.put("fileName", fileName);
        map.put("duration", "200");
        LfasrSignature lfasrSignature = new LfasrSignature(appid, keySecret);
        map.put("signa", lfasrSignature.getSigna());
        map.put("ts", lfasrSignature.getTs());

        String paramString = HttpUtil.parseMapToPathParam(map);
        System.out.println("upload paramString:" + paramString);

        String url = HOST + "/v2/api/upload" + "?" + paramString;
        System.out.println("upload_url:" + url);
        String response = HttpUtil.iflyrecUpload(url, new FileInputStream(audio));

        System.out.println("upload response:" + response);
        return response;
    }

    private static String getResult(String orderId) throws SignatureException, InterruptedException, IOException {
        HashMap<String, Object> map = new HashMap<>(16);
        map.put("orderId", orderId);
        LfasrSignature lfasrSignature = new LfasrSignature(appid, keySecret);
        map.put("signa", lfasrSignature.getSigna());
        map.put("ts", lfasrSignature.getTs());
        map.put("appId", appid);
        map.put("resultType", "transfer,predict");
        String paramString = HttpUtil.parseMapToPathParam(map);
        String url = HOST + "/v2/api/getResult" + "?" + paramString;
        System.out.println("\nget_result_url:" + url);
        while (true) {
            String response = HttpUtil.iflyrecGet(url);
            JsonParse jsonParse = gson.fromJson(response, JsonParse.class);
            if (jsonParse.content.orderInfo.status == 4 || jsonParse.content.orderInfo.status == -1) {
                System.out.println("识别完成");
                String pureText = extractPureText(jsonParse.content.orderResult);

//                System.out.println("pureText--------:"+pureText);
                text = pureText;
//                write(pureText);
                return response;
            } else {
                System.out.println("进行中...，状态为:" + jsonParse.content.orderInfo.status);
                //建议使用回调的方式查询结果，查询接口有请求频率限制
                Thread.sleep(7000);
            }
        }
    }
    // 提取纯文本内容的方法
    private static String extractPureText(String orderResultJson) {
        StringBuilder pureText = new StringBuilder();
        try {
            cn.hutool.json.JSONObject jsonObject = JSONUtil.parseObj(orderResultJson);
            cn.hutool.json.JSONArray lattices = jsonObject.getJSONArray("lattice");
            for (int i = 0; i < lattices.size(); i++) {
                cn.hutool.json.JSONObject lattice = lattices.getJSONObject(i);
                String json1Best = lattice.getStr("json_1best");
                cn.hutool.json.JSONObject json1BestObject = JSONUtil.parseObj(json1Best);
                cn.hutool.json.JSONArray rtArray = json1BestObject.getJSONObject("st").getJSONArray("rt");
                for (int j = 0; j < rtArray.size(); j++) {
                    cn.hutool.json.JSONObject rtObject = rtArray.getJSONObject(j);
                    cn.hutool.json.JSONArray wsArray = rtObject.getJSONArray("ws");
                    for (int k = 0; k < wsArray.size(); k++) {
                        cn.hutool.json.JSONObject wsObject = wsArray.getJSONObject(k);
                        cn.hutool.json.JSONArray cwArray = wsObject.getJSONArray("cw");
                        for (int l = 0; l < cwArray.size(); l++) {
                            cn.hutool.json.JSONObject cwObject = cwArray.getJSONObject(l);
                            pureText.append(cwObject.getStr("w"));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pureText.toString();
    }

    public static String getText(String url) throws IOException, SignatureException, InterruptedException {
        System.out.println(url);
        Ifasrdemo.AUDIO_FILE_PATH=url;
        String result = upload();
        String jsonStr = StringEscapeUtils.unescapeJavaScript(result);
        String orderId = String.valueOf(JSONUtil.getByPath(JSONUtil.parse(jsonStr), "content.orderId"));
        getResult(orderId);
        return text;
    }

    class JsonParse {
        Content content;
    }

    class Content {
        public String orderResult;
        OrderInfo orderInfo;
    }

    class OrderInfo {
        Integer status;
    }
}