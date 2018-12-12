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
 * @description VO生成程序
 * @author 杨号
 * @date 2018年9月14日
 */
@Slf4j
public class VoExecuter extends BaseExecuter {

	private GenFileInfo voInfo;

	public VoExecuter(GenConfig genConfig, GenFileInfo voInfo) {
		super(genConfig);
		this.voInfo = voInfo;
	}

	public void build(Table table) throws IOException {
		if (table.getPrimaryKeys().size() == 1) {
			buildVo(table);
		} else {
			buildUnionKeyVo(table);
			buildUnionVo(table);
		}
	}

	private void buildClassHeader(BufferedWriter bw, Table table, List<String> types, String className, String info)
			throws IOException {
		bw.write("package " + voInfo.getPackageName() + ";");
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

		buildClassComment(bw, String.format("表%s的%s,通过com.yanger.generator包代码工具自动生成", getName(table.getName()), info),
				classComment);

		bw.newLine();
		bw.write("@Data");
		bw.newLine();
		bw.write("public class " + className + " implements Serializable {");
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

	public void buildVo(Table table) throws IOException {
		List<String> types = GeneratorUtils.getTableColumnTypes(table);
		File beanFile = new File(voInfo.getPath(), voInfo.getName() + JAVA_SUFFIX);
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile)))) {

			buildClassHeader(bw, table, types, voInfo.getName(), "VO对象");

			List<Column> columns = table.getColumns();
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
		log.info("Generate VO file " + beanFile.getAbsolutePath());
	}

	public void buildUnionKeyVo(Table table) throws IOException {
		List<String> types = GeneratorUtils.getTablePrimaryKeysTypes(table);
		String keyVoName = voInfo.getName().substring(0, voInfo.getName().length() - 2) + "KeyVo";
		File beanFile = new File(voInfo.getPath(), keyVoName + JAVA_SUFFIX);
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile)))) {

			buildClassHeader(bw, table, types, keyVoName, "复合主键VO主键对象");
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
		log.info("Generate VO Key file " + beanFile.getAbsolutePath());
	}

	public void buildUnionVo(Table table) throws IOException {
		List<String> types = GeneratorUtils.getTableColumnTypes(table);
		String keyVoName = voInfo.getName().substring(0, voInfo.getName().length() - 2) + "KeyVo";
		List<String> primaryKeysCloumnNames = GeneratorUtils.getTablePrimaryKeysCloumnNames(table);
		File beanFile = new File(voInfo.getPath(), voInfo.getName() + JAVA_SUFFIX);
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile)))) {

			buildClassHeader(bw, table, types, voInfo.getName(), "复合主键VO对象");
			bw.write("\t/** 对应复合主键 */");
			bw.newLine();
			bw.write("\tprivate " + keyVoName + " " + getInstanceName(keyVoName) + ";");
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
			bw.write("}");
			bw.newLine();
			bw.flush();
		}
		log.info("Generate VO file " + beanFile.getAbsolutePath());
	}

}
