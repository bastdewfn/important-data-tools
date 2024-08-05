package com.dewfn.busstation.importantdata;

public class AccessImportDataException extends Exception{
    private String code;

    public AccessImportDataException(String code,String errorMesssage){
        super(errorMesssage);

        this.code=code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 没有用户信息
     */
    public final static String ErrorCode_NoUser="000001";
    /**
     * 超过解密次数
     */
    public final static String ErrorCode_AccessLimit="000002";
}
