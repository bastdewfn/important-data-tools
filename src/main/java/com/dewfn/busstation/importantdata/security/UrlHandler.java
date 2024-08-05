package com.dewfn.busstation.importantdata.security;


public class UrlHandler extends DesensitizeAdapter {

    public String doMask(String data) {
        return data;
    }

    public DesensitizeType getFieldType() {
        return DesensitizeType.OTHER_CARD;
    }
}
