## 基于Java+Springboot+vue的药店管理系统(源码+数据库)103

## 一、系统介绍
本系统前后端分离

-功能:
登录、药库药品管理、统计查询、药房管理、物资管理、挂号管理、账号管理、角色管理、权限管理、登录日志管理、药品管理、药品类型管理、客人类型管理


## 二、所用技术
后端技术栈：
- Springboot
- SpringMvc
- mybatisPlus
- mysql
- SpringSecurity

前端技术栈：
- Vue
- ElementUI
- vue-router
- axios

## 三、环境介绍
基础环境 :IDEA/eclipse, JDK 1.8, Mysql5.7及以上, Maven3.6, node.js(14版本)

所有项目以及源代码本人均调试运行无问题 可支持远程调试运行

## 四、页面截图
### 1、功能页面
![contents](./picture/picture1.png)
![contents](./picture/picture2.png)
![contents](./picture/picture3.png)
![contents](./picture/picture4.png)
![contents](./picture/picture5.png)
![contents](./picture/picture6.png)
![contents](./picture/picture7.png)
![contents](./picture/picture8.png)
![contents](./picture/picture9.png)
![contents](./picture/picture10.png)
![contents](./picture/picture11.png)
![contents](./picture/picture12.png)
![contents](./picture/picture13.png)
![contents](./picture/picture14.png)
![contents](./picture/picture15.png)
![contents](./picture/picture16.png)
![contents](./picture/picture17.png)
![contents](./picture/picture18.png)
![contents](./picture/picture19.png)
![contents](./picture/picture20.png)
![contents](./picture/picture21.png)
![contents](./picture/picture22.png)
![contents](./picture/picture23.png)

## 五、浏览地址
- 前台访问路径：http://localhost:8889
  账号密码：admin/123456

## 六、安装教程

1. 使用Navicat或者其它工具，在mysql中创建对应名称的数据库，并执行项目的sql

2. 使用IDEA/Eclipse导入drug项目，导入时，若为maven项目请选择maven; 等待依赖下载完成

3. 修改resources目录下面application.properties里面的数据库配置和文件路径配置

4. com/drug/Application.java启动后端项目

5. vscode或idea打开drug-vue项目

6. 在编译器中打开terminal，执行npm install 依赖下载完成后执行 npm run serve,执行成功后会显示前台访问地址



