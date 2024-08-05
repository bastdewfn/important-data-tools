package com.dewfn.busstation.importantdata;

/**
 * 多条同时解密时返回的实体
 */
public class DecryptMultipleResult {
   private String realData;
   private String data;


    public String getRealData() {
        return realData;
    }

    public void setRealData(String realData) {
        this.realData = realData;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

