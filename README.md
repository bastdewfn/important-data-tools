脱敏，加密，记录日志，限制次数，框架会统一处理

1 引入jar包
<dependency><groupId>com.dewfn.busstation</groupId><artifactId>important-data-tools</artifactId><version>1.3.0</version>
</dependency>
或非快照版本 
 <version>1.3.0</version>

如遇到问题，可尝试先升级最新版，


2 在项目启动类加上注解 @EnableAutoMvcImportantData
configCentenrAppUk为你配置中心的项目标识，不指定默认为当前项目




3 在需要加密字段上加上注解 @ImportantDataField(value = ImportantDataFieldType.MobilePhone , tag = "订单详情手机号查看")
ImportantDataFieldType类型根据情况，有手机号，姓名，地址等，
tag = "订单详情手机号查看" 根据需求，最后记录日志时的存档用



4    创建一个新controller  继承 BaseImportantDataController类，  或实现自己的controller，功能包括解密 记录查看次数  记录日志

getUserInfo方法为获取当前用户信息，可返回工号和姓名
convertResult方法为转换加密后信息为你项目自定义的 Result类

可选 override  方法errorHandler  ,对异常类型转为 你项目自定义的Result类


5.   实现继承类  DecryptAccessAdapter ，注意类上的注解@Component不可少

必实现方法 getCurrentUserUsedCount 从redis等组件获取当前用户已访问次数
必实现方法 setCurrentUserUsedCount 从redis等组件设置用户已访问次数
可选，@Override public void log(DecryptAccessToken decryptAccessToken)  ，以自己的格式记录访问日志



6 springmvc项目  xml配置， 在spring相关xml里加入
<bean id="importantDataConfiguration" class="com.dewfn.busstation.importantdata.ImportantDataConfiguration">        <constructor-arg ref="httpMessageConverters" />    </bean>    <bean id="httpMessageConverters" class="org.springframework.boot.autoconfigure.http.HttpMessageConverters">        <constructor-arg>            <list>                <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">                    <property name="objectMapper">                        <bean class="com.fasterxml.jackson.databind.ObjectMapper">                        </bean>                    </property>                </bean>            </list>        </constructor-arg>    </bean>

7  其它非springBoot项目时，其它非springBoot项目时，其它非springBoot项目时

初始化  ImportantDataConvertUtil.setKey("你的加密key")
初始化 ImportantDataConfigCenter.init("你从配置中拉取时的项目appUk")

使用ImportantDataConvertUtil 自己调用加密， 解密方法
加密
ImportantDataConvertUtil.encrypt(String data, ImportantDataFieldType dataType, String tag)
解密
IDecryptAccess decryptAccess=new MyDecryptAccessAdapter(); //MyDecryptAccessAdapter 需自己实现，继承 DecryptAccessAdapter
DecryptAccessToken decryptAccessToken = decryptAccess.beforeAccess(importantDataStore, getUserIdentify());String data = ImportantDataConvertUtil.decrypt(importantDataStore);decryptAccess.afterAccess(decryptAccessToken);

8.其它情况
在编辑页面时，可能不需要加密掩码，  但实体和查看或列表通用时     加@DisableImportantDataAutoEncrypt  在controller方法中，可禁用加密掩码

如果需要编辑时判断解密次数需实现以下代码
IDecryptAccess 为自动注入

ImportantDataStore importantDataStore=new ImportantDataStore();importantDataStore.setTag("线路编辑查看信息");decryptAccess.afterAccess(decryptAccess.beforeAccess(importantDataStore,"用户标识"));


最新支持 编辑等页面时自动判断访问次数，并计算次数 @DisableImportantDataAutoEncrypt(automaticAccessCount = true,accessCount = 1,accessTag = "编辑订单页面")

automaticAccessCount 为true时自动记数，accessCount 为访问一次页面算多少次访问次数， accessTag 为记录日志时标签

如想自动计数，DecryptAccessAdapter 需实现
Override
public String getUserIdentify(){return "当前用户标识";}


9.  配置
IMPORTANTDATA_DECRYPT_LIMIT_NUM = 1000 , 用户当天限制最大解密次数

IMPORTANTDATA_DECRYPT_LIMIT_TIMEOUT = 1440 ,用户N分钟内限会算制次数 ，不管怎么设置 每天0点开始重新计次数 (非必要)

如果配置，记得 公开 勾选


接入统一配置：点击  客户端介绍


10.    注入ImportantDataEncryptKey bean，为了你的项目安全，不要使用默认key 
@Beanpublic ImportantDataEncryptKey importantDataEncryptKey(){return new ImportantDataEncryptKey("e22f7bab55e72b710acd0f74a3c2239d");}


11. 新增模式
@EnableAutoMvcImportantData 增加属性 unGlobal=false
当为true时非全局启用自动注解, 需要每个controller方法上再加@EnableMethodImportantDataAutoEncrypt才会加密脱敏 ， 默认的为false,只要字段加上@ImportantDataField 就会脱敏加密
