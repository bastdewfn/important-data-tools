package com.dewfn.busstation.importantdata.filter.serializer;


import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.ContextValueFilter;
import com.dewfn.busstation.importantdata.ImportantDataAutoEncryptManage;
import com.dewfn.busstation.importantdata.util.ImportantDataConvertUtil;
import com.dewfn.busstation.importantdata.ImportantDataField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class ImportantDataContextValueFilter implements ContextValueFilter  {
    static final Logger logger= LoggerFactory.getLogger(ImportantDataContextValueFilter.class);

    @Override
    public Object process(BeanContext context, Object object, String name, Object value) {
        if(Objects.isNull(context)){
            return value;
        }
        ImportantDataField importantDataField= context.getAnnation(ImportantDataField.class);
        if(Objects.nonNull(importantDataField) && !ImportantDataAutoEncryptManage.isDisableImportantDataAutoEncrypt()) {
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
