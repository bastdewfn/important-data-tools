package com.dewfn.busstation.importantdata.disableglobal;

import java.util.Objects;

/**
 * 非全局自动加密情况 .   是否启用加密
 */
public class UnGlobalImportantDataAutoEncryptManage {
    private static final ThreadLocal<Boolean> disableImportantDataAutoEncryptLocal = new ThreadLocal<Boolean>();

    public static void enableImportantDataAutoEncryptLocal() {
        disableImportantDataAutoEncryptLocal.set(true);
    }

    public static void resetDisableImportantDataAutoEncryptLocal() {
        disableImportantDataAutoEncryptLocal.remove();
    }

    public static boolean isEnableImportantDataAutoEncrypt() {
        return Objects.nonNull(disableImportantDataAutoEncryptLocal.get());
    }

    public static boolean getEnableImportantDataAutoEncrypt() {
        return disableImportantDataAutoEncryptLocal.get();
    }


}
