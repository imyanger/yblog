# yanger #
基于 SpringBoot 2.0.4.RELEASE + Mybatis + MySQL 的后端项目。

### 前言 ###
该项目是 [yblog](https://github.com/imyanger/yblog) 的后端部分，使用SpringBoot构建，数据库采用MySQL，引入 Mybatis 作为ORM框架，项目引入了 Lombok 插件，并使用 Swagger2API进行文档支持和接口展示。

### 支持功能 ###
- [x] 部分化restful接口
- [x] swagger2API文档支持
- [x] generator代码生成
- [x] paginator的分页查询
- [x] redis缓存机制
- [x] shiro权限

### 目录结构介绍 ###

	|-- src                                              	// 源码目录
		|-- main                         
			|-- java                            
				|-- com      
					|-- yanger       
						|-- blog           		 	 	 	// 项目后台主要业务代码目录
						|-- common          		 	 	// 公共类目录
						|-- generator         		 	 	// generator代码生成程序目录
						|-- mybatis     		 	 	 	// mybatis分页实现程序目录
						|-- shiro             		 	 	// shiro相关目录
						|-- ueditor         		 	 	// ueditor文件目录
						|-- YangerApplication.java 	 	 	// 项目加载入口类
			|-- resources                               	// 资源文件目录
				|-- mapper                               	// mybatis数据库映射文件
				|-- ueditor                              	// ueditor配置文件目录
				|-- application.yml                     	// 项目主配置文件
				|-- application-dev.yml                 	// 开发环境配置文件
				|-- ehcache.xml                          	// ehcache缓存配置文件
				|-- log4j.properties                     	// log4j日志配置
	|-- test                                             	// 单元测试目录
	|-- pom.xml                                          	// maven项目的pom文件

### 部署流程 ###

1. 下载项目

   ``` shell
   // 下载项目到本地
   git clone https://github.com/imyanger/yblog.git  
   ```

2. 执行数据库 sql脚本 (doc目录下)：
   * 建表脚本：yanger.sql
   * 支持数据：data.sql
   * 一些模拟数据：模拟数据.sql（非必须）

3. 修改数据源地址，修改 redis 连接地址

4. 导入项目到 IDE工具中，下载依赖，启动 YangerApplication 启动类
5. 该项目使用了 lombok，若未安装，需要先安装 lombok，否则代码会编译报错

后端默认地址 http://localhost:8888/yanger

### 使用组件说明 ###

##### swagger2API #####
swagger提供API接口调用页面，可方便接口查看和测试。swagger官网地址：[swagger](https://swagger.io/)

##### lombok #####
lombok插件可使用注解简化一些繁琐的java代码。lombok访问地址：[element](https://projectlombok.org/)

##### 参考博文

- [简化代码：lombok插件的使用](https://blog.csdn.net/Simple_Yangger/article/details/90343095)

- [文档支持，Spring Boot集成Swagger2](https://blog.csdn.net/Simple_Yangger/article/details/93544230)

