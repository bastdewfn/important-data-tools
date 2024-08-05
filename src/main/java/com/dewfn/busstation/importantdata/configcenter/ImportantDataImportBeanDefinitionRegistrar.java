package com.dewfn.busstation.importantdata.configcenter;

import com.dewfn.busstation.importantdata.disableglobal.UnGlobalImportantDataConfiguration;
import com.dewfn.busstation.importantdata.ImportantDataConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

public class ImportantDataImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    static final Logger logger= LoggerFactory.getLogger(ImportantDataImportBeanDefinitionRegistrar.class);

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<String,Object> enableAutoMvcImportantDataMetadata= importingClassMetadata.getAnnotationAttributes("com.dewfn.busstation.importantdata.EnableAutoMvcImportantData");
        ImportantDataConfigCenter.init((String) enableAutoMvcImportantDataMetadata.get("configCentenrAppUk"));
        boolean unGlobal= (boolean)enableAutoMvcImportantDataMetadata.get("unGlobal");
        BeanDefinitionBuilder builder=null;
        if(unGlobal){
            builder = BeanDefinitionBuilder.rootBeanDefinition(UnGlobalImportantDataConfiguration.class);
        }else{
            builder = BeanDefinitionBuilder.genericBeanDefinition(ImportantDataConfiguration.class);
        }
        BeanDefinition  beanDefinition = builder.getBeanDefinition();
        registry.registerBeanDefinition("importantDataConfiguration", beanDefinition);
    }
}
