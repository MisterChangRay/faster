### faster 
微服务快速开发组件
提供常见的业务开发模块封装，所有的业务模块已经经历过线上检测，更简单快速。

我们的目的是抽出常见的项目中使用的第三方模块。方便复制开发。

项目约定:
- 项目必须使用 spring-boot-2.5.5 版本

提供以下常见的模块：
- 公共模块

    包含项目所有的常量定义，实体类定义，SQL定义。项目统一引入避免版本混乱方便快速开发
    
- 基本模块

    包含常用工具包， 线程池定义等。
    
- 用户中心

    单点用户必备
    
- 财务模块

    用户财务模块, 包含个人钱包, 账户实时变动, 账户日/周/月/年统计等功能。
    主要维护账户的余额变动。设计简单/高效。方便二次集成开发。
    
- 网关

    web服务限流, 表单数据重编码
    
- 短信服务

    集成阿里云统一封装短信服务
    
- 管理后台模板

    管理后台必备, 直接进入开发, 方便快捷
    