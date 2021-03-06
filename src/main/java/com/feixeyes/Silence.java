/**
 * Created by feixeyes on 17/9/2.
 */
package com.feixeyes;

import com.baidu.translate.TransApi;
import com.feixeyes.utils.CheckUtil;
import com.feixeyes.utils.XMLUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;


@RestController
@EnableAutoConfiguration
public class Silence {



    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    @RequestMapping(value = "/wx",method = RequestMethod.GET)
    String handleGet(HttpServletRequest request) {
        if(request.getParameter("signature") ==null) {
            return "Hello wechat, there is no input token! ";
        }
        // 接收微信服务器以Get请求发送的4个参数
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
            return echostr ;        // 校验通过，原样返回echostr参数内容
        }

        return "Token check Error!";
    }

    @RequestMapping(method = RequestMethod.POST)
    String handlePost(HttpServletRequest request, HttpServletResponse response ) throws UnsupportedEncodingException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");


        String responseStr = "I'm silence!";
        try{
            Map<String,String> infoMap = parseXml(request);
            for( Map.Entry<String,String> en : infoMap.entrySet()){
                System.out.println(en.getKey()+":" + en.getValue());
            }

            String toUserName = infoMap.get("ToUserName");
            String fromUserName = infoMap.get("FromUserName") ;

            switch (infoMap.get("MsgType")) {
                case "text": responseStr = processTextMsg(infoMap.get("Content")); break;
                case "voice":
                case "image":
                case "location":
                default : responseStr = "I don't know what's you say!";
            }

            String res = mkXML(responseStr,toUserName,fromUserName); //swap to and from username
            System.out.println(res);
            return res;
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

        return responseStr;
    }

    private String mkXML(String responseStr, String fromUserName, String toUserName){
        TextMessage textMsg = new TextMessage();
        textMsg.setFromUserName(fromUserName);
        textMsg.setToUserName(toUserName);
        textMsg.setContent(responseStr);
        return XMLUtils.textMessageToXml(textMsg);
    }


    private String processTextMsg(String ask) throws UnsupportedEncodingException {
        return TransApi.trans2ENRes(ask);
    }

    private Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();

        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流

        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点

        @SuppressWarnings("unchecked")
        List<Element> elementList = root.elements();

        // 遍历所有子节点
        for (Element e : elementList)
            map.put(e.getName(), e.getText());

        // 释放资源
        inputStream.close();

        return map;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Silence.class, args);
    }


}


