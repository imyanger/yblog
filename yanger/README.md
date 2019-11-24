# yanger #
基于springboot 2.0.4.RELEASE + mybatis + mysql的后端项目。

## 前言 ##
该项目基于springboot,使用maven打包，数据库选择mysql，引入mybatis作为ORM框架，项目引入了lombok插件，并使用swagger2API进行文档支持和接口展示。

## 功能 ##
- [x] 部分化restful接口
- [x] swagger2API文档支持
- [x] generator代码生成
- [x] paginator的分页查询
- [x] redis缓存机制
- [x] shiro权限

## 目录结构介绍 ##
   
	|-- src                                              			// 源码目录
		|-- main                         
			|-- java                            
				|-- com      
					|-- yanger       
						|-- blog           		// 项目后台主要业务代码目录
						|-- common          		// 公共类目录
						|-- generator         		// generator代码生成程序目录
						|-- mybatis     		// mybatis分页实现程序目录
						|-- shiro             		// shiro相关目录
						|-- ueditor         		// ueditor文件目录
						|-- YangerApplication.java 	// 项目加载入口类
			|-- resources                               		// 资源文件目录
				|-- mapper                               	// mybatis数据库映射文件
				|-- ueditor                              	// ueditor配置文件目录
				|-- application.yml                     	// 项目主配置文件
				|-- application-dev.yml                 	// 开发环境配置文件
				|-- ehcache.xml                          	// ehcache缓存配置文件
				|-- log4j.properties                     	// log4j日志配置
	|-- test                                             		 	// 单元测试目录
	|-- pom.xml                                          			// maven项目的pom文件

## 安装步骤 ##
	
	// 下载项目到本地
	git clone https://github.com/imyanger/yblog.git    
	// 进入项目后端目录
	cd yblog/yanger     
	// 执行数据库sql(doc目录下)，修改数据源，redis连接地址
	// 执行YangerApplication启动类，后端默认地址http://localhost:8888/yanger

## 使用组件说明 ##

### swagger2API ###
swagger提供API接口调用页面，可方便接口查看和测试。swagger官网地址：[swagger](https://swagger.io/)

### lombok ###
lombok插件可使用注解简化一些繁琐的java代码。lombok访问地址：[element](https://projectlombok.org/)
