package com.dewfn.busstation.importantdata.security;


public class ImageUrlHandler extends DesensitizeAdapter {

    public String doMask(String data) {
        return isBlank(data)?"":"//file.40017.cn/groundtraffic/linebus/linebus/backend/img-default.png";
    }

    public DesensitizeType getFieldType() {
        return DesensitizeType.OTHER_CARD;
    }
}
