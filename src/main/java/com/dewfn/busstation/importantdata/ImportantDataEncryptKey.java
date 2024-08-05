package com.dewfn.busstation.importantdata;

import java.util.Objects;

public class ImportantDataEncryptKey {

    public ImportantDataEncryptKey(String key) {
        Objects.requireNonNull(key);
        this.key = key;
    }

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
