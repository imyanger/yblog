package com.yanger.generator.util;

/**
 * @description 数据库类型工具类
 * @author 杨号
 * @date 2018年9月14日
 */
public class TypeUtils {

	private TypeUtils() {

	}

	public static String processType(String type) {
		String result = "";
		if (type.endsWith(" identity")) {
			type = type.substring(0, type.length() - " identity".length());
		}
		switch (type.toLowerCase()) {

		case "nvarchar":
		case "varchar2":
		case "varchar":
		case "_varchar":
		case "char":
		case "bpchar":
		case "_bpchar":
		case "text":
		case "tid":
		case "tinytext":
		case "mediumtext":
		case "longtext":
		case "enum":
		case "set":
		case "cidr":
		case "inet":
		case "macaddr":
		case "macaddr8":
		case "xml":
		case "json":
		case "jsonb":
		case "pg_lsn":
			result = "String";
			break;
		case "double":
		case "float8":
		case "float4":
			result = "Double";
			break;
		case "float":
		case "real":
			result = "Float";
			break;
		case "id":
		case "int":
		case "int2":
		case "int4":
		case "integer":
		case "serial":
		case "tinyint":
		case "smallint":
		case "mediumint":
			result = "Integer";
			break;
		case "bigint":
		case "bigserial":
		case "int8":
			result = "Long";
			break;
		case "date":
		case "time":
		case "timetz":
		case "datetime":
		case "smalldatetime":
		case "year":
		case "timestamp":
		case "abstime":
		case "interval":
		case "reltime":
			result = "Date";
			break;
		case "bit":
		case "varbit":
		case "boolean":
		case "bool":
			result = "Boolean";
			break;
		case "number":
		case "numeric":
		case "money":
		case "decimal":
			result = "BigDecimal";
			break;
		case "binary":
		case "varbinary":
		case "raw":
		case "blob":
		case "tinyblob":
		case "mediumblob":
		case "logngblob":
		case "bytea":
		case "_bytea":
			result = "byte[]";
			break;
		case "uuid":
			result = "UUID";
			break;
		default:
			System.err.println("Unsupport type [" + type + "]");
			break;
		}
		return result;
	}

}