package com.yanger.generator.executer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.yanger.generator.schema.Column;
import com.yanger.generator.schema.PrimaryKey;
import com.yanger.generator.schema.Table;
import com.yanger.generator.support.GenConfig;
import com.yanger.generator.support.GenFileInfo;
import com.yanger.generator.util.GeneratorUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @description 公共mapper映射文件
 * @author 杨号
 * @date 2018年9月14日
 */
@Slf4j
public class MyBatisBaseXmlExecuter extends BaseExecuter {

	private GenFileInfo baseMapperXmlInfo;

	private GenFileInfo daoInfo;

	private GenFileInfo poInfo;

	public MyBatisBaseXmlExecuter(GenConfig genConfig, GenFileInfo baseMapperXmlInfo, GenFileInfo daoInfo,
			GenFileInfo poInfo) {
		super(genConfig);
		this.baseMapperXmlInfo = baseMapperXmlInfo;
		this.daoInfo = daoInfo;
		this.poInfo = poInfo;
	}

	public void build(Table table) throws IOException {
		List<Column> columns = table.getColumns();
		File mapperXmlFile = new File(baseMapperXmlInfo.getPath(), baseMapperXmlInfo.getName() + XML_SUFFIX);
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperXmlFile)))) {
			bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			bw.newLine();
			bw.write(
					"<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
			bw.newLine();
			bw.write("<!-- ============================================================== -->");
			bw.newLine();
			bw.write("<!-- =======      通过com.yanger.generator包代码工具自动生成           ======= -->");
			bw.newLine();
			bw.write("<!-- =======   本配置文件中定义的节点可在自定义配置文件中直接使用     ======= -->");
			bw.newLine();
			bw.write("<!-- ============================================================== -->");
			bw.newLine();
			bw.write("<mapper namespace=\"" + daoInfo.getPackageName() + "." + daoInfo.getName() + "\">");
			if (genConfig.isDefaultCache()) {
				bw.newLine();
				bw.write("\t<!-- 默认开启二级缓存,使用Least Recently Used（LRU，最近最少使用的）算法来收回 -->");
				bw.newLine();
				bw.write("\t<cache/>");
			}
			bw.newLine();
			/*
			 * 下面开始写SqlMapper中的方法
			 */
			List<PrimaryKey> primaryKeys = table.getPrimaryKeys();
			PrimaryKey primaryKey = primaryKeys.get(0);

			buildBaseDaoBaseResultMap(bw, table, primaryKey, columns);
			buildBaseDaoBaseColumnList(bw, table, primaryKey, columns);
			buildBaseDaoBaseSelectByEntityWhere(bw, table, primaryKey, columns);
			buildBaseDaoBaseSelectByEntity(bw, table, primaryKey, columns);
			buildBaseDaoSelectByPrimaryKey(bw, table, primaryKey, columns);
			buildBaseDaoSelectBatchByPrimaryKeys(bw, table, primaryKey, columns);
			buildBaseDaoSelectPage(bw, table, primaryKey, columns);
			buildBaseDaoDeleteByPrimaryKey(bw, table, primaryKey, columns);
			buildBaseDaoDeleteBatchByPrimaryKeys(bw, table, primaryKey, columns);
			buildBaseDaoInsert(bw, table, primaryKey, columns);
			buildBaseDaoInsertSelective(bw, table, primaryKey, columns);
			buildBaseDaoUpdateSelectiveByPrimaryKey(bw, table, primaryKey, columns);
			buildBaseDaoUpdateByPrimaryKey(bw, table, primaryKey, columns);

			bw.write("</mapper>");
			bw.flush();
		}
		log.info("Generate BaseXml file " + mapperXmlFile.getAbsolutePath());
	}

	/**
	 * 通用返回参数
	 *
	 * @param bw
	 * @param idMap
	 * @param columns
	 * @throws IOException
	 */
	protected void buildBaseDaoBaseResultMap(BufferedWriter bw, Table table, PrimaryKey primaryKey,
			List<Column> columns) throws IOException {
		int size = columns.size();
		bw.newLine();
		bw.write("\t<!-- 通用查询结果对象-->");
		bw.newLine();
		bw.write(
				"\t<resultMap id=\"BaseResultMap\" type=\"" + poInfo.getPackageName() + "." + poInfo.getName() + "\">");
		bw.newLine();

		for (int i = 0; i < size; i++) {
			Column column = columns.get(i);
			if (column.getName().equalsIgnoreCase(primaryKey.getColumnName())) {
				bw.write("\t\t <id ");
			} else {
				bw.write("\t\t <result ");
			}
			bw.write("column=\"" + getName(column.getName()) + "\" ");
			bw.write("property=\"" + getInstanceName(column.getName()) + "\"/> ");
			bw.newLine();
		}
		bw.write("\t</resultMap>");
		bw.newLine();
		bw.newLine();
	}

	protected void buildBaseDaoBaseColumnList(BufferedWriter bw, Table table, PrimaryKey primaryKey,
			List<Column> columns) throws IOException {
		int size = columns.size();
		bw.write("\t<!-- 通用查询结果列-->");
		bw.newLine();
		bw.write(getName("\t<sql id=\"Base_Column_List\">"));
		bw.newLine();
		bw.write("\t\t");
		for (int i = 0; i < size; i++) {
			Column column = columns.get(i);
			bw.write(" " + getName(column.getName()));
			if (i != size - 1) {
				bw.write(",");
			}
			if (i % 5 == 4) {
				bw.newLine();
				bw.write("\t\t");
			}
		}
		bw.newLine();
		bw.write("\t</sql>");
		bw.newLine();
		bw.newLine();
	}

	protected void buildBaseDaoBaseSelectByEntityWhere(BufferedWriter bw, Table table, PrimaryKey primaryKey,
			List<Column> columns) throws IOException {
		int size = columns.size();
		bw.write("\t<!-- 按对象查询记录的WHERE部分 -->");
		bw.newLine();
		bw.write(getName("\t<sql id=\"Base_Select_By_Entity_Where\">"));
		bw.newLine();
		for (int i = 0; i < size; i++) {
			Column column = columns.get(i);
			if ("deletedFlag".equalsIgnoreCase(getObjectName(column.getName()))) {
				bw.write("\t\t\tand " + getName(column.getName()) + " = '0'");
				bw.newLine();
			} else {
				bw.write("\t\t<if test=\"" + getInstanceName(column.getName()) + " != null\" >");
				bw.newLine();
				bw.write("\t\t\tand " + getName(column.getName()) + " = #{" + getInstanceName(column.getName()) + "}");
				bw.newLine();
				bw.write("\t\t</if>");
				bw.newLine();
			}
		}
		bw.write("\t</sql>");
		bw.newLine();
		bw.newLine();
	}

	protected void buildBaseDaoBaseSelectByEntity(BufferedWriter bw, Table table, PrimaryKey primaryKey,
			List<Column> columns) throws IOException {
		bw.write("\t<!-- 按对象查询记录的SQL部分 -->");
		bw.newLine();
		bw.write(getName("\t<sql id=\"Base_Select_By_Entity\">"));
		bw.newLine();
		bw.write("\t\tselect");
		bw.newLine();
		bw.write(getName("\t\t\t<include refid=\"Base_Column_List\" />"));
		bw.newLine();
		bw.write("\t\tfrom " + getName(table.getName()));
		bw.newLine();
		bw.write("\t\t<where>");
		bw.newLine();
		bw.write(getName("\t\t\t<include refid=\"Base_Select_By_Entity_Where\" />"));
		bw.newLine();
		bw.write("\t\t</where>");
		bw.newLine();
		bw.write("\t</sql>");
		bw.newLine();
		bw.newLine();
	}

	protected void buildBaseDaoSelectByPrimaryKey(BufferedWriter bw, Table table, PrimaryKey primaryKey,
			List<Column> columns) throws IOException {
		bw.write("\t<!-- 按主键查询一条记录 -->");
		bw.newLine();
		bw.write("\t<select id=\"selectById\" resultMap=\"BaseResultMap\" parameterType=\"map\">");
		bw.newLine();
		bw.write("\t\tselect");
		bw.newLine();
		bw.write(getName("\t\t\t<include refid=\"Base_Column_List\" />"));
		bw.newLine();
		bw.write("\t\tfrom " + getName(table.getName()));
		bw.newLine();
		bw.write("\t\twhere " + getName(primaryKey.getColumnName()) + " = #{param1}");
		bw.newLine();
		bw.write("\t</select>");
		bw.newLine();
		bw.newLine();
	}

	protected void buildBaseDaoSelectBatchByPrimaryKeys(BufferedWriter bw, Table table, PrimaryKey primaryKey,
			List<Column> columns) throws IOException {
		bw.write("\t<!-- 按主键List查询多条记录 -->");
		bw.newLine();
		bw.write("\t<select id=\"selectByIds\" resultMap=\"BaseResultMap\" parameterType=\"map\">");
		bw.newLine();
		bw.write("\t\tselect");
		bw.newLine();
		bw.write(getName("\t\t\t<include refid=\"Base_Column_List\" />"));
		bw.newLine();
		bw.write("\t\tfrom " + getName(table.getName()));
		bw.newLine();
		bw.write("\t\twhere " + getName(primaryKey.getColumnName()) + " in");
		bw.newLine();
		bw.write(
				"\t\t<foreach item=\"item\" index=\"index\" collection=\"list\" open=\"(\" separator=\",\" close=\")\">");
		bw.newLine();
		bw.write("\t\t\t#{item}");
		bw.newLine();
		bw.write("\t\t</foreach>");
		bw.newLine();
		bw.write("\t</select>");
		bw.newLine();
		bw.newLine();
	}

	protected void buildBaseDaoSelectPage(BufferedWriter bw, Table table, PrimaryKey primaryKey, List<Column> columns)
			throws IOException {
		bw.write("\t<!-- 按对象查询一页记录（多条记录） -->");
		bw.newLine();
		bw.write("\t<select id=\"selectPage\" resultMap=\"BaseResultMap\" parameterType=\"" + poInfo.getPackageName()
				+ "." + poInfo.getName() + "\">");
		bw.newLine();
		bw.write(getName("\t\t<include refid=\"Base_Select_By_Entity\" />"));
		bw.newLine();
		bw.write("\t</select>");
		bw.newLine();
		bw.newLine();
	}

	protected boolean containsDeleteFlagField(Table table) {
		List<Column> columns = table.getColumns();
		int size = columns.size();
		for (int i = 0; i < size; i++) {
			Column column = columns.get(i);
			if ("deletedFlag".equalsIgnoreCase(getObjectName(column.getName()))) {
				return true;
			}
		}
		return false;
	}

	protected void buildBaseDaoDeleteByPrimaryKey(BufferedWriter bw, Table table, PrimaryKey primaryKey,
			List<Column> columns) throws IOException {
		bw.write("\t<!-- 按主键删除一条记录 -->");
		bw.newLine();
		bw.write("\t<delete id=\"deleteById\" parameterType=\"map\">");
		bw.newLine();
		if (genConfig.isDeletedFlagMode() && containsDeleteFlagField(table)) {

			bw.write("\t\tupdate " + getName(table.getName()));
			bw.newLine();
			bw.write("\t\t<set>");
			bw.newLine();
			int size = columns.size();
			for (int i = 0; i < size; i++) {
				Column column = columns.get(i);
				if ("deletedFlag".equalsIgnoreCase(getObjectName(column.getName()))) {
					bw.write("\t\t\t");
					bw.write(getName(column.getName()));
					bw.write("= '1',");
					bw.newLine();
				}
				if (genConfig.getVersionName().equalsIgnoreCase(getObjectName(column.getName()))) {
					bw.write("\t\t\t");
					bw.write(getName(column.getName()));
					bw.write("=");
					bw.write(getName(column.getName()));
					bw.write("+1,");
					bw.newLine();
					continue;
				}
				if (genConfig.getOperateTimeForHisName().equalsIgnoreCase(getObjectName(column.getName()))
						&& StringUtils.isNotBlank(genConfig.getOperateTimeForHisFunc())) {
					bw.write("\t\t\t");
					bw.write(getName(column.getName()));
					bw.write("=");
					bw.write(genConfig.getOperateTimeForHisFunc());
					bw.write(",");
					bw.newLine();
					continue;
				}
			}
			bw.write("\t\t</set>");
		} else {

			bw.write("\t\tdelete from " + getName(table.getName()));
		}
		bw.newLine();
		bw.write("\t\twhere " + getName(primaryKey.getColumnName()) + " = #{param1}");
		bw.newLine();
		bw.write("\t</delete>");
		bw.newLine();
		bw.newLine();
	}

	protected void buildBaseDaoDeleteBatchByPrimaryKeys(BufferedWriter bw, Table table, PrimaryKey primaryKey,
			List<Column> columns) throws IOException {
		bw.write("\t<!-- 按主键List删除多条记录 -->");
		bw.newLine();
		bw.write("\t<delete id=\"deleteByIds\" parameterType=\"map\">");
		bw.newLine();
		if (genConfig.isDeletedFlagMode() && containsDeleteFlagField(table)) {

			bw.write("\t\tupdate " + getName(table.getName()));
			bw.newLine();
			bw.write("\t\t<set>");
			bw.newLine();
			int size = columns.size();
			for (int i = 0; i < size; i++) {
				Column column = columns.get(i);
				if ("deletedFlag".equalsIgnoreCase(getObjectName(column.getName()))) {
					bw.write("\t\t\t");
					bw.write(getName(column.getName()));
					bw.write("= '1',");
					bw.newLine();
				}
				if (genConfig.getVersionName().equalsIgnoreCase(getObjectName(column.getName()))) {
					bw.write("\t\t\t");
					bw.write(getName(column.getName()));
					bw.write("=");
					bw.write(getName(column.getName()));
					bw.write("+1,");
					bw.newLine();
					continue;
				}
				if (genConfig.getOperateTimeForHisName().equalsIgnoreCase(getObjectName(column.getName()))
						&& StringUtils.isNotBlank(genConfig.getOperateTimeForHisFunc())) {
					bw.write("\t\t\t");
					bw.write(getName(column.getName()));
					bw.write("=");
					bw.write(genConfig.getOperateTimeForHisFunc());
					bw.write(",");
					bw.newLine();
					continue;
				}
			}
			bw.write("\t\t</set>");
		} else {

			bw.write("\t\tdelete from " + getName(table.getName()));
		}
		bw.newLine();
		bw.write("\t\twhere " + getName(primaryKey.getColumnName()) + " in ");
		bw.newLine();
		bw.write(
				"\t\t<foreach item=\"item\" index=\"index\" collection=\"list\" open=\"(\" separator=\",\" close=\")\">");
		bw.newLine();
		bw.write("\t\t\t#{item}");
		bw.newLine();
		bw.write("\t\t</foreach>");
		bw.newLine();
		bw.write("\t</delete>");
		bw.newLine();
		bw.newLine();
	}

	/**
	 * 通用返回参数
	 *
	 * @param bw
	 * @param idMap
	 * @param columns
	 * @throws IOException
	 */
	protected void buildBaseDaoInsert(BufferedWriter bw, Table table, PrimaryKey primaryKey, List<Column> columns)
			throws IOException {
		int size = columns.size();
		bw.write("\t<!-- 完整插入一条记录-->");
		bw.newLine();
		bw.write("\t<insert id=\"insert\"");
		String keyProperty = GeneratorUtils.getKeyProperty(table);
		if (StringUtils.isNotBlank(keyProperty)) {
			bw.write(" useGeneratedKeys=\"true\" keyProperty=\"" + keyProperty + "\"");
		}
		bw.write(" parameterType=\"" + poInfo.getPackageName() + "." + poInfo.getName() + "\">");

		bw.newLine();
		bw.write("\t\tinsert into " + getName(table.getName()) + " (");

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			Column column = columns.get(i);
			if (genConfig.getInsertTimeForHisName().equalsIgnoreCase(getObjectName(getName(column.getName())))) {
				continue;
			}
			sb.append(getName(column.getName()));

			if (i != size - 1) {
				sb.append(", ");
			}
			if (i % 5 == 4) {
				sb.append("\r\n\t\t\t");
			}
		}
		if (sb.toString().endsWith(", \r\n\t\t\t")) {
			sb.delete(0, sb.length() - ", \r\n\t\t\t".length());
		}
		bw.write(sb.toString());
		bw.write(")");
		bw.newLine();
		bw.write("\t\tvalues(");
		sb.setLength(0);
		for (int i = 0; i < size; i++) {
			Column column = columns.get(i);
			if (genConfig.getInsertTimeForHisName().equalsIgnoreCase(getObjectName(column.getName()))) {
				continue;
			}
			if (genConfig.getOperateTimeForHisName().equalsIgnoreCase(getObjectName(column.getName()))) {
				sb.append(genConfig.getOperateTimeForHisFunc());
			} else if (genConfig.getVersionName().equalsIgnoreCase(getObjectName(column.getName()))) {
				sb.append("1");
			} else if ("deletedFlag".equalsIgnoreCase(getObjectName(column.getName()))) {
				sb.append("'0'");
			} else {
				sb.append("#{" + getInstanceName(column.getName()));
				sb.append("}");
			}
			if (i != size - 1) {
				sb.append(", ");
			}
			if (i % 5 == 4) {
				sb.append("\r\n\t\t\t");
			}
		}
		if (sb.toString().endsWith(", \r\n\t\t\t")) {
			sb.delete(0, sb.length() - ", \r\n\t\t\t".length());
		}
		bw.write(sb.toString());
		bw.write(")");
		bw.newLine();
		bw.write("\t</insert>");
		bw.newLine();
		bw.newLine();
	}

	protected void buildBaseDaoInsertSelective(BufferedWriter bw, Table table, PrimaryKey primaryKey,
			List<Column> columns) throws IOException {
		int size = columns.size();
		bw.write("\t<!-- 插入一条记录(为空的字段不操作) -->");
		bw.newLine();

		bw.write("\t<insert id=\"insertVal\"");
		String keyProperty = GeneratorUtils.getKeyProperty(table);
		if (StringUtils.isNotBlank(keyProperty)) {
			bw.write(" useGeneratedKeys=\"true\" keyProperty=\"" + keyProperty + "\"");
		}
		bw.write(" parameterType=\"" + poInfo.getPackageName() + "." + poInfo.getName() + "\">");
		bw.newLine();
		bw.write("\t\tinsert into " + getName(table.getName()) + "");
		bw.newLine();
		bw.write("\t\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >");
		bw.newLine();
		for (int i = 0; i < size; i++) {
			Column column = columns.get(i);
			if (genConfig.getInsertTimeForHisName().equalsIgnoreCase(getObjectName(column.getName()))) {
				continue;
			}

			if (genConfig.getVersionName().equalsIgnoreCase(getObjectName(column.getName()))) {
				bw.write("\t\t\t" + getName(column.getName()) + ",");
				bw.newLine();
				continue;
			}
			if (genConfig.isDeletedFlagMode() && "deletedFlag".equalsIgnoreCase(getObjectName(column.getName()))) {
				bw.write("\t\t\t" + getName(column.getName()) + ",");
				bw.newLine();
				continue;
			}
			if (genConfig.getOperateTimeForHisName().equalsIgnoreCase(getObjectName(column.getName()))
					&& StringUtils.isNotBlank(genConfig.getOperateTimeForHisFunc())) {
				bw.write("\t\t\t" + getName(column.getName()) + ",");
				bw.newLine();
				continue;
			}
			bw.write("\t\t\t<if test=\"" + getInstanceName(column.getName()) + " != null\" >");
			bw.newLine();
			bw.write("\t\t\t\t" + getName(column.getName()) + ",");
			bw.newLine();
			bw.write("\t\t\t</if>");
			bw.newLine();
		}
		bw.write("\t\t</trim>");
		bw.newLine();
		bw.write("\t\tvalues <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >");
		bw.newLine();
		for (int i = 0; i < size; i++) {
			Column column = columns.get(i);
			if (genConfig.getInsertTimeForHisName().equalsIgnoreCase(getObjectName(column.getName()))) {
				// do nothing
			} else if (genConfig.getVersionName().equalsIgnoreCase(getObjectName(column.getName()))) {
				bw.write("\t\t\t1,");
				bw.newLine();
			} else if (genConfig.isDeletedFlagMode()
					&& "deletedFlag".equalsIgnoreCase(getObjectName(column.getName()))) {
				bw.write("\t\t\t'0',");
				bw.newLine();
			} else if (genConfig.getOperateTimeForHisName().equalsIgnoreCase(getObjectName(column.getName()))
					&& StringUtils.isNotBlank(genConfig.getOperateTimeForHisFunc())) {
				bw.write("\t\t\t");
				bw.write(genConfig.getOperateTimeForHisFunc());
				bw.write(",");
				bw.newLine();
			} else {
				bw.write("\t\t\t<if test=\"" + getInstanceName(column.getName()) + " != null\" >");
				bw.newLine();
				bw.write("\t\t\t\t#{" + getInstanceName(column.getName()) + "},");
				bw.newLine();
				bw.write("\t\t\t</if>");
				bw.newLine();
			}
		}
		bw.write("\t\t</trim>");
		bw.newLine();
		bw.write("\t</insert>");
		bw.newLine();
		bw.newLine();
	}

	protected void buildBaseDaoUpdateSelectiveByPrimaryKey(BufferedWriter bw, Table table, PrimaryKey primaryKey,
			List<Column> columns) throws IOException {
		int size = columns.size();
		bw.write("\t<!-- 更新一条记录(为空的字段不操作) -->");
		bw.newLine();
		bw.write("\t<update id=\"updateValById\"");
		String keyProperty = GeneratorUtils.getKeyProperty(table);
		if (StringUtils.isNotBlank(keyProperty)) {
			bw.write(" useGeneratedKeys=\"true\" keyProperty=\"" + keyProperty + "\"");
		}
		bw.write(" parameterType=\"" + poInfo.getPackageName() + "." + poInfo.getName() + "\">");

		bw.newLine();
		bw.write("\t\tupdate " + getName(table.getName()));
		bw.newLine();
		bw.write("\t\t<set>");
		bw.newLine();
		for (int i = 0; i < size; i++) {
			Column column = columns.get(i);
			if (column.getName().equalsIgnoreCase(primaryKey.getColumnName())) {
				continue;
			}
			if (genConfig.getInsertTimeForHisName().equalsIgnoreCase(getObjectName(column.getName()))) {
				continue;
			}
			if (genConfig.getVersionName().equalsIgnoreCase(getObjectName(column.getName()))) {
				bw.write("\t\t\t");
				bw.write(getName(column.getName()));
				bw.write("=");
				bw.write(getName(column.getName()));
				bw.write("+1,");
				bw.newLine();
				continue;
			}
			if (genConfig.getOperateTimeForHisName().equalsIgnoreCase(getObjectName(column.getName()))
					&& StringUtils.isNotBlank(genConfig.getOperateTimeForHisFunc())) {
				bw.write("\t\t\t");
				bw.write(getName(column.getName()));
				bw.write("=");
				bw.write(genConfig.getOperateTimeForHisFunc());
				bw.write(",");
				bw.newLine();
				continue;
			}

			bw.write("\t\t\t<if test=\"" + getInstanceName(column.getName()) + " != null\" >");
			bw.newLine();
			bw.write("\t\t\t\t" + getName(column.getName()) + "=#{" + getInstanceName(column.getName()) + "},");
			bw.newLine();
			bw.write("\t\t\t</if>");
			bw.newLine();
		}
		bw.write("\t\t</set>");
		bw.newLine();
		bw.write("\t\twhere " + getName(primaryKey.getColumnName()) + " = #{"
				+ getInstanceName(primaryKey.getColumnName()) + "}");
		bw.newLine();
		bw.write("\t</update>");
		bw.newLine();
		bw.newLine();
	}

	protected void buildBaseDaoUpdateByPrimaryKey(BufferedWriter bw, Table table, PrimaryKey primaryKey,
			List<Column> columns) throws IOException {
		int size = columns.size();
		bw.write("\t<!-- 完整更新一条记录 -->");
		bw.newLine();
		bw.write("\t<update id=\"updateById\"");
		String keyProperty = GeneratorUtils.getKeyProperty(table);
		if (StringUtils.isNotBlank(keyProperty)) {
			bw.write(" useGeneratedKeys=\"true\" keyProperty=\"" + keyProperty + "\"");
		}
		bw.write(" parameterType=\"" + poInfo.getPackageName() + "." + poInfo.getName() + "\">");
		bw.newLine();
		bw.write("\t\tupdate " + getName(table.getName()));
		bw.newLine();
		bw.write("\t\tset ");
		for (int i = 0; i < size; i++) {
			Column column = columns.get(i);
			if (column.getName().equalsIgnoreCase(primaryKey.getColumnName())
					|| genConfig.getInsertTimeForHisName().equalsIgnoreCase(getObjectName(column.getName()))) {
				continue;
			}
			if (genConfig.getVersionName().equalsIgnoreCase(getObjectName(column.getName()))) {
				bw.write(column.getName() + "=" + getInstanceName(column.getName()) + "+1");
			} else if (genConfig.getOperateTimeForHisName().equalsIgnoreCase(getObjectName(column.getName()))
					&& StringUtils.isNotBlank(genConfig.getOperateTimeForHisFunc())) {
				bw.write(getName(column.getName()) + "=" + genConfig.getOperateTimeForHisFunc());
			} else {

				bw.write(getName(column.getName()) + "=#{" + getInstanceName(column.getName()) + "}");
			}
			if (i != size - 1) {
				bw.write(",");
				bw.newLine();

				bw.write("\t\t\t");
			}
		}
		bw.newLine();
		bw.write("\t\twhere " + getName(primaryKey.getColumnName()) + " = #{"
				+ getInstanceName(primaryKey.getColumnName()) + "}");
		bw.newLine();
		bw.write("\t</update>");
		bw.newLine();
		bw.newLine();
	}

}
