package com.dewfn.busstation.importantdata.filter;

import com.dewfn.busstation.importantdata.AccessImportDataException;
import com.dewfn.busstation.importantdata.ImportantDataStore;
import com.dewfn.busstation.importantdata.configcenter.ImportantDataConfigCenter;
import com.dewfn.busstation.importantdata.constant.Constants;
import com.dewfn.busstation.importantdata.util.MD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Objects;

public abstract class DecryptAccessAdapter implements IDecryptAccess {

    @Autowired(required = false)
    private ImportantDataDecryptFilter importantDataDecryptFilter;


    static final Logger logger = LoggerFactory.getLogger(DecryptAccessAdapter.class);

    /**
     * 获取当前用户使用次数key
     * @param userIdentify
     * @return
     */
    public String convertUsedCountKey(String userIdentify) {
        String usedCountKey =  getUsedKeyPrefix()+ MD5.GetMD5Code(userIdentify.trim()) + ":" + LocalDate.now().getDayOfMonth();
        return usedCountKey;
    }

    /**
     * 获取存储用的key前缀，可重写
     * @return
     */
    public String getUsedKeyPrefix(){
        return Constants.IMPORTANTDATA_DECRYPT_USER_USED_COUNT;
    }

    /**
     * 访问之前
     * @param importantDataStore
     * @param userIdentify
     * @return
     * @throws AccessImportDataException
     */
    @Override
    public final DecryptAccessToken beforeAccess(ImportantDataStore importantDataStore,String userIdentify,int accessCount) throws AccessImportDataException {

        if (Objects.nonNull(importantDataDecryptFilter)) {
            if(!importantDataDecryptFilter.filter(importantDataStore)){
                return new DecryptAccessToken(importantDataStore,null,0l,getUserIdentify());
            }
        }
        if(StringUtils.isEmpty(userIdentify)) {
            userIdentify = getUserIdentify();
        }
        if (StringUtils.isEmpty(userIdentify)) {
            throw new AccessImportDataException(AccessImportDataException.ErrorCode_NoUser, "无法获取用户信息");
        }

        String usedCountKey = convertUsedCountKey(userIdentify);
        Long usedCount = getCurrentUserUsedCount(usedCountKey);
        if(usedCount==null){
            usedCount=0l;
        }

        if (usedCount+accessCount > ImportantDataConfigCenter.getImportantData_Decrypt_Limit_Num()) {
            //超过次数
            throw new AccessImportDataException(AccessImportDataException.ErrorCode_AccessLimit, "你的解密次数为"+usedCount+",超过限制");
        }
        return new DecryptAccessToken(importantDataStore, usedCountKey, usedCount+accessCount, userIdentify);
    }

    /**
     * 访问之后
     * @param decryptAccessToken
     */
    @Override
    public final void afterAccess(DecryptAccessToken decryptAccessToken) {
        setCurrentUserUsedCount(decryptAccessToken.getUserUsedCountKey(), decryptAccessToken.getAccessUsedCount(), ImportantDataConfigCenter.getImportantdata_Decrypt_Limit_Timeout());
        log(decryptAccessToken);
    }

    /**
     * 记录访问日志，  可重写
     * @param decryptAccessToken
     */
    public void log(DecryptAccessToken decryptAccessToken) {
        logger.info("用户查看解密信息，用户:[{}],信息:[{}],已使用次数[{}]", decryptAccessToken.getUserIdentify(), decryptAccessToken.getImportantDataStore().getTag(), decryptAccessToken.getAccessUsedCount());
    }

    /**
     * 获取用户唯一标识，可重写， 默认使用  BaseImportantDataController.getUserInfo方法返回
     * @return
     */
    public  String getUserIdentify(){
        return null;
    }


    /**
     * 从redis等组件获取当前用户已访问次数
     * @param usedCountKey  用户标识
     * @return
     */
    public abstract Long getCurrentUserUsedCount(String usedCountKey);

    /**
     * 从redis等组件设置用户已访问次数
     * @param usedCountKey  用户标识
     * @param userUsedCount  已访问次数
     * @param expireSecond  过期时期  单位秒
     */
    public abstract void setCurrentUserUsedCount(String usedCountKey, long userUsedCount, long expireSecond);


}
