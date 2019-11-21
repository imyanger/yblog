-- 文章模块的常量（学习笔记、心情随笔）必须插入
insert  into `const`(`id`,`depict`,`code`,`val`,`upper_code`,`status`,`insert_time`,`update_time`) values (1,'学习笔记模块','wzlx01','学习笔记','wzlx','1','2019-11-18 19:08:53','2019-11-18 19:08:58');
insert  into `const`(`id`,`depict`,`code`,`val`,`upper_code`,`status`,`insert_time`,`update_time`) values (2,'心情随笔模块','wzlx02','心情随笔','wzlx','1','2019-11-18 19:08:55','2019-11-18 19:09:00');

-- 初始化用户
insert  into `blog_user`(`user_id`,`user_code`,`password`,`user_nick_name`,`gender`,`user_img_path`,`summary`,`age`,`phone_number`,`moblie`,`email`,`address`,`graduate_school`,`education`,`birtd`,`user_real_name`,`user_type`,`status`,`insert_time`,`update_time`) values (1,'imyanger','D91B7AB25BB94AB4335D978031AF34D6','imyanger',NULL,NULL,NULL,NULL,NULL,'18888888888','imyanger@qq.com',NULL,NULL,NULL,NULL,NULL,'1',NULL,NULL,'2019-11-21 22:54:15');
insert  into `blog_user`(`user_id`,`user_code`,`password`,`user_nick_name`,`gender`,`user_img_path`,`summary`,`age`,`phone_number`,`moblie`,`email`,`address`,`graduate_school`,`education`,`birtd`,`user_real_name`,`user_type`,`status`,`insert_time`,`update_time`) values (2,'admin123','0192023A7BBD73250516F069DF18B500','超级管理员',NULL,NULL,NULL,NULL,NULL,'17777777777','admin123@qq.com',NULL,NULL,NULL,NULL,NULL,'2',NULL,NULL,'2019-11-21 22:55:53');

-- 一些其他自定义常量
insert  into `const`(`id`,`depict`,`code`,`val`,`upper_code`,`status`,`insert_time`,`update_time`) values (3,'关于java的文章','wzlx0101','java','wzlx01','1',NULL,'2019-11-21 22:58:15');
insert  into `const`(`id`,`depict`,`code`,`val`,`upper_code`,`status`,`insert_time`,`update_time`) values (4,'关于vue的笔记','wzlx0102','vue','wzlx01','1',NULL,'2019-11-21 22:58:37');
insert  into `const`(`id`,`depict`,`code`,`val`,`upper_code`,`status`,`insert_time`,`update_time`) values (5,'关于数据库的文章','wzlx0103','数据库','wzlx01','1',NULL,'2019-11-21 22:59:03');
insert  into `const`(`id`,`depict`,`code`,`val`,`upper_code`,`status`,`insert_time`,`update_time`) values (6,'随便记录一下生活','wzlx0201','生活笔记','wzlx02','1',NULL,'2019-11-21 22:59:49');
insert  into `const`(`id`,`depict`,`code`,`val`,`upper_code`,`status`,`insert_time`,`update_time`) values (7,'到处走走停停','wzlx0202','生活游记','wzlx02','1',NULL,'2019-11-21 23:00:25');
insert  into `const`(`id`,`depict`,`code`,`val`,`upper_code`,`status`,`insert_time`,`update_time`) values (9,'关于SpringBoot的文章','wzlx010101','SpringBoot','wzlx0101','1',NULL,'2019-11-21 23:01:59');
insert  into `const`(`id`,`depict`,`code`,`val`,`upper_code`,`status`,`insert_time`,`update_time`) values (10,'关于SpringCloud的文章','wzlx010102','SpringCloud','wzlx0101','1',NULL,'2019-11-21 23:07:24');
insert  into `const`(`id`,`depict`,`code`,`val`,`upper_code`,`status`,`insert_time`,`update_time`) values (11,'关于Spring的文章','wzlx010103','Spring','wzlx0101','1',NULL,'2019-11-21 23:07:24');
insert  into `const`(`id`,`depict`,`code`,`val`,`upper_code`,`status`,`insert_time`,`update_time`) values (12,'vue组件的简单使用','wzlx010201','vue组件','wzlx0102','1',NULL,'2019-11-21 23:08:20');
insert  into `const`(`id`,`depict`,`code`,`val`,`upper_code`,`status`,`insert_time`,`update_time`) values (13,'vuex全局仓库的使用','wzlx010202','vuex','wzlx0102','1',NULL,'2019-11-21 23:08:20');
insert  into `const`(`id`,`depict`,`code`,`val`,`upper_code`,`status`,`insert_time`,`update_time`) values (14,'mysql相关的文章','wzlx010301','mysql','wzlx0103','1',NULL,'2019-11-21 23:09:28');
