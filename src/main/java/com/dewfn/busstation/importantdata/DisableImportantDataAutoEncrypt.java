package com.dewfn.busstation.importantdata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 禁用自动加密脱敏注解，使用在controller方法上
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DisableImportantDataAutoEncrypt {

    /**
     *
     * @return 是否算
     */
    boolean automaticAccessCount() default false;
    /**
     * @return 计算访问解密次数，默认为1，automaticAccessCount 为true时生效
     */
    int accessCount() default 1;

    /**
     * 访问时记录的标签  automaticAccessCount 为true时生效
     * @return
     */
    String accessTag() default "";

}
