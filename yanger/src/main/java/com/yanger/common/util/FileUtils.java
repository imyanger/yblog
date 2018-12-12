package com.yanger.common.util;

import java.io.File;
import java.io.IOException;

import javax.imageio.stream.FileImageOutputStream;

/**
 * @description 文件处理工具类
 * @author 杨号
 * @date 2018年9月28日
 */
public class FileUtils {

	/**
	 * @description byte转file
	 * @author 杨号
	 * @date 2018年9月28日-上午10:42:57
	 * @param file
	 * @param data
	 * @throws IOException
	 */
	public static void saveFile(String path, String name, byte[] data) throws IOException {
		File targetFile = new File(path);
		// 进行存储图片
		if (!targetFile.mkdirs()) {
			targetFile.mkdirs();
		}
		targetFile.setWritable(true, false);
		File file = new File(targetFile, name);
		// 打开输入流
		FileImageOutputStream imageOutput = new FileImageOutputStream(file);
		// 将byte写入硬盘
		imageOutput.write(data, 0, data.length);
		imageOutput.close();
	}

}
