package com.dewfn.busstation.importantdata;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.dewfn.busstation.importantdata.filter.serializer.ImportantDataEncryptSerializer;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside  //让此注解可以被Jackson扫描到
@JsonSerialize(using = ImportantDataEncryptSerializer.class)  //配置处理此注解的序列化处理类
public @interface ImportantDataField {

    /**
     * 数据类型
     * @return
     */
    ImportantDataFieldType value() ;

    /**
     * 数据标签
     * @return
     */
    String tag() default "";


}
