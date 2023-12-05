package com.example.loverecite.okhttp;

public class TransApi {
    private static final String TRANS_API_HOST = "https://fanyi-api.baidu.com/api/trans/vip/translate";

    private String appid;
    private String securityKey;

    public TransApi(String appid, String securityKey) {
        this.appid = appid;
        this.securityKey = securityKey;
    }


    public String buildString(String query, String from, String to) {

        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());

        // 签名
        String sign =MD5.md5(appid + query + salt + securityKey); // 加密前的原文
        //拼接请求
        String url =  TRANS_API_HOST+"?appid=" + appid + "&q=" + query + "&from=" + from + "&to=" +
                to + "&salt=" + salt + "&sign=" + sign;

        return url;
    }
}
