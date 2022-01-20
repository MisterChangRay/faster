# jvm应用监控

使用java-agent 实现的jvm监控。 主要监控了一下指标

- 应用CPU使用率
- 应用GC情况
- 应用GC次数
- 应用方法耗时
- 应用死锁检测
- 应用非堆溢出检测
- 应用OOM溢出检测（只能检测一小部分）
- 应用线程数量检测


##### 1. 使用
在java启动时加以下参数:
`java -Xms512M -Xmx512m -javaagent:./monitor.jar -jar ./test.jar`


##### 2. 配置信息

可以配置推送信息, 支持钉钉推送。
配置文件位于项目根目录`monitor.properties`
示例配置：
```$xslt
monitorPackage=/com/test/target
maxTTLOfSec=2
```


支持的配置属性
```$xslt
    /**
     * 监控包路径
     */
    String monitorPackage;

    /**
     * 钉钉HTTP通知地址
     */
    String notifyUrlOfDingDing;

    String notifySecretOfDingDing;

    /**
     * 方法超时时间
     */
    int maxTTLOfSec;

    /**
     * 日志记录路径
     */
    String logPath;


    /**
     * 是否记录CPU使用率
     *
     */
    boolean recordCpuUsage;

    /**
     * 进程使用最大CPU通知阈值
     * 进程使用CPU超过此值后发送通知
     * 默认为90
     */
    int maxCpuUsedOfProcess;

    /**
     * 打印GC情况
     * 每 5/s 打印一次GC情况
     */
    boolean recordGC;

    /**
     * 一个进程最大线程数
     * 当前进程线程数超过此值进行通知
     */
    int maxThreadOfProcess;


    /**
     * 最大异常
     * 超过此异常阈值发送通知
     */
    int maxExceptions;


    /**
     * 是否记录内存使用情况
     */
    boolean recordMemUsed;

    /**
     * 内存使用超过此阈值后发送通知
     *
     * 默认为 500MB
     */
    long maxMemUseKb;


    /**
     * 应用名称
     */
    String appName;
```
- 

