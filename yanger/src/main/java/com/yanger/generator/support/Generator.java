package com.yanger.generator.support;

import java.io.IOException;

import com.yanger.generator.executer.DaoExecuter;
import com.yanger.generator.executer.MyBatisBaseXmlExecuter;
import com.yanger.generator.executer.MyBatisCustomXmlExecuter;
import com.yanger.generator.executer.PoExecuter;
import com.yanger.generator.executer.VoExecuter;
import com.yanger.generator.schema.Table;
import com.yanger.generator.util.GeneratorUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @description 代码生成工具
 * @author 杨号
 * @date 2018年9月14日
 */
@Slf4j
public class Generator extends BaseGenerator {

	private GenFileInfo voInfo;

	private GenFileInfo poInfo;

	private GenFileInfo daoInfo;

	private GenFileInfo baseMapperXmlInfo;

	private GenFileInfo mapperXmlInfo;

	private String assemblePackage(String module, String catalog) {
		String result = genConfig.getBasePackage() + "." + module;
		if (catalog != null && catalog.trim().length() > 0) {
			result = result + "." + catalog;
		}
		return result;
	}

	private String assembleXmlPackage(String module) {
		String result = "";
		if (module != null && module.trim().length() > 0) {
			result = module;
		} else {
			result = "misc";
		}
		return result;
	}

	private void resetFileInfo(final String beanNameIn, String module) {
		String saveDir = genConfig.getSaveDir();
		String beanName = beanNameIn;
		if (genConfig.getIgnoreTablePrefixs() != null) {
			for (String ignoreTablePrefix : genConfig.getIgnoreTablePrefixs()) {
				ignoreTablePrefix = ignoreTablePrefix.replace("_", "");
				if (beanName.toLowerCase().startsWith(ignoreTablePrefix.toLowerCase())) {
					beanName = beanName.substring(ignoreTablePrefix.length());
					break;
				}
			}
		}

		// VO
		String name = beanName + "Vo";
		String packageName = assemblePackage(module, "vo");
		String path = getFilePath(genConfig.getSaveDirForVo(), getPathFromPackageName(packageName));
		voInfo = new GenFileInfo(name, packageName, path);
		// PO
		name = beanName;
		if (genConfig.keepPrefixForPO) {
			name = beanNameIn;
		}
		packageName = assemblePackage(module, "po");
		path = getFilePath(saveDir, getPathFromPackageName(packageName));
		poInfo = new GenFileInfo(name, packageName, path);

		// Dao
		name = beanName + "Dao";
		packageName = assemblePackage(module, "dao");
		path = getFilePath(saveDir, getPathFromPackageName(packageName));
		daoInfo = new GenFileInfo(name, packageName, path);

		// BaseMapperXml
		name = beanName + "BaseDao";
		packageName = assembleXmlPackage(module);
		String xmlPath = getFilePath(genConfig.getSaveDirForXml(), "base");
		path = getFilePath(xmlPath, getPathFromPackageName(packageName));
		baseMapperXmlInfo = new GenFileInfo(name, packageName, path);

		// MapperXml
		name = beanName + "Dao";
		packageName = assembleXmlPackage(module);
		xmlPath = getFilePath(genConfig.getSaveDirForXml(), "custom");
		path = getFilePath(xmlPath, getPathFromPackageName(packageName));
		mapperXmlInfo = new GenFileInfo(name, packageName, path);
	}

	@Override
	protected void run(Table table, String module) throws IOException {
		log.info("============处理表" + table.getName() + "==================");
		if (table.getPrimaryKeys().isEmpty()) {
			log.info("表" + table.getName() + "没有主键字段，忽略生成，请手工编写.");
			return;
		}
		String beanName = GeneratorUtils.getObjectName(table.getName());
		resetFileInfo(beanName, module);

		fileOvervide = false;
		if (containsGenType(GenType.VO) && validFile(voInfo.getPath(), voInfo.getName(), JAVA_SUFFIX)) {
			new VoExecuter(genConfig, voInfo).build(table);
		}

		fileOvervide = true;
		if (containsGenType(GenType.PO) && validFile(poInfo.getPath(), poInfo.getName(), JAVA_SUFFIX)) {
			new PoExecuter(genConfig, poInfo).build(table);
		}

		if (table.getPrimaryKeys().size() > 1) {
			log.info("表" + table.getName() + "为联合主键，忽略dao和mapper生成,请手工编写.");
			return;
		}
		fileOvervide = false;
		if (containsGenType(GenType.DAO) && validFile(daoInfo.getPath(), daoInfo.getName(), JAVA_SUFFIX)) {
			new DaoExecuter(genConfig, daoInfo, poInfo).build(table);
		}
		fileOvervide = true;
		if (containsGenType(GenType.BASE_MAPPER_XML)
				&& validFile(baseMapperXmlInfo.getPath(), baseMapperXmlInfo.getName(), XML_SUFFIX)) {
			new MyBatisBaseXmlExecuter(genConfig, baseMapperXmlInfo, daoInfo, poInfo).build(table);
		}
		fileOvervide = false;
		if (containsGenType(GenType.MAPPER_XML)
				&& validFile(mapperXmlInfo.getPath(), mapperXmlInfo.getName(), XML_SUFFIX)) {
			new MyBatisCustomXmlExecuter(genConfig, mapperXmlInfo, daoInfo).build(table);
		}

	}

}
