package com.dewfn.busstation.importantdata.filter;

import com.dewfn.busstation.importantdata.ImportantDataAutoEncryptManage;
import com.dewfn.busstation.importantdata.DisableImportantDataAutoEncrypt;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class ImportantDataHandlerInterceptor implements HandlerInterceptor {

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        if(Objects.nonNull(handler)&&handler instanceof HandlerMethod){
            DisableImportantDataAutoEncrypt disableImportantDataAutoEncrypt=((HandlerMethod)handler).getMethodAnnotation(DisableImportantDataAutoEncrypt.class);
            if(Objects.nonNull(disableImportantDataAutoEncrypt)){
                ImportantDataAutoEncryptManage.resetDisableImportantDataAutoEncryptLocal();
            }
        }
    }
}
