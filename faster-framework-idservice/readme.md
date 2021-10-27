# idesrvice 全局ID生成器

使用雪花算法, 请保证全局 appId 和 instanceId 全局唯一

##### 1. 引入maven
```xml

<dependency>
    <groupId>com.github.misterchangray</groupId>
    <artifactId>faster-framework-idservice</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>

```
##### 2. 配置应用参数
```xml

com:
  github:
    misterchangray:
      idservice:
        appId: 1
        appInstanceId: 2

```

##### 3. 可以在项目中注入使用`IDService`来进行开发了
```java

    @Autowired(required = false)
    IdService id;

  public static void main(String[] args) {
        id.setAppId(321);
        id.setAppInstanceId(52);

        System.out.println(id.parseDate(id.nextId()));
        System.out.println(id.parseAppId(id.nextId()));
        System.out.println(id.parseAppInstanceId(id.nextId()));

    }



```

