# yblog  我的个人博客项目 # 
基于 SpringBoot 2.0.4.RELEASE + Mybatis + MySQL + Vue + Element 搭建的个人博客。

博客访问地址：[杨号个人博客：http://imyanger.com](http://imyanger.com)。

### 前言 ###
该项目采用前后端分离，后端主要采用SpringBoot + Mybatis + MySQL ，前端基于Vue  + Element 。

项目在功能上分为两部分：

* 博客门户，即浏览博文的前台展示页面。
* 博客管理平台，后台管理博文的平台。

### 后端主要依赖 ###
- [x] SpringBoot 
- [x] Mybatis 
- [x] MySQL 数据库
- [x] Redis缓存
- [x] poi插件
- [x] thumbnailator图片插件
- [x] Lombok插件
- [x] Swagger2API文档支持
- [x] Shiro权限

### 前端主要依赖 ###
- [x] Element UI
- [x] Vue.js
- [x] ueditor富文本
- [x] vue-schart 图标
- [x] mavonEditor 一款Markdown编辑器
- [x] vue-cropperjs 图片裁剪

### 目录结构介绍 ###

	|-- doc                              // 项目文档目录
	|-- yanger                           // 后端源码目录
	|-- yanger-app                       // 前端源码目录

### 项目部署 ###

[后端部署详细说明](https://github.com/imyanger/yblog/blob/master/yanger/README.md)

[前端部署详细说明](https://github.com/imyanger/yblog/blob/master/yanger-app/README.md)

* 项目前端访问默认端口为：8080
* 项目在功能上分为博客门户和博客管理平台，且两者用户分开使用
  * 门默认用户：
    * 账号：imyanger
    * 密码：imyanger
  * 博客管理平台默认用户：
    * 账号：admin123
    * 密码：admin123

#### [项目部分截图查看](https://github.com/imyanger/yblog/blob/master/doc/img/README.md)