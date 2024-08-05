package com.dewfn.busstation.importantdata.configcenter;
import com.dewfn.busstation.importantdata.constant.Constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;


public class ImportantDataConfigCenter {
    private static final Logger logger = LoggerFactory.getLogger(ImportantDataConfigCenter.class);

    private static String configCentenrAppUk;

    public static void init() {
        init("");
    }

    public static void init(String configCentenrAppUk){

    }

    public static String getConfigCentenrAppUk() {
        return configCentenrAppUk;
    }

    public static int getImportantData_Decrypt_Limit_Num() {
        return importantData_Decrypt_Limit_Num;
    }

    private static synchronized void setImportantData_Decrypt_Limit_Num(String value) {
        if(StringUtils.isEmpty(value)){
            return;
        }
        try {
            importantData_Decrypt_Limit_Num = Integer.parseInt(value);
            ImportantDataConfigCenter.logger.info("统一配置有配置变化推送,重要数据解密限制次数： " + value);
        } catch (Exception ex) {
            importantData_Decrypt_Limit_Num = 0;
            ImportantDataConfigCenter.logger.error("转换为重要数据解密限制次数异常： ", ex);
        }
    }

    private static int importantData_Decrypt_Limit_Num = 0;
    /**
     * 重要数据解密限制时间 分钟单位，默认1天
     */
    private static long IMPORTANTDATA_DECRYPT_LIMIT_TIMEOUT = 24 * 60 * 60l;

    public static long getImportantdata_Decrypt_Limit_Timeout() {
        return IMPORTANTDATA_DECRYPT_LIMIT_TIMEOUT;
    }

    private static synchronized void setImportantdata_Decrypt_Limit_Timeout(String value) {

        if(StringUtils.isEmpty(value)){
            return;
        }
        try {
            int v = Integer.parseInt(value);
            if(v>1440||v<=0){
                ImportantDataConfigCenter.logger.info("统一配置有配置变化推送,重要数据解密限制时间大于了24小时,或小于0，设置无效： " + value);
                return;
            }
            IMPORTANTDATA_DECRYPT_LIMIT_TIMEOUT = v*60l;//分钟转为秒
            ImportantDataConfigCenter.logger.info("统一配置有配置变化推送,重要数据解密限制时间： " + value);
        } catch (Exception ex) {
            IMPORTANTDATA_DECRYPT_LIMIT_TIMEOUT = 24 * 60 * 60l;
            ImportantDataConfigCenter.logger.error("转换为重要数据解密限制时间异常： ", ex);
        }
    }

}
