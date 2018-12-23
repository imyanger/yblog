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

	|-- src                                             // 源码目录
	    |-- main                         
            |-- java                            
                |-- com           	          
                    |-- yanger       
                        |-- blog                        // 项目后台主要业务代码目录
                        |-- common                      // 公共类目录
                        |-- generator                   // generator代码生成程序目录
                        |-- mybatis                     // mybatis分页实现程序目录
                        |-- shiro                       // shiro相关目录
                        |-- ueditor                     // ueditor文件目录
                        |-- YangerApplication.java      // 项目加载入口类
	        |-- resources                               // 资源文件目录
	            |-- mapper                              // mybatis数据库映射文件
                |-- ueditor                             // ueditor配置文件目录
                |-- application.yml                     // 项目主配置文件
                |-- application-dev.yml                 // 开发环境配置文件
                |-- ehcache.xml                         // ehcache缓存配置文件
                |-- log4j.properties                    // log4j日志配置
        |-- test                   	                    // 单元测试目录
    |-- static                                          // 静态资源目录
	    |-- css                                         // css样式目录
	    |-- img                                         // 图片目录
        |-- js           	             // 公共js目录
        |-- ueditor                      // 富文本编辑器ueditor目录
	|-- pom.xml                          // maven项目的pom文件

## 安装步骤 ##

	git clone https://github.com/imyanger/yblog.git     // 把模板下载到本地
	cd yblog/yanger-app     // 进入模板目录
	npm install     // 安装项目依赖，等待安装完成之后

## 本地开发 ##

	// 开启服务器，浏览器访问 http://localhost:8080
	npm run dev

## 构建生产 ##

	// 执行构建命令，生成的dist文件夹放在服务器下即可访问
	npm run build

## 组件使用说明与演示 ##

### vue-schart ###
vue.js封装sChart.js的图表组件。访问地址：[vue-schart](https://www.npmjs.com/package/vue-schart)

### element-ui ###
一套基于vue.js2.0的桌面组件库。访问地址：[element](http://element.eleme.io/#/zh-CN/component/layout)

### Vue-Quill-Editor（IE10及以下不支持） ###
基于Quill、适用于Vue2的富文本编辑器。访问地址：[vue-quill-editor](https://github.com/surmon-china/vue-quill-editor)

### mavonEditor ###
基于Vue的markdown编辑器。访问地址：[mavonEditor](https://github.com/hinesboy/mavonEditor)

### vue-cropperjs ###
一个封装了 cropperjs 的 Vue 组件，用于裁剪图片。访问地址：[vue-cropperjs](https://github.com/Agontuk/vue-cropperjs)

### ueditor ###
百度web前端研发部开发所见即所得富文本web编辑器，轻量，可定制。访问地址：[ueditor](https://ueditor.baidu.com/website/index.html)
