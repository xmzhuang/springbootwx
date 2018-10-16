package com.zxm.util;

import com.thoughtworks.xstream.XStream;
import com.zxm.entity.MessageText;

import java.util.Date;

public class TextMessageUtil implements BaseMessageUtil<MessageText> {
    /**
     * 将发送消息封装成对应的xml格式
     */
    public  String messageToxml(MessageText message) {
        XStream xstream  = new XStream();
        xstream.alias("xml", message.getClass());
        return xstream.toXML(message);
    }
    /**
     * 封装发送消息对象,封装时，需要将调换发送者和接收者的关系
     * @param FromUserName
     * @param ToUserName
     */
    public  String initMessage(String FromUserName, String ToUserName) {
        MessageText text = new MessageText();
        text.setToUserName(FromUserName);
        text.setFromUserName(ToUserName);
        text.setContent("欢迎关注zrunning");
        text.setCreateTime(new Date().getTime());
        text.setMsgType("text");
        return  messageToxml(text);
    }

    public String initMessage(String FromUserName, String ToUserName,String Content) {
        MessageText text = new MessageText();
        text.setToUserName(FromUserName);
        text.setFromUserName(ToUserName);
        text.setContent(FromUserName+"输入的内容是："+Content);
        text.setCreateTime(new Date().getTime());
        text.setMsgType("text");
        return  messageToxml(text);
    }

    public String initMessage(String FromUserName, String ToUserName,String Content,String a) {
        MessageText text = new MessageText();
        text.setToUserName(FromUserName);
        text.setFromUserName(ToUserName);
        text.setContent(FromUserName+"输入的内容是："+Content);
        text.setCreateTime(new Date().getTime());
        text.setMsgType("text");
        return  messageToxml(text);
    }
}
