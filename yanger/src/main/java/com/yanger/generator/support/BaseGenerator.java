package com.yanger.generator.support;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.yanger.generator.schema.Database;
import com.yanger.generator.schema.Table;
import com.yanger.generator.util.WordFileUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @description 映射文件自动生成类
 * @author 杨号
 * @date 2018年9月14日
 */
@Slf4j
abstract class BaseGenerator {

	protected static final String JAVA_SUFFIX = ".java";

	protected static final String XML_SUFFIX = ".xml";

	protected GenConfig genConfig;

	protected List<GenParam> paramList;

	protected Database database;

	protected boolean fileOvervide = false;

	/**
	 * run 执行
	 */
	protected abstract void run(Table table, String basePackage) throws IOException;

	public void setGenConfig(GenConfig genConfig) {
		this.genConfig = genConfig;
	}

	public void setParamList(List<GenParam> paramList) {
		this.paramList = paramList;
	}

	/**
	 * @description 根据包名转换成具体路径
	 * @param packageName
	 * @return
	 */
	protected static String getPathFromPackageName(String packageName) {
		if (StringUtils.isEmpty(packageName)) {
			return "";
		}
		return packageName.replace(".", File.separator);
	}

	/**
	 * @description 生成文件地址
	 * @param segment-文件地址片段
	 * @return
	 */
	protected static String getFilePath(String savePath, String segment) {
		File folder = new File(savePath, segment);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		return folder.getPath();
	}

	protected boolean containsGenType(GenType genType) {
		for (GenType gen : genConfig.getGenTypes()) {
			if (gen == (genType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @description 生成映射文件
	 */
	public void generate() {
		WordFileUtils.initWordMap();

		if (StringUtils.isBlank(genConfig.getInsertTimeForHisName())) {
			genConfig.setInsertTimeForHisName("insertTimeForHis");
		}
		genConfig.setInsertTimeForHisName(genConfig.getInsertTimeForHisName().toLowerCase().replace("_", ""));

		if (StringUtils.isBlank(genConfig.getOperateTimeForHisName())) {
			genConfig.setOperateTimeForHisName("operateTimeForHis");
		}
		genConfig.setOperateTimeForHisName(genConfig.getOperateTimeForHisName().toLowerCase().replace("_", ""));

		if (StringUtils.isBlank(genConfig.getVersionName())) {
			genConfig.setVersionName("Version");
		}
		genConfig.setVersionName(genConfig.getVersionName().toLowerCase().replace("_", ""));

		// 设置SaveDirForVo默认值
		if (StringUtils.isBlank(genConfig.getSaveDirForVo())) {
			genConfig.setSaveDirForVo(genConfig.getSaveDir());
		}

		// 设置SaveDirForXml默认值
		if (StringUtils.isBlank(genConfig.getSaveDirForXml())) {
			genConfig.setSaveDirForXml(new File(genConfig.getSaveDir(), "../resources/mapper").getAbsolutePath());
		}
		if (containsGenType(GenType.VO) && StringUtils.isBlank(genConfig.getSaveDirForVo())) {
			genConfig.setSaveDirForVo((genConfig.getSaveDir()));
			log.info("未设置SaveDirForVo参数,使用默认位置");
		}
		if (containsGenType(GenType.MAPPER_XML) && StringUtils.isBlank(genConfig.getSaveDirForXml())) {
			throw new IllegalArgumentException("生成Mapper XML时需要设置SaveDirForXml参数");
		}
		if (containsGenType(GenType.BASE_MAPPER_XML) && StringUtils.isBlank(genConfig.getSaveDirForXml())) {
			throw new IllegalArgumentException("生成Base Mapper XML时需要设置SaveDirForXml参数");
		}
		try {
			Class.forName(genConfig.getDbDriverName());
			Properties props = new Properties();
			props.setProperty("user", genConfig.getDbUser());
			props.setProperty("password", genConfig.getDbPassword());
			props.setProperty("remarks", "true"); // 设置可以获取remarks信息
			props.setProperty("useInformationSchema", "true");// 设置可以获取tables
																// remarks信息

			try (Connection conn = DriverManager.getConnection(genConfig.getDbUrl(), props)) {

				DatabaseUtils databaseUtils = DatabaseUtils.getInstance(conn, genConfig.getDbSchema());
				database = databaseUtils.database;

				String dbName = database.getProductName().toLowerCase();
				if (StringUtils.isBlank(genConfig.getDbSchema())) {
					if (dbName.indexOf("oracle") != -1) {
						genConfig.setDbSchema(genConfig.getDbUser().toUpperCase());
					}
				}
				if (StringUtils.isBlank(genConfig.getOperateTimeForHisFunc())) {
					String dateFunc = "";
					if (dbName.indexOf("informix") != -1) {
						dateFunc = "current";
					} else if (dbName.indexOf("oracle") != -1) {
						dateFunc = "sysdate";
					} else if (dbName.indexOf("postgresql") != -1) {
						dateFunc = "now()";
					} else if (dbName.indexOf("mysql") != -1) {
						dateFunc = "now()";
					} else if (dbName.indexOf("sinoregal") != -1) {
						// sinoregal dynamic server,sinodb
						dateFunc = "current";
					} else if (dbName.indexOf("argon") != -1) {
						// argon dynamic server华胜
						dateFunc = "current";
					} else if (dbName.indexOf("ibm informix") != -1) {
						// 南大
						dateFunc = "current";
					} else if (dbName.indexOf("dynamic server") != -1) { // Informix序列
						dateFunc = "current";
					} else if (dbName.indexOf("sql server") != -1) {
						dateFunc = "GetDate()";
					}
					genConfig.setOperateTimeForHisFunc(dateFunc);
				}
				if (StringUtils.isBlank(genConfig.getOperateTimeForHisFunc())) {
					log.warn("请自行设置setOperateTimeForHisFunc");
				}
				/**
				 * 根据配置获取应该生成文件的表信息
				 */
				Map<String, String> tableNamesMap = databaseUtils.getAllTableNamesMap();
				if (tableNamesMap.size() == 0) {
					return;
				}

				for (GenParam genParam : paramList) {
					String[] tableNames = genParam.getTables();
					for (int i = 0; i < tableNames.length; i++) {
						String tableName = tableNames[i].toLowerCase();
						if (!tableNamesMap.containsKey(tableName)) {
							log.warn("Can't find table {}", tableName);
							continue;
						}
						Table table = databaseUtils.getTableInfo(tableNamesMap.get(tableName));
						run(table, genParam.getModule());
					}
				}
			}

		} catch (Exception e) {
			log.warn("{}", e);

		}
	}

	/**
	 * @description 根据是否覆盖标志决定是否覆盖当前已存在的文件
	 * @param dirPath
	 * @param beanName
	 * @param suffix
	 * @return
	 */
	protected boolean validFile(String dirPath, String beanName, String suffix) {
		File file = new File(dirPath, beanName + suffix);
		return !file.exists() || fileOvervide;
	}

	/**
	 * @description 自动打开生成文件的目录，根据 osName 执行相应命
	 * @author YangHao
	 * @time 2018年12月12日-下午11:08:35
	 */
	protected void openDir() {
		try {
			String osName = System.getProperty("os.name");
			if (osName != null) {
				if (osName.contains("Mac")) {
					Runtime.getRuntime().exec("open " + genConfig.getSaveDir());
				} else if (osName.contains("Windows")) {
					Runtime.getRuntime().exec("cmd /c start " + genConfig.getSaveDir());
				} else {
					log.error("save dir:" + genConfig.getSaveDir());
				}
			}
		} catch (IOException e) {
			log.warn("{}", e);
		}
	}

}
