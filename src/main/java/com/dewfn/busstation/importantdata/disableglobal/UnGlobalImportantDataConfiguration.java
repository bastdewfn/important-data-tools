package com.dewfn.busstation.importantdata.disableglobal;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.dewfn.busstation.importantdata.disableglobal.filter.serializer.UnGlobalImportantDataContextValueFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.dewfn.busstation.importantdata.ImportantDataEncryptKey;
import com.dewfn.busstation.importantdata.ImportantDataMvcConfigurer;
import com.dewfn.busstation.importantdata.filter.ImportantDataResponseBodyAdviceAdapter;
import com.dewfn.busstation.importantdata.filter.serializer.ImportantDataEncryptJacksonFilter;
import com.dewfn.busstation.importantdata.util.ImportantDataConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.TimeZone;

/**
 * @author wei.hu  非全局自动注解配置
 * @date 2023/11/19
 */

public class UnGlobalImportantDataConfiguration {


    @Autowired(required = false)
    private ImportantDataEncryptKey importantDataEncryptKey;

    @PostConstruct
    void init(){
        if(Objects.nonNull(importantDataEncryptKey)) {
            ImportantDataConvertUtil.setKey(importantDataEncryptKey.getKey());
        }
    }


    @ConditionalOnMissingBean(ImportantDataResponseBodyAdviceAdapter.class)
    @Bean
    public ImportantDataResponseBodyAdviceAdapter importantDataResponseBodyAdviceAdapter(){
        return new ImportantDataResponseBodyAdviceAdapter();
    }

    @ConditionalOnMissingBean(ImportantDataMvcConfigurer.class)
    @Bean
    public ImportantDataMvcConfigurer importantDataMvcConfigurer(){
        return new ImportantDataMvcConfigurer(true);
    }

    public UnGlobalImportantDataConfiguration(HttpMessageConverters httpMessageConverters){
        if(httpMessageConverters==null){
            return;
        }

        httpMessageConverters.forEach(httpMessageConverter -> {
            if(httpMessageConverter instanceof FastJsonHttpMessageConverter){
                ((FastJsonHttpMessageConverter) httpMessageConverter).getFastJsonConfig().setSerializeFilters(new UnGlobalImportantDataContextValueFilter());
            }
            if(httpMessageConverter instanceof MappingJackson2HttpMessageConverter){
                ObjectMapper objectMapper= ((MappingJackson2HttpMessageConverter) httpMessageConverter).getObjectMapper();
                if(objectMapper==null) {
                    objectMapper=new ObjectMapper();
                    String dataFormat="yyyy-MM-dd HH:mm:ss";

                    objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
                    // 日期类型字符串处理
                    objectMapper.setDateFormat(new SimpleDateFormat(dataFormat));
                    // java8日期日期处理
                    JavaTimeModule javaTimeModule = new JavaTimeModule();
                    javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dataFormat)));
                    javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(dataFormat)));
                    objectMapper.registerModule(javaTimeModule);
                }
                FilterProvider filters = new SimpleFilterProvider().setFailOnUnknownId(false).addFilter("disableGlobalImportantDataEncryptJacksonFilter", new ImportantDataEncryptJacksonFilter());
                objectMapper.setFilterProvider(filters);

                ((MappingJackson2HttpMessageConverter) httpMessageConverter).setObjectMapper(objectMapper);
            }
        });
    }




}
