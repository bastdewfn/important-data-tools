package com.dewfn.busstation.importantdata.filter;

import com.dewfn.busstation.importantdata.*;
import com.dewfn.busstation.importantdata.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

@Order
@ControllerAdvice
public class ImportantDataResponseBodyAdviceAdapter implements  ResponseBodyAdvice {
    static final Logger logger = LoggerFactory.getLogger(ImportantDataResponseBodyAdviceAdapter.class);

    @Autowired(required = false)
    private IDecryptAccess decryptAccess;

//    public boolean supportsReturnType(MethodParameter methodParameter) {
//        DisableImportantDataAutoEncrypt disableImportantDataAutoEncrypt = methodParameter.getMethodAnnotation(DisableImportantDataAutoEncrypt.class);
//        if (Objects.nonNull(disableImportantDataAutoEncrypt)) {
//            ImportantDataAutoEncryptManage.disableImportantDataAutoEncryptLocal(disableImportantDataAutoEncrypt);
//            return true;
//        } else {
//            ImportantDataAutoEncryptManage.resetDisableImportantDataAutoEncryptLocal();
//            return false;
//        }
//
//    }
//
//    @Override
//    public void handleReturnValue(Object o, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws Exception {
//        ImportantDataAutoEncryptManage.resetDisableImportantDataAutoEncryptLocal();
//    }


    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        DisableImportantDataAutoEncrypt disableImportantDataAutoEncrypt = ImportantDataAutoEncryptManage.getDisableImportantDataAutoEncrypt();
        if(Objects.isNull(disableImportantDataAutoEncrypt)){
            return o;
        }
        //是否需要自动计数
        if (disableImportantDataAutoEncrypt.automaticAccessCount()) {
            ImportantDataStore importantDataStore = new ImportantDataStore(null, null, disableImportantDataAutoEncrypt.accessTag());
            String userIdentify = (decryptAccess instanceof DecryptAccessAdapter) ? ((DecryptAccessAdapter) decryptAccess).getUserIdentify() : null;
            DecryptAccessToken token = null;
            try {
                token = decryptAccess.beforeAccess(importantDataStore, userIdentify, disableImportantDataAutoEncrypt.accessCount());
            } catch (AccessImportDataException e) {
                throw new AdviceAccessImportDataException(e.getCode(), e);
            }
            decryptAccess.afterAccess(token);
        }
        return o;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        DisableImportantDataAutoEncrypt disableImportantDataAutoEncrypt = methodParameter.getMethodAnnotation(DisableImportantDataAutoEncrypt.class);
        if (Objects.nonNull(disableImportantDataAutoEncrypt)) {
            ImportantDataAutoEncryptManage.disableImportantDataAutoEncryptLocal(disableImportantDataAutoEncrypt);
            return true;
        }
        return false;
    }
}
