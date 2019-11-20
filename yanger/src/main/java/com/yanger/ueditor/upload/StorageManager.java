package com.yanger.ueditor.upload;

import com.yanger.common.util.CommonConstant;
import com.yanger.ueditor.define.AppInfo;
import com.yanger.ueditor.define.BaseState;
import com.yanger.ueditor.define.State;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

@Component
@ConfigurationProperties(prefix="nginx")
public class StorageManager {
	public static final int BUFFER_SIZE = 8192;
	
	private static String fileurl;

	public static String getFileurl() {
		return fileurl;
	}

	public static void setFileurl(String fileurl) {
		StorageManager.fileurl = fileurl;
	}

	public static int getBufferSize() {
		return BUFFER_SIZE;
	}

	public StorageManager() {
	}

	public static State saveBinaryFile(byte[] data, String path) {
		File file = new File(path);

		State state = valid(file);

		if (!state.isSuccess()) {
			return state;
		}

		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(file));
			bos.write(data);
			bos.flush();
			bos.close();
		} catch (IOException ioe) {
			return new BaseState(false, AppInfo.IO_ERROR);
		}

		state = new BaseState(true, file.getAbsolutePath());
		state.putInfo( "size", data.length );
		state.putInfo( "title", file.getName() );
		return state;
	}

	public static State saveFileByInputStream(HttpServletRequest request, InputStream is, String path, String picName,
			long maxSize) {
		
		State state = null;
		File tmpFile = getTmpFile();
		byte[] dataBuf = new byte[ 2048 ];

		try {
			//转成字节流
			ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
	        int rc = 0;  
	        while ((rc = is.read(dataBuf, 0, 100)) > 0) {  
	            swapStream.write(dataBuf, 0, rc);  
	        }
	        
	        dataBuf = swapStream.toByteArray();
	        swapStream.flush();
	        swapStream.close();

			if (tmpFile.length() > maxSize) {
				tmpFile.delete();
				return new BaseState(false, AppInfo.MAX_SIZE);
			}
			
			//进行存储图片
			com.yanger.common.util.FileUtils.saveFile(path, picName , dataBuf);
			
			//此处调用文件上传服务，并获取返回结果返回
//			UploadResult result = baseFileService.upload(dataBuf, picName, "OM", null);
			
			boolean success = true;
			//如果上传成功
			if (success) {
				state = new BaseState(true);
				state.putInfo( "size", tmpFile.length() );
				//文件名填入此处
				state.putInfo( "title", picName);
				//所属group填入此处
				state.putInfo( "group", "");
				//文件访问的url填入此处
				String url = "/file/getImg?path=" + path.substring(CommonConstant.FILE_PATH.length()) + "/" + picName;
				state.putInfo( "url", url);
				tmpFile.delete();
			}else{
				state = new BaseState(false, 4);
				tmpFile.delete();
			}

			return state;
			
		} catch (IOException e) {
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	public static State saveFileByInputStream(InputStream is, String path, String picName) {
		State state = null;

		File tmpFile = getTmpFile();

		byte[] dataBuf = new byte[ 2048 ];
		BufferedInputStream bis = new BufferedInputStream(is, StorageManager.BUFFER_SIZE);

		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(tmpFile), StorageManager.BUFFER_SIZE);

			int count = 0;
			while ((count = bis.read(dataBuf)) != -1) {
				bos.write(dataBuf, 0, count);
			}
			bos.flush();
			bos.close();

			//state = saveTmpFile(tmpFile, path);
			//重新将文件转成文件流的方式
//			InputStream in = new FileInputStream(tmpFile);
//			UploadUtils uploadUtils = new UploadUtils();
//			boolean success = uploadUtils.uploadFile(in, path, picName);
			boolean success = true;
			
			//如果上传成功
			if (success) {
				state = new BaseState(true);
				state.putInfo( "size", tmpFile.length() );
				state.putInfo( "title", tmpFile.getName() );
				tmpFile.delete();
			}else{
				state = new BaseState(false, 4);
				tmpFile.delete();
			}

			return state;
		} catch (IOException e) {
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	private static File getTmpFile() {
		File tmpDir = FileUtils.getTempDirectory();
		String tmpFileName = (Math.random() * 10000 + "").replace(".", "");
		return new File(tmpDir, tmpFileName);
	}

	private static State saveTmpFile(File tmpFile, String path) {
		State state = null;
		File targetFile = new File(path);

		if (targetFile.canWrite()) {
			return new BaseState(false, AppInfo.PERMISSION_DENIED);
		}
		try {
			FileUtils.moveFile(tmpFile, targetFile);
		} catch (IOException e) {
			return new BaseState(false, AppInfo.IO_ERROR);
		}

		state = new BaseState(true);
		state.putInfo( "size", targetFile.length() );
		state.putInfo( "title", targetFile.getName() );
		
		return state;
	}

	private static State valid(File file) {
		File parentPath = file.getParentFile();

		if ((!parentPath.exists()) && (!parentPath.mkdirs())) {
			return new BaseState(false, AppInfo.FAILED_CREATE_FILE);
		}

		if (!parentPath.canWrite()) {
			return new BaseState(false, AppInfo.PERMISSION_DENIED);
		}

		return new BaseState(true);
	}
}
