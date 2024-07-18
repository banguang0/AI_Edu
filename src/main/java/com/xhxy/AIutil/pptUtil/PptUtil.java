package com.xhxy.AIutil.pptUtil;

import com.alibaba.fastjson.JSON;

import java.io.IOException;

public class PptUtil {
    private static String query = "请按照以下大纲生成ppt";

    private static  String outline;

    private static String url;

    public static String getOutlineByQuestion(String question) throws IOException, InterruptedException {
        query = question;

        // 输入个人appId
        String appId = "e4e82936";
        String secret = "MzI4NjMyZWZjM2Q0MzNmZmJlZjU5NjFl";
        long timestamp = System.currentTimeMillis() / 1000;
        String ts = String.valueOf(timestamp);
        // 获得鉴权信息
        ApiAuthAlgorithm auth = new ApiAuthAlgorithm();
        String signature = auth.getSignature(appId, secret, timestamp);
        System.out.println(signature);

        // 建立链接
        ApiClient client = new ApiClient("https://zwapi.xfyun.cn");

        // 发送生成PPT请求
        String resp = client.createPPT(appId, ts, signature, query);
        System.out.println(resp);
        CreateResponse response = JSON.parseObject(resp, CreateResponse.class);

        // 利用sid查询PPT生成进度
        int progress = 0;
        ProgressResponse progressResponse;
        while (progress < 100) {
            String progressResult = client.checkProgress(appId, ts, signature, response.getData().getSid());
            progressResponse = JSON.parseObject(progressResult, ProgressResponse.class);
            progress = progressResponse.getData().getProcess();
            System.out.println("AAA:" + progressResult);

            if (progress < 100) {
                Thread.sleep(5000); // 暂停2秒
            }
        }

        // 大纲生成
        String outlineQuery = query;
        String outlineResp = client.createOutline(appId, ts, signature, outlineQuery);
        System.out.println(outlineResp);
        CreateResponse outlineResponse = JSON.parseObject(outlineResp, CreateResponse.class);
        System.out.println("生成的大纲如下：");
        outline = outlineResponse.getData().getOutline();
        System.out.println(outline);

        return outline;
    }

    public static String getUrl(String theme,String d) throws IOException, InterruptedException {
        outline = d;

        // 输入个人appId
        String appId = "e4e82936";
        String secret = "MzI4NjMyZWZjM2Q0MzNmZmJlZjU5NjFl";
        long timestamp = System.currentTimeMillis() / 1000;
        String ts = String.valueOf(timestamp);
        // 获得鉴权信息
        ApiAuthAlgorithm auth = new ApiAuthAlgorithm();
        String signature = auth.getSignature(appId, secret, timestamp);
        System.out.println(signature);

        // 建立链接
        ApiClient.setTheme(theme);
        ApiClient client = new ApiClient("https://zwapi.xfyun.cn");

        // 基于大纲生成ppt
        String pptResp = client.createPptByOutline(appId, ts, signature, query, outline);
        System.out.println(pptResp);
        CreateResponse pptResponse = JSON.parseObject(pptResp, CreateResponse.class);

        // 利用sid查询PPT生成进度
        int progress = 0;
        ProgressResponse progressResponse;
        while (progress < 100) {
            String progressResult = client.checkProgress(appId, ts, signature, pptResponse.getData().getSid());
            progressResponse = JSON.parseObject(progressResult, ProgressResponse.class);
            progress = progressResponse.getData().getProcess();
            url = progressResponse.getData().getPptUrl();
            System.out.println("BBB:" + progressResult);

            if (progress < 100) {
                Thread.sleep(5000); // 暂停2秒
            }
        }
        return url;
    }
}