DROP TABLE IF EXISTS `operate_log`;
CREATE TABLE `operate_log`(  
  `log_id` INT NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `operate_type` VARCHAR(10) COMMENT '操作类型',
  `operate_desc` VARCHAR(50) COMMENT '操作描述',
  `operator_id` INT COMMENT '操作人id',
  `operator_name` VARCHAR(60) COMMENT '操作人名称',
  `update_params` VARCHAR(1000) COMMENT '更新参数',
  `request_url` VARCHAR(100) COMMENT '请求地址',
  `table_name` VARCHAR(100) COMMENT '表名',
  `status` CHAR(1) COMMENT '数据状态',
  `insert_time` DATETIME COMMENT '创建时间',
  `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`log_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`(  
  `article_id` INT NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `module` VARCHAR(50) COMMENT '模块',
  `type` VARCHAR(50) COMMENT '所属类型',
  `classify` VARCHAR(50) COMMENT '分类（具体）',
  `title` VARCHAR(100) COMMENT '标题',
  `author` VARCHAR(60) COMMENT '作者',
  `rux_words` VARCHAR(100) COMMENT '关键字',
  `summary` VARCHAR(200) COMMENT '简介',
  `content` TEXT COMMENT '内容',
  `art_img_path` VARCHAR(100) COMMENT '图片路径',
  `likes` INT COMMENT '喜欢数量',
  `views` INT COMMENT '浏览数量',
  `commons` INT COMMENT '评论数量',
  `user_id` INT COMMENT '用户id',
  `status` CHAR(1) COMMENT '数据状态',
  `insert_time` DATETIME COMMENT '创建时间',
  `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`article_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `blog_user`;
CREATE TABLE `user`(  
  `user_id` INT NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_code` VARCHAR(20) COMMENT '用户账号',
  `password` VARCHAR(20) COMMENT '用户密码',
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
  `status` CHAR(1) COMMENT '数据状态',
  `insert_time` DATETIME COMMENT '创建时间',
  `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `article_kinds`;
CREATE TABLE `article_kinds`(  
  `art_kinds_id` INT NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `type` VARCHAR(50) COMMENT '所属类型',
  `classify` VARCHAR(50) COMMENT '分类（具体）',
  `sum` INT COMMENT '文章数量',
  `status` CHAR(1) COMMENT '数据状态',
  `insert_time` DATETIME COMMENT '创建时间',
  `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`art_kinds_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `leaving_msg`;
CREATE TABLE `leaving_msg`(  
  `msg_id` INT NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `type` VARCHAR(20) COMMENT '所属类型',
  `user_id` INT COMMENT '留言用户id',
  `user_nick_name` VARCHAR(60) COMMENT '用户昵称',
  `user_img_path` VARCHAR(100) COMMENT '头像图片路径',
  `article_id` INT COMMENT '留言文章id',
  `art_img_path` VARCHAR(100) COMMENT '留言文章图片路径',
  `article_title` VARCHAR(100) COMMENT '留言文章标题',
  `content` TEXT COMMENT '留言内容',
  `upper_id` INT COMMENT '留言父级id',
  `msg_order` INT COMMENT '留言顺序（作为子级）',
  `status` CHAR(1) COMMENT '数据状态',
  `insert_time` DATETIME COMMENT '创建时间',
  `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`msg_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `outer_link`;
CREATE TABLE `outer_link`(  
  `link_id` INT NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `type` VARCHAR(50) COMMENT '所属类型',
  `describe` VARCHAR(100) COMMENT '描述',
  `link` VARCHAR(100) COMMENT '链接',
  `order` INT COMMENT '顺序',
  `status` CHAR(1) COMMENT '数据状态',
  `insert_time` DATETIME COMMENT '创建时间',
  `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`link_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `contacts`;
CREATE TABLE `contacts`(  
  `contact_id` INT NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `type` VARCHAR(50) COMMENT '类型',
  `name` VARCHAR(50) COMMENT '组名',
  `describe` VARCHAR(200) COMMENT '描述',
  `words` VARCHAR(20) COMMENT '口令',
  `user_id` INT COMMENT '成员id',
  `user_nick_name` VARCHAR(60) COMMENT '成员昵称',
  `user_real_name` VARCHAR(60) COMMENT '真实姓名',
  `address` VARCHAR(100) COMMENT '地址',
  `moblie` VARCHAR(100) COMMENT '联系方式',
  `user_img_path` VARCHAR(100) COMMENT '用户头像路径',
  `img_path` VARCHAR(100) COMMENT '组描述图片',
  `status` CHAR(1) COMMENT '数据状态',
  `insert_time` DATETIME COMMENT '创建时间',
  `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`contact_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `upload_file`;
CREATE TABLE `upload_file`(  
  `path_id` INT NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `type` VARCHAR(50) COMMENT '所属类型',
  `classify` VARCHAR(50) COMMENT '分类（具体）',
  `file_name` VARCHAR(60) COMMENT '图片名',
  `suffix` VARCHAR(10) COMMENT '图片后缀',
  `img_path` VARCHAR(100) COMMENT '图片路径',
  `order` INT COMMENT '顺序',
  `user_id` INT COMMENT '创建人id',
  `user_nick_name` VARCHAR(60) COMMENT '用户昵称',
  `user_img_path` VARCHAR(100) COMMENT '用户头像图片路径',
  `status` CHAR(1) COMMENT '数据状态',
  `insert_time` DATETIME COMMENT '创建时间',
  `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`path_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;
