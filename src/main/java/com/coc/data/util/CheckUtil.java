package com.coc.data.util;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author guokaiqiang
 * @date 2020/10/3 09:58
 */
public class CheckUtil {

    private static String token = "gkq";

    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        if (ObjectUtils.isEmpty(timestamp)
        || ObjectUtils.isEmpty(nonce)) {
            return false;
        }
        String[] arr = new String[]{token, timestamp, nonce};
        Arrays.sort(arr);
        String content = StringUtils.join(arr, "");
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(content.toString().getBytes());
            String tmpStr = byteToStr(digest);

            return !ObjectUtils.isEmpty(tmpStr) && tmpStr.equalsIgnoreCase(signature);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    private static String byteToHexStr(byte mByte) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
            'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;

    }
}
