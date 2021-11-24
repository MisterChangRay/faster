# log4j2 框架引用

系统日志使用lo4j2， 引入此框架包统一解决日志问题


##### 1. 引入maven
```xml

<dependency>
    <groupId>com.github.misterchangray</groupId>
    <artifactId>faster-framework-log4j2</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>

```
##### 2. 配置`log4j2-spring.xml` 可以在框架包 resource 目录下拷贝源文件并进行修改
```xml

<?xml version="1.0" encoding="UTF-8"?>
<Configuration>
    <Properties>
        <Property name="datePattern">
            <!-- 日志格式化规则：[日志级别][线程名称-线程ID][日志从JVM生成时间][日志记录时间][日志完全类名][日志具体行] -->
            <!--            [%d{yyyy-MM-dd HH:mm:ss,SSS}] [%p] [%t-%T] [%r] [%c{10}] [%l] %m%n-->
            [%d{yyyy-MM-dd HH:mm:ss,SSS}] [%p] [%t-%T] [%r] [%c{1.}.%M:%L] [%X{tradeId}] [%X{code}] %m%n
        </Property>
        <!-- 日志的存放根路径 -->
        <Property name="logBasePath">logs</Property>
        <Property name="debugLogFilePath">${logBasePath}/debug/app.log</Property>
        <Property name="infoLogFilePath">${logBasePath}/info/app.log</Property>
        <Property name="errorLogFilePath">${logBasePath}/error/app.log</Property>
        <!-- 单个日志文件大小 -->
        <Property name="logFileSize">60M</Property>
    </Properties>
    <Appenders>
        <Console name="Console" target="STDOUT">
            <PatternLayout pattern="${datePattern}" />
        </Console>
        <RollingFile name="debugAppender"
                     fileName="${debugLogFilePath}"
                     filePattern="${logBasePath}/debug/debug-%d{yyyy-MM-dd}-%i.log.gz">
            <Filters>
                <ThresholdFilter level="INFO" onMatch="DENY" onMismatch="NEUTRAL"/>
                <ThresholdFilter level="DEBUG" onMatch="ACCEPT" onMismatch="DENY"/>
            </Filters>
            <PatternLayout>
                <Pattern>${datePattern}</Pattern>
            </PatternLayout>
            <Policies>
                <TimeBasedTriggeringPolicy />
                <SizeBasedTriggeringPolicy	size="${logFileSize}" />
            </Policies>
            <DefaultRolloverStrategy fileIndex="nomax" max="5000"/>
        </RollingFile>
        <RollingFile name="infoAppender"
                     fileName="${infoLogFilePath}"
                     filePattern="${logBasePath}/info/info-%d{yyyy-MM-dd}-%i.log.gz">
            <Filters>
                <!--			    <ThresholdFilter level="ERROR" onMatch="DENY" onMismatch="NEUTRAL"/>-->
                <ThresholdFilter level="INFO" onMatch="ACCEPT" onMismatch="DENY"/>
            </Filters>
            <PatternLayout>
                <Pattern>${datePattern}</Pattern>
            </PatternLayout>
            <Policies>
                <TimeBasedTriggeringPolicy />
                <SizeBasedTriggeringPolicy	size="${logFileSize}" />
            </Policies>
            <DefaultRolloverStrategy fileIndex="nomax" max="5000"/>
        </RollingFile>
        <RollingFile name="errorAppender"
                     fileName="${errorLogFilePath}"
                     filePattern="${logBasePath}/error/error-%d{yyyy-MM-dd}-%i.log.gz">
            <Filters>
                <ThresholdFilter level="ERROR" onMatch="ACCEPT" onMismatch="DENY"/>
            </Filters>
            <PatternLayout>
                <Pattern>${datePattern}</Pattern>
            </PatternLayout>
            <Policies>
                <TimeBasedTriggeringPolicy />
                <SizeBasedTriggeringPolicy	size="${logFileSize}" />
            </Policies>
            <DefaultRolloverStrategy fileIndex="nomax" max="5000"/>
        </RollingFile>
    </Appenders>
    <Loggers>
        <Logger name="com.xd" additivity="false">
            <AppenderRef ref="Console"/>
            <AppenderRef ref="debugAppender" level="debug" />
            <AppenderRef ref="infoAppender" level="info" />
            <AppenderRef ref="errorAppender" level="error" />
        </Logger>
        <Logger name="org" level="info"/>
        <Logger name="com.xxl.job"  level="off" />
        <Root level="debug" additivity="false">
            <AppenderRef ref="Console"/>
            <AppenderRef ref="debugAppender" level="debug" />
            <AppenderRef ref="infoAppender" level="info" />
            <AppenderRef ref="errorAppender" level="error" />
        </Root>
    </Loggers>
</Configuration>

```

##### 3. 可以在项目中注入使用了, 注意统一使用slf4j接口进行日志输出
```java
     import org.slf4j.Logger;
     import org.slf4j.LoggerFactory;

     private static final Logger logger = LoggerFactory.getLogger(Test.class);

    @Override
    public int save(FinancialAccount financialAccount) {
         logger.error("日志输出..."）;
    }

```


##### 4. 注意事项
使用时注意排除项目中其他的日志框架，比如logback, 如使用spring-web应予以排除
```xml

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <!-- 去掉springboot默认日志配置 -->
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-logging</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

    
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-log4j2</artifactId>
        </dependency>
```