# yanger-app #

基于Vue.js 2.x系列 + Element UI 的前端项目。

### 前言 ###
该项目是 [yblog](https://github.com/imyanger/yblog) 的前端部分，使用 Vue.js，引入 Element UI 样式组件库，方便开发快速简洁好看的组件。

### 支持功能 ###
- [x] Element UI
- [x] 登录/注销
- [x] 表格
- [x] Tab选项卡
- [x] 表单
- [x] 图表 :bar_chart:
- [x] 富文本编辑器
- [x] markdown编辑器
- [x] 图片拖拽/裁剪上传
- [x] 404 / 403


### 目录结构介绍 ###

	|-- build                            // webpack配置文件
	|-- config                           // 项目打包路径
	|-- src                              // 源码目录
	    |-- components                   // 组件
	        |-- back                     // 后台管理系统组件
	        |-- blog                     // 博客前台展示组件
	        |-- common                   // 公共组件
	    |-- pages                        // 主要路由页面
	        |-- back                     // 后台管理系统页面
	        |-- blog                     // 博客前台页面
	    |-- router                       // 路由目录
	    |-- store                        // vuex目录
	    |-- App.vue                      // 页面入口文件
	    |-- main.js                      // 程序入口文件，加载各种公共组件
	|-- static                           // 静态资源目录
	    |-- css                          // css样式目录
	    |-- img                          // 图片目录
	    |-- js           	             // 公共js目录
	    |-- ueditor                      // 富文本编辑器ueditor目录
	|-- .babelrc                         // ES6语法编译配置
	|-- .editorconfig                    // 代码编写规格
	|-- .gitignore                       // 忽略的文件
	|-- index.html                       // 入口html文件
	|-- package.json                     // 项目及工具的依赖配置文件
	|-- README.md                        // 说明

### 部署流程 ###

1. 克隆项目到本地

   ``` she
   // 克隆项目到本地
   git clone https://github.com/imyanger/yblog.git
   ```

2. 安装项目依赖

   ``` sh
   // 进入前端根目录
   cd yblog/yanger-app
   // 安装项目依赖，等待安装完成之后
   npm install
   ```

3. 本地开发启动

   ``` sh
   // 启动服务，浏览器访问 http://localhost:8080
   npm run dev
   ```

4. 生产构建

   ``` sh
   // 执行构建命令，生成的dist文件夹放在服务器下即可访问（生产可搭配 Nginx 使用）
   npm run build
   ```

### 组件使用说明与演示 ###

##### vue-schart #####
vue.js封装sChart.js的图表组件。访问地址：[vue-schart](https://www.npmjs.com/package/vue-schart)

##### element-ui #####
一套基于vue.js2.0的桌面组件库。访问地址：[element](http://element.eleme.io/#/zh-CN/component/layout)

##### Vue-Quill-Editor（IE10及以下不支持） #####
基于Quill、适用于Vue2的富文本编辑器。访问地址：[vue-quill-editor](https://github.com/surmon-china/vue-quill-editor)

##### mavonEditor #####
基于Vue的markdown编辑器。访问地址：[mavonEditor](https://github.com/hinesboy/mavonEditor)

##### vue-cropperjs #####
一个封装了 cropperjs 的 Vue 组件，用于裁剪图片。访问地址：[vue-cropperjs](https://github.com/Agontuk/vue-cropperjs)

##### ueditor #####
百度web前端研发部开发所见即所得富文本web编辑器，轻量，可定制。访问地址：[ueditor](https://ueditor.baidu.com/website/index.html)
