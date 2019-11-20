package com.yanger.generator.start;

import java.util.ArrayList;
import java.util.List;

import com.yanger.generator.support.GenConfig;
import com.yanger.generator.support.GenParam;
import com.yanger.generator.support.GenType;
import com.yanger.generator.support.Generator;

/**
 * @description 代码生成执行程序入口类
 * @author 杨号
 * @date 2018年9月14日
 */
public class CodeGenerator {

	public static void main(String[] args) {

		List<GenParam> paramList = new ArrayList<GenParam>();
		// 模块包名 -- 扫描的表名
		paramList.add(new GenParam("blog", new String[] { "article" }));
		GenConfig gc = new GenConfig();
		gc.setBasePackage("com.yanger");
		// 设置要忽略的表名前缀，默认空
		gc.setIgnoreTablePrefixs(new String[] { "yanger" });
		// 设置PO是否保留前缀(设置忽略表名前缀时)，默认true
		gc.setKeepPrefixForPO(false);
		// 设置取操作时间函数，默认空
		gc.setOperateTimeForHisFunc("");
		// 设置是否默认开启二级缓存（影响base中的MapperXML），默认false
		gc.setDefaultCache(false);
		// mysql 数据库相关配置
		// 设置基本保存目录（Java源代码根目录）
		gc.setSaveDir("src/main/java");
		gc.setDbDriverName("com.mysql.jdbc.Driver");
		gc.setDbSchema("platform_devdb");
		gc.setDbUser("root");
		gc.setDbPassword("1234");
		gc.setDbUrl("jdbc:mysql://localhost:3306/yanger?characterEncoding=utf8&autoReconnect=true");

		// 支持生成的文件类型:生成PO、BASE_MAPPER_XML（自动覆盖）、Dao、VO、MapperXML（不覆盖）
		gc.setGenTypes(
				new GenType[] { GenType.VO, GenType.PO, GenType.DAO, GenType.BASE_MAPPER_XML, GenType.MAPPER_XML });

		Generator generator = new Generator();
		generator.setGenConfig(gc);
		generator.setParamList(paramList);
		generator.generate();
	}
}
