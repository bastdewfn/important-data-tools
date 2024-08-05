package com.dewfn.busstation.importantdata.disableglobal.filter.serializer;


import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.ContextValueFilter;
import com.dewfn.busstation.importantdata.ImportantDataField;
import com.dewfn.busstation.importantdata.disableglobal.UnGlobalImportantDataAutoEncryptManage;
import com.dewfn.busstation.importantdata.util.ImportantDataConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class UnGlobalImportantDataContextValueFilter implements ContextValueFilter  {
    static final Logger logger= LoggerFactory.getLogger(UnGlobalImportantDataContextValueFilter.class);

    @Override
    public Object process(BeanContext context, Object object, String name, Object value) {
        if(Objects.isNull(context)){
            return value;
        }
        if(!UnGlobalImportantDataAutoEncryptManage.isEnableImportantDataAutoEncrypt()){
            return value;
        }
        ImportantDataField importantDataField= context.getAnnation(ImportantDataField.class);
        if(Objects.nonNull(importantDataField)) {
            try {
                String  s = ImportantDataConvertUtil.encrypt((String) value,importantDataField);
                return s;
            } catch (Exception e) {
                logger.error("Fast重要数据加密掩码异常,"+importantDataField.tag(),e);
                return "获取失败,刷新页面";
            }
        }
        return value;
    }
}
