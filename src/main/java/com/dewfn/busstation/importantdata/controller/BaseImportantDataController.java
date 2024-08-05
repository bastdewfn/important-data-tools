package com.dewfn.busstation.importantdata.controller;

import com.dewfn.busstation.importantdata.DecryptMultipleResult;
import com.dewfn.busstation.importantdata.util.ImportantDataConvertUtil;
import com.dewfn.busstation.importantdata.ImportantDataStore;
import com.dewfn.busstation.importantdata.filter.DecryptAccessToken;
import com.dewfn.busstation.importantdata.filter.IDecryptAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/importantData")
@Controller
public abstract class BaseImportantDataController {
    static final Logger logger = LoggerFactory.getLogger(BaseImportantDataController.class);

    @Autowired
    IDecryptAccess decryptAccess;

    @PostMapping(value = "/decrypt")
    @ResponseBody
    public Object decrypt(@RequestBody ImportantDataStore importantDataStore) {
        // 判断查看信息次数
        // 加查看信息次数
        // 记录日志
        try {
            DecryptAccessToken decryptAccessToken = decryptAccess.beforeAccess(importantDataStore, getUserIdentify());
            String data = ImportantDataConvertUtil.decrypt(importantDataStore);
            decryptAccess.afterAccess(decryptAccessToken);
            return convertResult(data);
        } catch (Exception ex) {
            return errorHandler(ex);
        }
    }



    @PostMapping(value = "/multipleDecrypt")
    @ResponseBody
    public Object multipleDecrypt(@RequestBody List<ImportantDataStore> importantDataStores) {
        // 判断查看信息次数
        // 加查看信息次数
        // 记录日志
        List<DecryptMultipleResult> resultList = importantDataStores.stream().map(importantDataStore -> {
            DecryptMultipleResult result = new DecryptMultipleResult();
            result.setData(importantDataStore.getData());
            DecryptAccessToken decryptAccessToken;
            try {
                decryptAccessToken = decryptAccess.beforeAccess(importantDataStore, getUserIdentify());
            } catch (Exception ex) {
                logger.info("超过解密次数");
                result.setRealData("超过解密次数");
                return result;
            }
            String s = null;
            try {
                s = ImportantDataConvertUtil.decrypt(importantDataStore);
                decryptAccess.afterAccess(decryptAccessToken);
            } catch (Exception e) {
                s = "解密失败，请重试!" + e.getMessage();
            }
            result.setRealData(s);
            return result;
        }).collect(Collectors.toList());
        return convertListResult(resultList);
    }

    public Object errorHandler(Exception ex) {
        return ex.getMessage();
    }

    public abstract String getUserInfo();

    /**
     * 获取当前用户唯一标识， 根据此标识做次数判断
     *
     * @return
     */
    public String getUserIdentify() {
        return getUserInfo();
    }

    public abstract Object convertResult(String data);

    /**
     * 同时解密 多少时转为定义结果
     *
     * @param data
     * @return
     */
    public Object convertListResult(List<DecryptMultipleResult> data) {
        return data;
    }
}
