package com.dewfn.busstation.importantdata.filter;

import com.dewfn.busstation.importantdata.ImportantDataStore;

public class DecryptAccessToken {
    /**
     * 要解密数据
     */
    private ImportantDataStore importantDataStore;
    /**
     * 用户使用次数的key, 实例化时标识
     */
    private String userUsedCountKey;
    /**
     * 已访问次数
     */
    private Long accessUsedCount;
    /**
     * 用户唯一标识
     */
    private String userIdentify;

    public DecryptAccessToken() {
    }

    public DecryptAccessToken(ImportantDataStore importantDataStore, String userUsedCountKey, Long accessUsedCount, String userIdentify) {
        this.importantDataStore = importantDataStore;
        this.userUsedCountKey = userUsedCountKey;
        this.accessUsedCount = accessUsedCount;
        this.userIdentify = userIdentify;
    }

    public ImportantDataStore getImportantDataStore() {
        return importantDataStore;
    }

    public void setImportantDataStore(ImportantDataStore importantDataStore) {
        this.importantDataStore = importantDataStore;
    }

    public String getUserUsedCountKey() {
        return userUsedCountKey;
    }

    public void setUserUsedCountKey(String userUsedCountKey) {
        this.userUsedCountKey = userUsedCountKey;
    }

    public Long getAccessUsedCount() {
        return accessUsedCount;
    }

    public void setAccessUsedCount(Long accessUsedCount) {
        this.accessUsedCount = accessUsedCount;
    }

    public String getUserIdentify() {
        return userIdentify;
    }

    public void setUserIdentify(String userIdentify) {
        this.userIdentify = userIdentify;
    }
}
