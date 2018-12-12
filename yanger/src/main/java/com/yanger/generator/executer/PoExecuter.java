package com.yanger.generator.executer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import com.yanger.generator.schema.Column;
import com.yanger.generator.schema.Table;
import com.yanger.generator.support.GenConfig;
import com.yanger.generator.support.GenFileInfo;
import com.yanger.generator.util.GeneratorUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @description PO生成程序
 * @author 杨号
 * @date 2018年9月14日
 */
@Slf4j
public class PoExecuter extends BaseExecuter {

	private GenFileInfo poInfo;

	public PoExecuter(GenConfig genConfig, GenFileInfo poInfo) {
		super(genConfig);
		this.poInfo = poInfo;
	}

	public void build(Table table) throws IOException {
		if (table.getPrimaryKeys().size() == 1) {
			buildPo(table);
		} else {
			buildUnionKeyPo(table);
			buildUnionPo(table);
		}
	}

	private void buildClassHeader(BufferedWriter bw, Table table, List<String> types, String className, String info)
			throws IOException {
		bw.write(String.format("package %s;", poInfo.getPackageName()));
		bw.newLine();
		bw.newLine();
		bw.write("import java.io.Serializable;");
		bw.newLine();
		if (GeneratorUtils.isDate(types)) {
			bw.write("import java.util.Date;");
			bw.newLine();
		}
		if (GeneratorUtils.isDecimal(types)) {
			bw.write("import java.math.BigDecimal;");
			bw.newLine();
		}
		bw.newLine();
		bw.write("import lombok.Data;");
		bw.newLine();
		String classComment = "对应表名：" + getName(table.getName());
		if (table.getComment() != null && table.getComment().trim().length() > 0) {
			classComment = classComment + ",备注：" + table.getComment().trim();
		}
		buildClassComment(bw, String.format("表%s的%s，通过com.yanger.generator包代码工具自动生成", getName(table.getName()), info),
				classComment);
		bw.newLine();
		bw.write("@Data");
		bw.newLine();
		bw.write(String.format("public class %s implements Serializable {", className));
		bw.newLine();
		bw.newLine();
		bw.write("\tprivate static final long serialVersionUID = 1L;");
		bw.newLine();
		bw.newLine();
	}

	private void buildFieldGetterSetter(BufferedWriter bw, Column column) throws IOException {
		String field = getInstanceName(column.getName());
		bw.write("\t/** 对应字段：" + getName(column.getName()));
		String comment = column.getComment();
		if (comment != null && comment.trim().length() > 0) {
			bw.write(",备注：" + comment.trim());
		}
		bw.write(" */");
		bw.newLine();
		bw.write("\tprivate " + processType(column) + " " + field + ";");
		bw.newLine();
		bw.newLine();
	}

	public void buildPo(Table table) throws IOException {
		List<String> types = GeneratorUtils.getTableColumnTypes(table);
		File beanFile = new File(poInfo.getPath(), poInfo.getName() + JAVA_SUFFIX);
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile)))) {
			buildClassHeader(bw, table, types, poInfo.getName(), "PO对象");
			List<Column> columns = table.getColumns();
			int size = columns.size();
			for (int i = 0; i < size; i++) {
				Column column = columns.get(i);
				buildFieldGetterSetter(bw, column);
			}
			bw.write("}");
			bw.newLine();
			bw.flush();
		}
		log.info("Generate PO file " + beanFile.getAbsolutePath());
	}

	public void buildUnionKeyPo(Table table) throws IOException {
		List<String> types = GeneratorUtils.getTablePrimaryKeysTypes(table);
		String keyPoName = poInfo.getName() + "Key";
		File beanFile = new File(poInfo.getPath(), keyPoName + JAVA_SUFFIX);
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile)))) {
			buildClassHeader(bw, table, types, keyPoName, "复合主键PO主键对象");
			List<Column> columns = GeneratorUtils.getTablePrimaryKeysColumns(table);
			int size = columns.size();
			for (int i = 0; i < size; i++) {
				Column column = columns.get(i);
				buildFieldGetterSetter(bw, column);
			}

			bw.newLine();
			bw.write("}");
			bw.newLine();
			bw.flush();
		}
		log.info("Generate PO Key file " + beanFile.getAbsolutePath());
	}

	public void buildUnionPo(Table table) throws IOException {
		List<String> types = GeneratorUtils.getTableColumnTypes(table);
		List<String> primaryKeysCloumnNames = GeneratorUtils.getTablePrimaryKeysCloumnNames(table);
		String keyPoName = poInfo.getName() + "Key";
		File beanFile = new File(poInfo.getPath(), poInfo.getName() + JAVA_SUFFIX);
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile)))) {
			buildClassHeader(bw, table, types, poInfo.getName(), "复合主键PO对象");
			bw.write("\t/** 对应复合主键 */");
			bw.newLine();
			bw.write("\tprivate " + keyPoName + " " + getInstanceName(keyPoName) + ";");
			bw.newLine();
			List<Column> columns = table.getColumns();
			int size = columns.size();
			for (int i = 0; i < size; i++) {
				Column column = columns.get(i);
				if (primaryKeysCloumnNames.contains(column.getName().toLowerCase())) {
					continue;
				}
				buildFieldGetterSetter(bw, column);
			}

			bw.newLine();
			bw.write("}");
			bw.newLine();
			bw.flush();
		}
		log.info("Generate PO file " + beanFile.getAbsolutePath());
	}

}
