package com.zxm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class CheckUtil {
    private static Logger logger = LoggerFactory.getLogger(CheckUtil.class);

    /**
     * 该 token 值要和微信公众号那个网页里面的 token 一致，否则校验会出错
     */
    public static final String TOKEN = "zxmtest";



    public static boolean checkSignature(String signature, String timestamp,
                                         String nonce) {

        String[] arr = new String[] { TOKEN, timestamp, nonce };
        logger.debug("排序之前的数组：" + arr);
        // 按自然顺序排序
        Arrays.sort(arr);
        logger.debug("排序以后的数组：" + arr);

        // 生成字符串
        StringBuffer content = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }

        // sha1 加密
        String temp = getSha1(content.toString());
        return temp.equals(signature);
    }

    private static String getSha1(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
