package com.dewfn.busstation.importantdata.security;


public interface DesensitizeHandler {
    String doMask(String fieldValue);

    Long doMask();

    DesensitizeType getFieldType();
}
