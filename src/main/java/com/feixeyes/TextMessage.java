package com.feixeyes;

/**
 * Created by feixeyes on 17/9/2.
 */
public class TextMessage extends BaseMessage {
    /**
     * 回复的消息内容
     */
    TextMessage(){
        this.setMsgType("text");
        this.setCreateTime(System.currentTimeMillis());
    }
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
