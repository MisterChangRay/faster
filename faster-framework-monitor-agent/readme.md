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
`java -Xms512M -Xmx512m -javaagent:./monitor.jar=./monitorj.properties -jar ./test.jar`


##### 2. 配置信息

可以配置推送信息, 支持钉钉推送。

配置文件位于项目根目录`monitorj.properties`

配置文件默认为项目目录下, 或者启动时携带配置文件地址. 

如: ` -javaagent:./monitor.jar=/var/data/monitor/monitorj.properties`

启动时将会使用`/var/data/monitor/monitorj.properties`此文件为配置文件

日志默认输出位置为项目目录。

示例配置：
```$xslt

# 应用名, 通知时使用
appName=user_provider_192.168.0.10_9127

# 默认监控包路径
monitorPackage=com.test.package3
# 可以添加多个
monitorPackage[0]=com.test.package1
monitorPackage[1]=com.test.package2
maxTTLOfSec=2

# 指定 com.mister.register 方法 超时阈值为 2S
method[0].name=com.mister.register
method[0].ttl=2
# 可以添加多个方法
method[1].name=com.mister.register2
method[1].ttl=3

# 钉钉通知URL地址
notifyUrlOfDingDing=https://oapi.dingtalk.com/robot/send?access_token=72f1abba57510f0538832948844758c8ba4a58e68fc6b28bb26d972dd0ae1b2
# 钉钉通知密钥
notifySecretOfDingDing=SEC028a906e7ca41b79f2b91503793025c7b53aaf0ad3fbef0974bc8fc0d4b16a3


# 日志路径, 默认项目目录下
logPath=
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

