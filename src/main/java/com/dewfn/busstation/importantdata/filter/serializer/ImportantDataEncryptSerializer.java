package com.dewfn.busstation.importantdata.filter.serializer;


import com.dewfn.busstation.importantdata.ImportantDataAutoEncryptManage;
import com.dewfn.busstation.importantdata.ImportantDataField;
import com.dewfn.busstation.importantdata.disableglobal.filter.serializer.UnGlobalImportantDataEncryptSerializer;
import com.dewfn.busstation.importantdata.util.ImportantDataConvertUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class ImportantDataEncryptSerializer extends JsonSerializer<String>
        implements ContextualSerializer {

    static final Logger logger = LoggerFactory.getLogger(ImportantDataEncryptSerializer.class);


    private ImportantDataField importantDataFieldInfo;

    ImportantDataEncryptSerializer() {
        this(null);
    }

    public ImportantDataEncryptSerializer(ImportantDataField importantDataFieldInfo) {
        this.importantDataFieldInfo = importantDataFieldInfo;
    }

    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        if (ImportantDataAutoEncryptManage.isDisableImportantDataAutoEncrypt()) {
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

    }

    @Override
    public JsonSerializer<?> createContextual(final SerializerProvider
                                                      serializerProvider, final BeanProperty beanProperty) throws JsonMappingException {
        if (beanProperty != null) { // 为空直接跳过
            ImportantDataField importantDataField =
                    beanProperty.getAnnotation(ImportantDataField.class);
            if (importantDataField != null) {
                if (serializerProvider.getFilterProvider() != null) {
                    PropertyFilter propertyFilter = serializerProvider.getFilterProvider().findPropertyFilter("importantDataEncryptJacksonFilter", null);
                    if (propertyFilter != null) {
                        return new ImportantDataEncryptSerializer(importantDataField);
                    } else {
                        propertyFilter = serializerProvider.getFilterProvider().findPropertyFilter("disableGlobalImportantDataEncryptJacksonFilter", null);
                        if (propertyFilter != null) {
                            return new UnGlobalImportantDataEncryptSerializer(importantDataField);
                        }
                    }
                }
            }
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }
        return serializerProvider.findNullValueSerializer(beanProperty);
    }

}
