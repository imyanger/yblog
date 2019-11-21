
-- 操作日志
DROP TABLE IF EXISTS `operate_log`;
CREATE TABLE `operate_log`(  
  `log_id` INT NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `operator_id` INT COMMENT '操作人(用户)id',
  `operate_ip` VARCHAR(60) COMMENT '操作机器Ip',
  `operate_type` VARCHAR(10) COMMENT '操作类型',
  `operate_desc` VARCHAR(50) COMMENT '操作描述',
  `request_params` TEXT COMMENT '请求参数',
  `request_url` VARCHAR(100) COMMENT '请求地址',
  `status` CHAR(1) COMMENT '数据状态',
  `insert_time` DATETIME COMMENT '创建时间',
  `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`log_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


-- 文章
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`(  
  `article_id` INT NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `module` VARCHAR(50) COMMENT '模块',
  `type` VARCHAR(50) COMMENT '所属类型',
  `classify` VARCHAR(50) COMMENT '分类（具体）',
  `title` VARCHAR(100) COMMENT '标题',
  `author` VARCHAR(60) COMMENT '作者',
  `user_id` INT COMMENT '作者（用户）id',
  `rux_words` VARCHAR(100) COMMENT '关键字',
  `summary` VARCHAR(200) COMMENT '简介',
  `content` TEXT COMMENT '内容(html格式)',
  `word_content` TEXT COMMENT '文档内容(编辑器中的原格式)',
  `word_type` CHAR(1) COMMENT '文档编辑器类型',
  `art_state` CHAR(2) COMMENT '文章的状态',
  `art_img_path` VARCHAR(100) COMMENT '图片路径',
  `likes` INT COMMENT '喜欢数量',
  `views` INT COMMENT '浏览数量',
  `commons` INT COMMENT '评论数量',
  `status` CHAR(1) COMMENT '数据状态',
  `insert_time` DATETIME COMMENT '创建时间',
  `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`article_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


-- 博客用户
DROP TABLE IF EXISTS `blog_user`;
CREATE TABLE `blog_user`(  
  `user_id` INT NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_code` VARCHAR(20) COMMENT '用户账号',
  `password` VARCHAR(300) COMMENT '用户密码',
  `user_nick_name` VARCHAR(60) COMMENT '用户昵称',
  `gender` VARCHAR(10) COMMENT '性别',
  `user_img_path` VARCHAR(100) COMMENT '头像图片路径',
  `summary` VARCHAR(200) COMMENT '用户简介',
  `age` INT COMMENT '年龄',
  `phone_number` VARCHAR(100) COMMENT '电话',
  `moblie` VARCHAR(100) COMMENT '手机号',
  `email` VARCHAR(100) COMMENT '邮箱',
  `address` VARCHAR(200) COMMENT '地址',
  `graduate_school` VARCHAR(100) COMMENT '毕业院校',
  `education` VARCHAR(50) COMMENT '学历',
  `birtd` DATETIME COMMENT '出生日期',
  `user_real_name` VARCHAR(60) COMMENT '真实姓名',
  `user_type` CHAR(1) COMMENT '用户类型',
  `status` CHAR(1) COMMENT '数据状态',
  `insert_time` DATETIME COMMENT '创建时间',
  `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


-- 文章分类
DROP TABLE IF EXISTS `article_kind`;
CREATE TABLE `article_kind`(  
  `art_kinds_id` INT NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `module` VARCHAR(50) COMMENT '模块',
  `type` VARCHAR(50) COMMENT '所属类型',
  `classify` VARCHAR(50) COMMENT '分类（具体）',
  `sum` INT COMMENT '文章数量',
  `status` CHAR(1) COMMENT '数据状态',
  `insert_time` DATETIME COMMENT '创建时间',
  `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`art_kinds_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


-- 留言
DROP TABLE IF EXISTS `leaving_msg`;
CREATE TABLE `leaving_msg`(  
  `msg_id` INT NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `type` VARCHAR(20) COMMENT '所属类型',
  `user_id` INT COMMENT '留言用户id',
  `article_id` INT COMMENT '留言文章id',
  `content` TEXT COMMENT '留言内容',
  `upper_id` INT COMMENT '留言父级id',
  `msg_order` INT COMMENT '留言顺序（作为子级）',
  `status` CHAR(1) COMMENT '数据状态',
  `insert_time` DATETIME COMMENT '创建时间',
  `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`msg_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


-- 外部链接
DROP TABLE IF EXISTS `outer_link`;
CREATE TABLE `outer_link`(  
  `link_id` INT NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `type` VARCHAR(50) COMMENT '所属类型',
  `depict` VARCHAR(100) COMMENT '描述',
  `link` VARCHAR(100) COMMENT '链接',
  `sequence` INT COMMENT '顺序',
  `status` CHAR(1) COMMENT '数据状态',
  `insert_time` DATETIME COMMENT '创建时间',
  `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`link_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


-- 联系人
DROP TABLE IF EXISTS `contact`;
CREATE TABLE `contact`(  
  `contact_id` INT NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `type` VARCHAR(50) COMMENT '类型',
  `name` VARCHAR(50) COMMENT '组名',
  `depict` VARCHAR(200) COMMENT '描述',
  `words` VARCHAR(20) COMMENT '口令',
  `user_id` INT COMMENT '成员(用户)id',
  `img_path` VARCHAR(100) COMMENT '组描述图片',
  `status` CHAR(1) COMMENT '数据状态',
  `insert_time` DATETIME COMMENT '创建时间',
  `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`contact_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


-- 文件上传
DROP TABLE IF EXISTS `upload_file`;
CREATE TABLE `upload_file`(  
  `path_id` INT NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `type` VARCHAR(50) COMMENT '所属类型',
  `classify` VARCHAR(50) COMMENT '分类（具体）',
  `file_name` VARCHAR(60) COMMENT '图片名',
  `suffix` VARCHAR(10) COMMENT '图片后缀',
  `img_path` VARCHAR(100) COMMENT '图片路径',
  `sequence` INT COMMENT '顺序',
  `user_id` INT COMMENT '创建人（用户）id',
  `status` CHAR(1) COMMENT '数据状态',
  `insert_time` DATETIME COMMENT '创建时间',
  `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`path_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


-- 常量
DROP TABLE IF EXISTS `const`;
CREATE TABLE `const`(  
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `depict` VARCHAR(50) COMMENT '描述',
  `code` VARCHAR(10) COMMENT '常量代码',
  `val` VARCHAR(50) COMMENT '常量值',
  `upper_code` VARCHAR(10) COMMENT '上一级常量代码',
  `status` CHAR(1) COMMENT '状态',
  `insert_time` DATETIME COMMENT '插入时间',
  `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB CHARSET=utf8;


-- 待办
DROP TABLE IF EXISTS `todo`;
CREATE TABLE `todo`(  
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` CHAR(1) COMMENT '类型',
  `title` VARCHAR(20) COMMENT '标题',
  `depict` VARCHAR(200) COMMENT '描述',
  `status` CHAR(1) COMMENT '状态',
  `insert_time` DATETIME COMMENT '插入时间',
  `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB CHARSET=utf8;