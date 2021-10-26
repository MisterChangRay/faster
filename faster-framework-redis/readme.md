# Redisson 框架整合包

[更多使用示例参考: Redisson 使用手册](https://www.bookstack.cn/read/redisson-wiki-zh/spilt.1.6.-%E5%88%86%E5%B8%83%E5%BC%8F%E5%AF%B9%E8%B1%A1.md)

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
```java

    @Autowired(required = false)
    RedissonClient redissonClient;

    @Override
    public int save(FinancialAccount financialAccount) {
        // 构建Key对象
        RBucket<Object> bucket = redissonClient.getBucket("com.github.misterchangray.faster.user");
        // 尝试设置, 未设置则设置成功
        boolean b = bucket.trySet("{}", 22, TimeUnit.SECONDS);
        // 获取后删除
        Object andDelete = bucket.getAndDelete();  
        // 获取值
        Object o = bucket.get();

    }

```

