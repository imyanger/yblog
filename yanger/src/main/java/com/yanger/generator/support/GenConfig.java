package com.yanger.generator.support;

import lombok.Data;

/**
 * @description 生成器配置类
 */
@Data
public class GenConfig {

	private String basePackage;

	// saveDir 文件生成目录
	protected String saveDir;

	protected String saveDirForVo;

	protected String saveDirForXml;

	private GenType[] genTypes;

	private String insertTimeForHisName;

	private String operateTimeForHisName;

	private String operateTimeForHisFunc;

	private String versionName;

	/** 设置要忽略表的名字前缀 */
	protected String[] ignoreTablePrefixs = null;

	/** PO是否保留前缀(设置忽略表名前缀时) */
	protected boolean keepPrefixForPO = true;

	/** 是否启用删除标志模式（不真删除，只是设置标志deleted_flag字段为1） */
	protected boolean deletedFlagMode = false;

	/** 设置忽略下划线，默认为false */
	protected boolean ignoreUnderline = false;

	// tableNames 要生成的表名称，如为空就直接指定所有表.格式为逗号分割
	protected String[] tableNames = null;

	/*
	 * 是否设置默认二级缓存（默认 false）
	 */
	protected boolean defaultCache = false;

	/*
	 * 是否覆盖当前路径下已有文件（默认 true）
	 */
	protected boolean fileOverride = true;

	/* db_config */
	protected boolean dbPrefix = false;

	/*
	 * 数据库字段使用下划线命名（默认 false）
	 */
	protected boolean dbColumnUnderline = false;

	protected String dbDriverName;

	protected String dbUser;

	protected String dbPassword;

	protected String dbUrl;

	protected String dbSchema;

}
