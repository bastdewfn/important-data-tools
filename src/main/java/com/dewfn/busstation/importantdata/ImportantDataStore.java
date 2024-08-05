package com.dewfn.busstation.importantdata;

public class ImportantDataStore{
    /**
     * 数据，加密后或加密前
     */
    private String data;
    /*
    掩码后数据
     */
    private String mask;
    //标识，与 @ImportantDataField中 tag一致
    private String tag;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }
    public ImportantDataStore(){}

    public ImportantDataStore(String data, String mask,String tag) {
        this.data = data;
        this.mask = mask;
        this.tag = tag;
    }
}

