package com.dewfn.busstation.importantdata.disableglobal.filter.serializer;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.dewfn.busstation.importantdata.ImportantDataField;
import com.dewfn.busstation.importantdata.disableglobal.UnGlobalImportantDataAutoEncryptManage;
import com.dewfn.busstation.importantdata.util.ImportantDataConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class UnGlobalImportantDataEncryptSerializer extends JsonSerializer<String> {

    static final Logger logger = LoggerFactory.getLogger(UnGlobalImportantDataEncryptSerializer.class);


    private ImportantDataField importantDataFieldInfo;


    public UnGlobalImportantDataEncryptSerializer(ImportantDataField importantDataFieldInfo) {
        this.importantDataFieldInfo = importantDataFieldInfo;
    }

    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if(!UnGlobalImportantDataAutoEncryptManage.isEnableImportantDataAutoEncrypt()){
            jsonGenerator.writeString(value);
            return;
        }
        try {
            String s = ImportantDataConvertUtil.encrypt(value, this.importantDataFieldInfo);
            jsonGenerator.writeString(s);
        } catch (Exception e) {
            logger.error("Jack重要数据加密掩码异常," + this.importantDataFieldInfo.tag(), e);
            jsonGenerator.writeString("获取失败,刷新页面");
        }
        return;
    }


}
