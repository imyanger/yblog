package com.yanger.generator.executer;

import java.io.BufferedWriter;
import java.io.IOException;

import com.yanger.generator.schema.Column;
import com.yanger.generator.schema.Table;
import com.yanger.generator.support.GenConfig;
import com.yanger.generator.util.TypeUtils;
import com.yanger.generator.util.WordFileUtils;

/**
 * @description 基础生成类
 * @author 杨号
 * @date 2018年9月14日
 */
public abstract class BaseExecuter {

	protected static final String JAVA_SUFFIX = ".java";

	protected static final String XML_SUFFIX = ".xml";

	protected GenConfig genConfig;

	public BaseExecuter(GenConfig genConfig) {
		this.genConfig = genConfig;
	}

	/**
	 * @description 生成文件
	 * @param table-数据库表
	 * @throws IOException
	 */
	public abstract void build(Table table) throws IOException;

	/**
	 * @description 构建类上面的注释
	 * @param bw
	 * @param text
	 * @return
	 * @throws IOException
	 */
	protected static void buildClassComment(BufferedWriter bw, String prefix, String text) throws IOException {
		bw.newLine();
		bw.write("/**");
		bw.newLine();
		if (prefix != null && prefix.trim().length() > 0) {
			bw.write(" * " + prefix);
			bw.newLine();
		}
		bw.write(" * " + text);
		bw.newLine();
		bw.write(" */");
	}

	protected static String processType(Column column) {
		String type = column.getType();
		return TypeUtils.processType(type);
	}

	/**
	 * @description 得到实例名称
	 * @param field-表字段
	 * @return
	 */
	protected static String getInstanceName(String field) {
		return WordFileUtils.getBeautyInstanceName(field);
	}

	/**
	 * @description 得到对象名称
	 * @param field-表字段
	 * @return
	 */
	protected static String getObjectName(String field) {
		return WordFileUtils.getBeautyObjectName(field);
	}

	public String getName(final String name) {
		String result = name;
		if (genConfig.isIgnoreUnderline()) {
			result = name.replace("_", "");
		}
		return result;
	}

}
