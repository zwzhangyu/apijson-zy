# 基于ApiJson和传统的ORM框架构建了一个基础的增删改查项目

## ApiJson介绍

![概览](https://img-blog.csdnimg.cn/7740168320824c269326c67800a7340c.png)


 - [APIJSON自动化ORM库，后端接口和文档自动化实践整理 （一）](https://blog.csdn.net/Octopus21/article/details/126441890)
-  [APIAuto——敏捷开发最强大易用的 HTTP 接口工具 （二）](https://blog.csdn.net/Octopus21/article/details/126441982)
 - [UnitAuto——机器学习单元测试平台 （三）](https://blog.csdn.net/Octopus21/article/details/126442002)

## 项目介绍
为了更好的了解和分析ApiJson，这里基于ApiJson和传统的MyBatis(MyBatisPlus)框架构建了一个基础的增删改查项目，目前这俩种方式是合并在一起的
![概览](https://img-blog.csdnimg.cn/fdcee6188535425887eb8190d13e727b.png)
- 机器管理模块时使用传统的ORM框架开发的
- 消息管理模块是完全使用ApiJson进行的开发
- 使用的前后端分离的方式后端：SpringBoot+ApiJson+MySQL
前端：Vue+Axios+ElementUI


## 安装

### 前端server_frontend

```javascript
npm install
npm run dev 
```

### 后端ServerManager
- server_manager.sql为数据库脚本，将其导入对应的数据库中

![概览](https://img-blog.csdnimg.cn/06cd171daed84598ac1c82c9abcd9c6e.png)

- 右击选择libs文件夹，将其目录内的jar包加载到项目中，待编译后直接启动SpringBoot即可
- 代码风格很混乱，仅仅是为了测试ApiJson功能做的简单示例
