package com.dewfn.busstation.importantdata.filter;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 本地存储的 实现，  可以在非集群机器时使用
 */
public class DecryptAccessStoreLocal extends DecryptAccessAdapter{
    private  final Map<String,Long> userUsedAccessCountStore=new ConcurrentHashMap();
    private ScheduledExecutorService scheduledExecutorService=
            Executors.newScheduledThreadPool(1,new CustomizableThreadFactory("DecryptAccessToLocalClear-"));
    @Override
    public Long getCurrentUserUsedCount(String usedCountKey) {
        return userUsedAccessCountStore.getOrDefault(usedCountKey,0l);
    }

    @Override
    public void setCurrentUserUsedCount(String usedCountKey, long userUsedCount, long expireSecond) {
        //如果第一次设置，也就是userUsedCount=1时，则设置过期时间，  其余不设置过期时间
        userUsedAccessCountStore.put(usedCountKey,userUsedCount);
        if(userUsedCount==1){
            scheduledExecutorService.schedule(()->{
                userUsedAccessCountStore.remove(usedCountKey);
            },expireSecond, TimeUnit.SECONDS);
        }

    }
}
