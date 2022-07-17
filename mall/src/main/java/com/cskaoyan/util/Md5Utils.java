package com.cskaoyan.util;

import java.security.MessageDigest;

/**
 * Md5加密算法
 *
 * @author Zah
 * @date 2022/07/17 21:09
 */
public class Md5Utils {

    public static String getMd5(String content) throws Exception {

        // 获得消息摘要算法
        MessageDigest messageDigest = MessageDigest.getInstance("md5");
        byte[] contentBytes = content.getBytes();
        // 此处已经有了加密结果
        byte[] resultBytes = messageDigest.digest(contentBytes);
        // 下面转为十六进制的形式
        StringBuffer stringBuffer = new StringBuffer();
        for (byte resultByte : resultBytes) {
            int temp = resultByte & 0xff;
            String s = Integer.toHexString(temp);
            if (s.length()==1){
                stringBuffer.append(0);
            }
            stringBuffer.append(s);
        }
        // 返回加密结果是字符串形式的十六进制密文
        return stringBuffer.toString();
    }
}
