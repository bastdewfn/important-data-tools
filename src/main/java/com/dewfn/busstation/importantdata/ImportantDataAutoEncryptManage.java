package com.dewfn.busstation.importantdata;

import java.util.Objects;

public class ImportantDataAutoEncryptManage {
    private static final ThreadLocal<DisableImportantDataAutoEncrypt> disableImportantDataAutoEncryptLocal = new ThreadLocal<DisableImportantDataAutoEncrypt>();

    public static void disableImportantDataAutoEncryptLocal(DisableImportantDataAutoEncrypt disableImportantDataAutoEncrypt) {
        disableImportantDataAutoEncryptLocal.set(disableImportantDataAutoEncrypt);
    }

    public static void resetDisableImportantDataAutoEncryptLocal() {
        disableImportantDataAutoEncryptLocal.remove();
    }

    public static boolean isDisableImportantDataAutoEncrypt() {
        return Objects.nonNull(disableImportantDataAutoEncryptLocal.get());
    }

    public static DisableImportantDataAutoEncrypt getDisableImportantDataAutoEncrypt() {
        return disableImportantDataAutoEncryptLocal.get();
    }
}
