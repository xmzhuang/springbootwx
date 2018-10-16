package com.zxm.util;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageUtil {

    /**
     * 将微信的请求中参数转成map
     * @param request
     * @return
     */
    public static Map<String,String> xmlToMap(HttpServletRequest request){
        Map<String,String> map = new HashMap<String,String>();
        SAXReader reader = new SAXReader();
        InputStream in = null;
        try {
            in = request.getInputStream();
            Document doc = reader.read(in);
            Element root = doc.getRootElement();
            List<Element> list = root.elements();
            for (Element element : list) {
                map.put(element.getName(), element.getText());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return map;
    }


    /**
     * 将 XML 转换为 Map
     *
     * @param request
     * @return
     * @throws IOException
     * @throws DocumentException
     *//*
    @SuppressWarnings("unchecked")
    public static Map<String, String> xmlToMap(HttpServletRequest request)
            throws IOException, DocumentException {
        Map<String, String> map = new HashMap<String, String>();
        SAXReader reader = new SAXReader();
        InputStream ins = request.getInputStream();
        Document doc = reader.read(ins);
        Element root = doc.getRootElement();
        List<Element> list = root.elements();
        for (Element e : list) {
            map.put(e.getName(), e.getText());
        }
        ins.close();
        return map;
    }

    *//**
     * 将文本消息（TextMessage 对象）转换为 xml 格式
     *
     * @param textMessage
     * @return
     *//*
    public static String textMessageToXML(TextMessage textMessage) {
        XStream xtream = new XStream();
        xtream.alias("xml", textMessage.getClass());
        String xmlStr = xtream.toXML(textMessage);
        return xmlStr;
    }*/
}
