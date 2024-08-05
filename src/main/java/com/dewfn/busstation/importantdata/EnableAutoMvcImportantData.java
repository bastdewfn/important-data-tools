package com.dewfn.busstation.importantdata;

import com.dewfn.busstation.importantdata.configcenter.ImportantDataImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;
/**
 * @author wei.hu
 * @date 2023/11/19
 */

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ImportantDataImportBeanDefinitionRegistrar.class})
public @interface EnableAutoMvcImportantData {
    String configCentenrAppUk() default  "";

    /**
     * 非全局启用自动注解, 需要每个方法上加@EnableMethodImportantDataAutoEncrypt才会加密脱敏
     * @return
     */
    boolean unGlobal() default false;
}
