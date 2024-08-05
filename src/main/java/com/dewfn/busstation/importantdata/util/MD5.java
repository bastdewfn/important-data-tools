package com.dewfn.busstation.importantdata.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
 * MD5 算法
*/
public class MD5 {

    // 全局数组
    private final static String[] strDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public MD5() {
    }

    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    // 返回形式只为数字
    private static String byteToNum(byte bByte) {
        int iRet = bByte;
        System.out.println("iRet1=" + iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        return String.valueOf(iRet);
    }

    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    /**
     * @param @param  strObj
     * @param @return
     * @return String
     * @throws
     * @Title: md5加密
     * @Description:
     */
    public static String GetMD5Code(String strObj) {
        String resultString = null;
        try {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }


    public static byte[] convertToBytes(String srcStr) {
        return  convertToBytes(srcStr,"UTF8");
    }


    public static byte[] convertToBytes(String srcStr, String charsetName) {
        byte[] md5Bytes = null;
        try {
            byte[] byteArray = srcStr.getBytes(charsetName);
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5Bytes = md5.digest(byteArray);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return md5Bytes;
    }

    public static String convertToChar(String src){
        byte[] md5Bytes = null;
        try {
            char[] charArray = src.toCharArray();
            byte[] byteArray = new byte[charArray.length];

            for (int i = 0; i < charArray.length; i++){
                byteArray[i] = (byte) charArray[i];
            }
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5Bytes = md5.digest(byteArray);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return byte2hex(md5Bytes);
    }
    // 加密成十六进制字符串
    public static String convertToHexStr(String srcStr, String charsetName) {
        String md5Str = byte2hex(convertToBytes(srcStr,charsetName));
        return md5Str;
    }
    // 加密成十六进制字符串
    public static String convertToHexStr(String srcStr) {
        String md5Str = byte2hex(convertToBytes(srcStr));
        return md5Str;
    }
    // 加密成十六进制字符串
    public static String convertToHexStrWithKey(String srcStr, String key) {
        String md5Str = byte2hex(convertToBytes(srcStr+key));
        return md5Str;
    }
    public static String convertToHexKeyWithStr(String key, String srcStr) {
        String md5Str = byte2hex(convertToBytes(key+srcStr));
        return md5Str;
    }
    // 把字节数组转换成十六进制字符串
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            }
            else {
                hs = hs + stmp;
            }
        }
        return hs.toLowerCase();
    }


}
