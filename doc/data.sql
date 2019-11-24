-- 文章模块的常量（学习笔记、心情随笔）必须插入
insert  into `const`(`id`,`depict`,`code`,`val`,`upper_code`,`status`,`insert_time`,`update_time`) values (1,'学习笔记模块','wzlx01','学习笔记','wzlx','1','2019-11-18 19:08:53','2019-11-18 19:08:58');
insert  into `const`(`id`,`depict`,`code`,`val`,`upper_code`,`status`,`insert_time`,`update_time`) values (2,'心情随笔模块','wzlx02','心情随笔','wzlx','1','2019-11-18 19:08:55','2019-11-18 19:09:00');

-- 初始化用户
insert  into `blog_user`(`user_id`,`user_code`,`password`,`user_nick_name`,`gender`,`user_img_path`,`summary`,`age`,`phone_number`,`moblie`,`email`,`address`,`graduate_school`,`education`,`birtd`,`user_real_name`,`user_type`,`status`,`insert_time`,`update_time`) values (1,'imyanger','D91B7AB25BB94AB4335D978031AF34D6','imyanger',NULL,NULL,NULL,NULL,NULL,'18888888888','imyanger@qq.com',NULL,NULL,NULL,NULL,NULL,'1',NULL,NULL,'2019-11-21 22:54:15');
insert  into `blog_user`(`user_id`,`user_code`,`password`,`user_nick_name`,`gender`,`user_img_path`,`summary`,`age`,`phone_number`,`moblie`,`email`,`address`,`graduate_school`,`education`,`birtd`,`user_real_name`,`user_type`,`status`,`insert_time`,`update_time`) values (2,'admin123','0192023A7BBD73250516F069DF18B500','超级管理员',NULL,NULL,NULL,NULL,NULL,'17777777777','admin123@qq.com',NULL,NULL,NULL,NULL,NULL,'2',NULL,NULL,'2019-11-21 22:55:53');
