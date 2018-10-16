package com.zxm.controller;

import com.zxm.util.CheckUtil;
import com.zxm.util.MessageUtil;
import com.zxm.util.TextMessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@RestController
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    // 第 1 步，我们测试接入，这一部分请参考微信的 《接入指南》
    // 由于我的项目是一个 springboot 项目，因此我启动服务器以后是不用输入项目根路径的
    // 在这里，我们要将 http://liwei.tunnel.mobi/weixin 这个地址映射出去
    // 端口映射工具有花生壳、ngrok（tunnel）
    @RequestMapping(value = "/wx", method = RequestMethod.GET)
    public void weixin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        logger.debug("参数-微信加密签名 signature：" + signature);
        logger.debug("参数-时间戳 timestamp：" + timestamp);
        logger.debug("参数-随机数 nonce：" + nonce);
        logger.debug("参数-随机字符串 echostr：" + echostr);
        // 这里须要校验这些参数是否合法，即是否来自微信公众号
        boolean r = CheckUtil.checkSignature(signature, timestamp, nonce);
        if (r) {
            System.out.println("接入验证通过。");
            // 如果验证通过，将随机字符串返回
            out.print(echostr);
        }
    }

   /* // 第 2 步，我们接收一个文本消息
    // 步骤是这样的：当我们向公众号发送一个消息的时候，微信公众号的服务器，会将
    // 我们发送的数据以 post 的方式转发到我们自己的服务器上
    // 数据交换的格式是 XML
    @RequestMapping(value = "/wx", method = RequestMethod.POST)
    public void receivingText(HttpServletRequest request, HttpServletResponse response)
            throws IOException, DocumentException {
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        // request.setCharacterEncoding("UTF-8");
        // response.setCharacterEncoding("UTF-8");
        Map<String, String> map = MessageUtil.xmlToMap(request);
        String ToUserName = map.get("ToUserName");
        String FromUserName = map.get("FromUserName");
        String CreateTime = map.get("CreateTime");
        String MsgType = map.get("MsgType");
        String Content = map.get("Content");
        // 以上是接收消息

        *//**
         * 下面我们要封装一个 TextMessage 对象 以 XML 字符串的方式转发到用户的手机界面
         *//*
        TextMessage text = null;
        if ("text".endsWith(MsgType)) {
            text = new TextMessage();
            text.setToUserName(FromUserName);
            text.setFromUserName(ToUserName);
            text.setCreateTime(CreateTime);
            text.setMsgType(MsgType);
            text.setContent("您发送的消息是：" + Content + "，该消息由zxm服务器处理。");
        }
        String strXML = MessageUtil.textMessageToXML(text);
        *//*
         * String strXML = "<xml><ToUserName><![CDATA[" + FromUserName
         * +"]]></ToUserName><FromUserName><![CDATA[" + ToUserName +"]]>" +
         * "</FromUserName>" + "<CreateTime>" + CreateTime +
         * "</CreateTime><MsgType><![CDATA[" + MsgType +
         * "]]></MsgType><Content><![CDATA[" + Content +"]]></Content></xml>";
         * System.out.println(strXML);
         *//*

        logger.debug(strXML);
        // 响应消息
        PrintWriter out = response.getWriter();
        out.print(strXML);
    }*/

    @RequestMapping(value = "wx",method=RequestMethod.POST)
    public void receivingText(HttpServletRequest request,HttpServletResponse response){
        response.setCharacterEncoding("utf-8");
        PrintWriter out = null;
        //将微信请求xml转为map格式，获取所需的参数
        Map<String,String> map = MessageUtil.xmlToMap(request);
        String ToUserName = map.get("ToUserName");
        String FromUserName = map.get("FromUserName");
        String MsgType = map.get("MsgType");
        String Content = map.get("Content");
        System.out.println("abc");
        String message = null;
        //处理文本类型，实现输入1，回复相应的封装的内容
        if("text".equals(MsgType)) {
            TextMessageUtil textMessage = new TextMessageUtil();
            if("zxm".equals(Content)){
                message = textMessage.initMessage(FromUserName, ToUserName, "我爱小刁宝","");
            }else{
                message = textMessage.initMessage(FromUserName, ToUserName, Content);
            }


        }
        try {
            out = response.getWriter();
            out.write(message);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        out.close();
    }

}
