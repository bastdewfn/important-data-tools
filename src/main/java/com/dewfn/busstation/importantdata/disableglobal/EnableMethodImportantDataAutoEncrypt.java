package com.dewfn.busstation.importantdata.disableglobal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启用自动加密脱敏注解，使用在controller方法上，当EnableAutoMvcImportantData中unGlobal
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableMethodImportantDataAutoEncrypt {
}
