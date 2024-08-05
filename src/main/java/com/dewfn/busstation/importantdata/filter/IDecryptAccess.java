package com.dewfn.busstation.importantdata.filter;

import com.dewfn.busstation.importantdata.ImportantDataStore;
import com.dewfn.busstation.importantdata.AccessImportDataException;


public interface IDecryptAccess {

    /**
     * 访问解密重要数据前执行方法，主要判断次数限制
     * @param importantDataStore 解密数据
     * @param userIdentify 用户唯一标识
     * @return
     * @throws AccessImportDataException
     */
    default DecryptAccessToken beforeAccess(ImportantDataStore importantDataStore, String userIdentify) throws AccessImportDataException{
        return beforeAccess(importantDataStore,userIdentify,1);
    }

    /**
     * 访问解密重要数据前执行方法，主要判断次数限制
     * @param importantDataStore 解密数据
     * @param userIdentify 用户唯一标识
     * @param accessCount 此次的访问次数
     * @return
     * @throws AccessImportDataException
     */
    DecryptAccessToken beforeAccess(ImportantDataStore importantDataStore,String userIdentify,int accessCount) throws AccessImportDataException;

    /**
     * 解密重要数据后执行方法，记录日志，存储访问次数等
     * @param decryptAccessToken
     */
    void afterAccess(DecryptAccessToken decryptAccessToken) ;

}
