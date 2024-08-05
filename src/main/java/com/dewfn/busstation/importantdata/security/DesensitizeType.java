package com.dewfn.busstation.importantdata.security;

public enum DesensitizeType {
    EMAIL,
    MOBILE,
    PASSWORD,
    USER_ID,
    IP,
    ADDRESS,
    OTHER_CARD,
    BANK_CARD,
    CAR_LICENSE,
    GENDER,
    BIRTHDAY,
    CHINESE_NAME,
    ID_CARD;

    private DesensitizeType() {
    }
}
