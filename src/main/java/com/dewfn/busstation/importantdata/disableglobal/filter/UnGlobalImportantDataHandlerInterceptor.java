package com.dewfn.busstation.importantdata.disableglobal.filter;

import com.dewfn.busstation.importantdata.disableglobal.EnableMethodImportantDataAutoEncrypt;
import com.dewfn.busstation.importantdata.disableglobal.UnGlobalImportantDataAutoEncryptManage;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class UnGlobalImportantDataHandlerInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (Objects.nonNull(handler) && handler instanceof HandlerMethod) {
            EnableMethodImportantDataAutoEncrypt disableImportantDataAutoEncrypt = ((HandlerMethod) handler).getMethodAnnotation(EnableMethodImportantDataAutoEncrypt.class);
            if(disableImportantDataAutoEncrypt!=null){
                UnGlobalImportantDataAutoEncryptManage.enableImportantDataAutoEncryptLocal();
            }
        }
        return true;
    }
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        if(Objects.nonNull(handler)&&handler instanceof HandlerMethod){
            EnableMethodImportantDataAutoEncrypt disableImportantDataAutoEncrypt=((HandlerMethod)handler).getMethodAnnotation(EnableMethodImportantDataAutoEncrypt.class);
            if(Objects.nonNull(disableImportantDataAutoEncrypt)){
                UnGlobalImportantDataAutoEncryptManage.resetDisableImportantDataAutoEncryptLocal();
            }
        }
    }
}
