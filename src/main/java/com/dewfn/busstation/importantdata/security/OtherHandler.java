package com.dewfn.busstation.importantdata.security;


public class OtherHandler extends DesensitizeAdapter {
    public String doMask(String data) {
        return this.isBlank(data) ? "" : this.hide(data, 3, data.length());
    }

    public DesensitizeType getFieldType() {
        return DesensitizeType.OTHER_CARD;
    }
}
