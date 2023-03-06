# 股票型基金理财系统的服务器端工程
## 简介
本工程是针对股票型基金理财系统做的服务器建设。

## java模块介绍

### server-adapters
使用本服务器系统的适配器，用于客户端调用、前端rest调用等


### server-common
通用的模块，放置一些和业务逻辑无关的基础功能，以后可能会移入其他工具库中


### application
领域设计中的应用层。业务功能组装器


### infrastructures
领域设计中的基础设施层parent


#### infrastructure-core
核心的或者理解为通用的基础的基础设施层实现。其中包含：
- DAO的实现。主要是关系数据库（事务）的DAO实现。


#### infrastructure-query
查询服务的实现，在基础设施层完成。



