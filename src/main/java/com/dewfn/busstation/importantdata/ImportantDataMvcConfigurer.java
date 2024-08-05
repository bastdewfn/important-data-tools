package com.dewfn.busstation.importantdata;

import com.dewfn.busstation.importantdata.disableglobal.filter.UnGlobalImportantDataHandlerInterceptor;
import com.dewfn.busstation.importantdata.filter.ImportantDataHandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class ImportantDataMvcConfigurer implements WebMvcConfigurer {

    public ImportantDataMvcConfigurer(){}

    private boolean unGlobal=false;
    public ImportantDataMvcConfigurer(Boolean unGlobal){
        this.unGlobal=unGlobal;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if(unGlobal){
            registry.addInterceptor(new UnGlobalImportantDataHandlerInterceptor());
        }
        registry.addInterceptor(new ImportantDataHandlerInterceptor());

    }

}
