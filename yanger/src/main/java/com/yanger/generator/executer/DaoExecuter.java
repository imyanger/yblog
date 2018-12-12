package com.yanger.generator.executer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import com.yanger.generator.schema.Column;
import com.yanger.generator.schema.PrimaryKey;
import com.yanger.generator.schema.Table;
import com.yanger.generator.support.GenConfig;
import com.yanger.generator.support.GenFileInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * @description dao层接口生成程序
 * @author 杨号
 * @date 2018年9月14日
 */
@Slf4j
public class DaoExecuter extends BaseExecuter {

	private GenFileInfo daoInfo;

	private GenFileInfo poInfo;

	public DaoExecuter(GenConfig genConfig, GenFileInfo daoInfo, GenFileInfo poInfo) {
		super(genConfig);
		this.daoInfo = daoInfo;
		this.poInfo = poInfo;
	}

	public void build(Table table) throws IOException {
		List<PrimaryKey> primaryKeys = table.getPrimaryKeys();
		if (primaryKeys.size() != 1) {
			throw new IllegalArgumentException("目前只支持单一主键的表");
		}
		PrimaryKey primaryKey = primaryKeys.get(0);
		Column idColumn = null;
		List<Column> columns = table.getColumns();
		int size = columns.size();
		for (int i = 0; i < size; i++) {
			Column column = columns.get(i);
			if (column.getName().equalsIgnoreCase(primaryKey.getColumnName())) {
				idColumn = column;
				break;
			}
		}
		if (idColumn == null) {

			throw new IllegalArgumentException("找不到主键名对应的字段");
		}

		File mapperFile = new File(daoInfo.getPath(), daoInfo.getName() + JAVA_SUFFIX);
		try (BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(mapperFile), "utf-8"))) {
			bw.write("package " + daoInfo.getPackageName() + ";");
			bw.newLine();
			bw.newLine();
			bw.write("import org.apache.ibatis.annotations.Mapper;");
			bw.newLine();
			bw.newLine();
			bw.write("import " + poInfo.getPackageName() + "." + poInfo.getName() + ";");
			bw.newLine();
			bw.write("import com.yanger.generator.dao.MybatisBaseDao;");
			bw.newLine();
			buildClassComment(bw, "", "表" + getName(table.getName()) + "对应Dao接口");
			bw.newLine();
			bw.write("@Mapper");
			bw.newLine();
			bw.write("public interface " + daoInfo.getName() + " extends MybatisBaseDao<" + poInfo.getName() + ", "
					+ processType(idColumn) + "> {");
			bw.newLine();
			bw.newLine();
			bw.write("}");
			bw.flush();
		}
		log.info("Generate Dao file " + mapperFile.getAbsolutePath());
	}

}
