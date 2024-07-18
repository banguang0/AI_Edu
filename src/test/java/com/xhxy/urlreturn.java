package com.xhxy;

public class urlreturn {
    private static String text;

    public static String returnUrl(String s){
        text = s+",OK";
        return text;
    }
}
