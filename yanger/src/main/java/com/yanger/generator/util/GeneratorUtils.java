package com.yanger.generator.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yanger.generator.schema.Column;
import com.yanger.generator.schema.PrimaryKey;
import com.yanger.generator.schema.Table;

/**
 * @description 生成工具类
 * @author 杨号
 * @date 2018年9月14日
 */
public class GeneratorUtils {

	public static List<String> getTableColumnTypes(Table table) {
		List<String> types = new ArrayList<>();
		List<Column> columns = table.getColumns();
		int size = columns.size();
		for (int i = 0; i < size; i++) {
			Column column = columns.get(i);
			types.add(column.getType());
		}
		return types;
	}

	public static List<Column> getTablePrimaryKeysColumns(Table table) {
		Map<String, Column> columnMap = new HashMap<>();
		List<Column> columns = table.getColumns();
		int size = columns.size();
		for (int i = 0; i < size; i++) {
			Column column = columns.get(i);
			columnMap.put(column.getName().toLowerCase(), column);
		}
		List<Column> result = new ArrayList<>();
		List<PrimaryKey> primaryKeys = table.getPrimaryKeys();
		size = primaryKeys.size();
		for (int i = 0; i < size; i++) {
			PrimaryKey primaryKey = primaryKeys.get(i);
			result.add(columnMap.get(primaryKey.getColumnName().toLowerCase()));
		}
		return result;
	}

	/**
	 * @description 如果主键为单主键且为只增字段则返回字段名，否则返回空串
	 * @param table-对应表
	 * @return 单主键且为只增字段则返回字段名，否则返回空串
	 */
	public static String getKeyProperty(final Table table) {
		List<Column> primaryKeysColumns = getTablePrimaryKeysColumns(table);
		String result = "";
		if (primaryKeysColumns.size() == 1) {
			Column column = primaryKeysColumns.get(0);
			if (column.isAutoIncrement()) {
				result = column.getName();
			}
		}
		return result;
	}

	/**
	 * @description 返回主键字段名列表（全小写）
	 * @param table
	 * @return
	 */
	public static List<String> getTablePrimaryKeysCloumnNames(Table table) {
		List<String> result = new ArrayList<>();
		List<PrimaryKey> primaryKeys = table.getPrimaryKeys();
		int size = primaryKeys.size();
		for (int i = 0; i < size; i++) {
			PrimaryKey primaryKey = primaryKeys.get(i);
			result.add(primaryKey.getColumnName().toLowerCase());
		}
		return result;
	}

	public static List<String> getTablePrimaryKeysTypes(Table table) {
		Map<String, String> columnMap = new HashMap<>();
		List<Column> columns = table.getColumns();
		int size = columns.size();
		for (int i = 0; i < size; i++) {
			Column column = columns.get(i);
			columnMap.put(column.getName().toLowerCase(), column.getType());
		}

		List<String> types = new ArrayList<>();
		List<PrimaryKey> primaryKeys = table.getPrimaryKeys();
		size = primaryKeys.size();
		for (int i = 0; i < size; i++) {
			PrimaryKey primaryKey = primaryKeys.get(i);
			types.add(columnMap.get(primaryKey.getColumnName().toLowerCase()));
		}
		return types;
	}

	/**
	 * @description 字段是否为日期类型
	 * @param types-字段类型列表
	 * @return
	 */
	public static boolean isDate(List<String> types) {
		for (String type : types) {
			String t = type.toLowerCase();
			if (t.contains("date") || t.contains("time")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @description 字段是否为浮点数类型
	 * @param types-字段类型列表
	 * @return
	 */
	public static boolean isDecimal(List<String> types) {
		for (String type : types) {
			if (type.toLowerCase().contains("number") || type.toLowerCase().contains("numeric")
					|| type.toLowerCase().contains("decimal")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @description 得到实例名称
	 * @param field-表字段
	 * @return
	 */
	public static String getInstanceName(String field) {
		return WordFileUtils.getBeautyInstanceName(field);
	}

	/**
	 * @description 得到对象名称
	 * @param field-表字段
	 * @return
	 */
	public static String getObjectName(String field) {
		return WordFileUtils.getBeautyObjectName(field);
	}

}
