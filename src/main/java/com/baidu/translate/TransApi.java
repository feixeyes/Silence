package com.baidu.translate;

import com.feixeyes.utils.Helper;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransApi {
    private static final String TRANS_API_HOST = "http://api.fanyi.baidu.com/api/trans/vip/translate";

    private String appid;
    private String securityKey;

    public TransApi(String appid, String securityKey) {
        this.appid = appid;
        this.securityKey = securityKey;
    }

    public String getTransResult(String query, String from, String to) {
        Map<String, String> params = buildParams(query, from, to);
        return HttpGet.get(TRANS_API_HOST, params);
    }



    private Map<String, String> buildParams(String query, String from, String to) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);

        params.put("appid", appid);

        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);

        // 签名
        String src = appid + query + salt + securityKey; // 加密前的原文
        params.put("sign", MD5.md5(src));

        return params;
    }


    public static String trans2EN(String query){
        String APP_ID = Helper.getProperty("APP_ID");
        String SECURITY_KEY = Helper.getProperty("SECURITY_KEY");
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);
        return api.getTransResult(query, "auto", "en");
    }


    public static String trans2ENRes(String query) {
        String jsonStr = trans2EN(query);
        Gson gson = new Gson();
        BaiduTransRes obj = gson.fromJson(jsonStr,BaiduTransRes.class);
        return obj.getSingleResList();
    }
}

class BaiduTransRes {
    String from;
    String to;
    List<BaiduTransResNode> trans_result;

    public List<BaiduTransResNode> getResList(){
        return trans_result;
    }

    public String getSingleResList(){
        return trans_result.get(0).getRes();
    }
}

class BaiduTransResNode {
    String src;
    String dst;

    public String getRes() {
        return dst;
    }
}


