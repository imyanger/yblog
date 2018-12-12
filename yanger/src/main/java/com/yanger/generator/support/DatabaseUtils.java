package com.yanger.generator.support;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yanger.generator.schema.Column;
import com.yanger.generator.schema.Database;
import com.yanger.generator.schema.PrimaryKey;
import com.yanger.generator.schema.Table;

import lombok.extern.slf4j.Slf4j;

/**
 * @description 数据库元数据解析工具类
 * @author 杨号
 * @date 2018年9月14日
 */
@Slf4j
public class DatabaseUtils {

	private Connection conn = null;

	private String schema;

	Database database = null;

	/**
	 * @description 禁止实例化
	 */
	private DatabaseUtils() {

	}

	public static DatabaseUtils getInstance(Connection conn, String schema) {
		DatabaseUtils obj = new DatabaseUtils();
		try {
			DatabaseMetaData metaData = conn.getMetaData();
			Database database = new Database();
			database.setProductName(metaData.getDatabaseProductName());
			database.setProductVersion(metaData.getDatabaseProductVersion());
			obj.conn = conn;
			obj.database = database;
			obj.schema = schema;
		} catch (Exception e) {
			log.warn("{}", e);
		}
		return obj;
	}

	public Map<String, String> getAllTableNamesMap() {
		ResultSet rs = null;
		Map<String, String> result = new HashMap<>();
		try {
			DatabaseMetaData metaData = conn.getMetaData();
			rs = metaData.getTables(null, schema, null, new String[] { "TABLE" });
			while (rs.next()) {
				String tableName = rs.getString("TABLE_NAME");
				result.put(tableName.toLowerCase(), tableName);
			}
			rs.close();
		} catch (SQLException e) {
			log.warn("{}", e);
		}
		return result;
	}

	public Table getTableInfo(String tableName) {
		Table table = new Table();
		table.setName(tableName);

		ResultSet rs = null;
		try {
			// 获取表基本信息
			DatabaseMetaData metaData = conn.getMetaData();
			rs = metaData.getTables(null, schema, tableName, new String[] { "TABLE" });
			if (rs.next()) {
				table.setComment(rs.getString("REMARKS"));
			}
			rs.close();

		} catch (SQLException e) {
			log.warn("{}", e);
		}
		table.setColumns(getTableColumns(tableName));
		table.setPrimaryKeys(getTablePrimaryKeys(tableName));
		return table;
	}

	private List<Column> getTableColumns(String tableName) {
		List<Column> columns = new ArrayList<>();

		ResultSet rs = null;
		try {
			// 获取字段基本信息
			DatabaseMetaData metaData = conn.getMetaData();
			rs = metaData.getColumns(null, schema, tableName, null);
			while (rs.next()) {
				Column column = new Column();
				column.setName(rs.getString("COLUMN_NAME"));
				column.setType(rs.getString("TYPE_NAME"));
				column.setSize(rs.getInt("COLUMN_SIZE"));
				column.setComment(rs.getString("REMARKS"));
				column.setNullable(rs.getBoolean("NULLABLE"));
				column.setDefaultValue(rs.getString("COLUMN_DEF"));
				column.setAutoIncrement(rs.getBoolean("IS_AUTOINCREMENT"));
				// column.setGeneratedColumn(rs.getBoolean("IS_GENERATEDCOLUMN"));JTDS不支持
				columns.add(column);
			}
			rs.close();

		} catch (SQLException e) {
			log.warn("{}", e);
		}

		return columns;
	}

	private List<PrimaryKey> getTablePrimaryKeys(String tableName) {
		List<PrimaryKey> primaryKeys = new ArrayList<>();

		ResultSet rs = null;
		try {
			// 获取字段基本信息
			DatabaseMetaData metaData = conn.getMetaData();
			rs = metaData.getPrimaryKeys(null, schema, tableName);
			while (rs.next()) {
				PrimaryKey obj = new PrimaryKey();
				obj.setColumnName(rs.getString("COLUMN_NAME"));
				obj.setKeySeq(rs.getInt("KEY_SEQ"));
				obj.setPkName(rs.getString("PK_NAME"));
				primaryKeys.add(obj);
			}
			rs.close();

		} catch (SQLException e) {
			log.warn("{}", e);
		}

		return primaryKeys;
	}

}
