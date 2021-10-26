# Redisson 框架整合包


##### 1. 引入maven
```xml

<dependency>
    <groupId>com.github.misterchangray</groupId>
    <artifactId>faster-framework-redis</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>

```
##### 2. 配置连接信息
```xml
com:
  github:
    misterchangray:
      redis:
        single:
          host: 127.0.0.1
          port: 1234
          password: 123456
          db: 3

```

##### 3. 可以在项目中注入使用`RedissonClient`来进行开发了