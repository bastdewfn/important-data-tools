package com.dewfn.busstation.importantdata.util;


import com.alibaba.fastjson.JSON;
import com.dewfn.busstation.importantdata.ImportantDataField;
import com.dewfn.busstation.importantdata.ImportantDataFieldType;
import com.dewfn.busstation.importantdata.ImportantDataStore;
import com.dewfn.busstation.importantdata.security.DesensitizeAdapter;
import com.dewfn.busstation.importantdata.security.ImageUrlHandler;
import com.dewfn.busstation.importantdata.security.OtherHandler;
import com.dewfn.busstation.importantdata.security.UrlHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ImportantDataConvertUtil {

    private final static Map<ImportantDataFieldType, DesensitizeAdapter> desensitizeAdapterMap=new HashMap<>();


    static{
        desensitizeAdapterMap.put(ImportantDataFieldType.Other,new OtherHandler());
        desensitizeAdapterMap.put(ImportantDataFieldType.Archives,new OtherHandler());
        desensitizeAdapterMap.put(ImportantDataFieldType.Url,new UrlHandler());
        desensitizeAdapterMap.put(ImportantDataFieldType.ImageUrl,new ImageUrlHandler());
    }

    /**
     * 新加或修改掩码的转换器
     * @param dataFieldType 数据类型
     * @param desensitizeAdapter 掩码转换器
     */
    public static void registerDesensitizeAdapter(ImportantDataFieldType dataFieldType,DesensitizeAdapter desensitizeAdapter){
        desensitizeAdapterMap.put(dataFieldType,desensitizeAdapter);
    }

    static final Logger logger=LoggerFactory.getLogger(ImportantDataConvertUtil.class);

    private static String KEY="e22f7bab55e72b710acd0f74a3c2239d";//修改为你自己的


    private static String getKey(){
        return KEY;//可以动态生成key ，或从配置文件取
    }

    /**
     * 设置加密使用key
     * @param key
     */
    public synchronized static void  setKey(String key){
         KEY=key;
    }

    public static String decrypt(ImportantDataStore data) throws Exception {
        return AesUtil.decryptDataEcb(data.getData(), getKey());
    }

    /**
     * 对重要数据加密掩码
     * @param data  数据
     * @param importantDataField 数据标签
     * @return
     * @throws Exception
     */
     public static String encrypt(String data, ImportantDataField importantDataField) throws Exception {
      return   encrypt(data,importantDataField.value(),importantDataField.tag());
    }

    /**
     * 对重要数据加密掩码
     * @param data  数据
     * @param dataType 数据类型
     * @param tag 数据标签
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, ImportantDataFieldType dataType, String tag) throws Exception {
        ImportantDataStore importantDataStore=new ImportantDataStore();
        if(StringUtils.hasText(data)) {
            String maskStr = mask(data, dataType);
            String encryptData = AesUtil.encryptDataEcb(data, getKey());
            importantDataStore.setData(encryptData);
            importantDataStore.setMask(maskStr);
        }else{
            importantDataStore.setMask(data);
        }
        importantDataStore.setTag(tag);
        return JSON.toJSONString(importantDataStore);
    }

    public static String encrypt(String data) throws Exception {
        return encrypt(data, ImportantDataFieldType.Customize,null);
    }

    private static String mask(String data,ImportantDataFieldType dataType){
        DesensitizeAdapter desensitizeAdapter = desensitizeAdapterMap.getOrDefault(dataType, null);
        return desensitizeAdapter.doMask(data);
    }

    @Deprecated
    private static String mask(String data){
        if(data==null||data.trim().length()<2){
            return null;
        }
        int len=data.length();
        if(len>18){
            return getStarString2(data,6,len-10);
        }
        if(len>11){ //一般是身份证
            return getStarString2(data,6,4);
        }
        if(len>8){ //一般是电话
            return getStarString2(data,3,4);
        }
        return getStarString2(data,0,1);

    }


    private static String getStarString2(String content, int frontNum, int endNum) {

        if (frontNum >= content.length() || frontNum < 0) {
            return content;
        }
        if (endNum >= content.length() || endNum < 0) {
            return content;
        }
        if (frontNum + endNum >= content.length()) {
            return content;
        }
        String starStr = "";
        for (int i = 0; i < (content.length() - frontNum - endNum); i++) {
            starStr = starStr + "*";
        }
        return content.substring(0, frontNum) + starStr
                + content.substring(content.length() - endNum, content.length());

    }



    public static void main(String[] args) throws Exception {
        String ff=ImportantDataConvertUtil.encrypt("130000339");
        System.out.println(ImportantDataConvertUtil.decrypt(new ImportantDataStore(ff,null,"")));
        System.out.println(ImportantDataConvertUtil.decrypt(new ImportantDataStore("FUMuvgcjrSYQ64jmUgukGA==",null,"")));

    }
}
