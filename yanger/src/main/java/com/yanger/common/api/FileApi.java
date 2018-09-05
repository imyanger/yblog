package com.yanger.common.api;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yanger.common.util.ConstantUtils;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

/*
 * 操作文件的类
 */
@Controller
@RequestMapping("/file")
public class FileApi {
	
	/**
	 * <p>Description: 显示原文件（图片）</p>  
	 * @author YangHao  
	 * @date 2018年9月3日-下午10:46:18
	 * @param path
	 * @param response
	 */
	@RequestMapping(value="/getImg")
	public void getImg(String path,HttpServletResponse response){
		//输入输出流
		FileInputStream fis = null;  
		OutputStream os = null;
		//组织完整的文件路径
		path = new StringBuilder(ConstantUtils.FILE_PATH).append(path).toString();
		try {  
			//File file = new File(url);
			fis = new FileInputStream(path);  
			os = response.getOutputStream();  
			int count = 0;  
			byte[] buffer = new byte[1024 * 8];  
			while ((count = fis.read(buffer)) != -1) {  
				os.write(buffer, 0, count);  
				os.flush();  
			}  
		} catch (Exception e) {  
			e.printStackTrace();  
		}  
	}
	
	/**
	 * <p>Description: 显示图片的缩略图  使用thumbnailator插件 </p>  
	 * @author YangHao  
	 * @date 2018年9月3日-下午10:46:44
	 * @param path 路径
	 * @param wh 1.不传入，使用默认的100px*100px 2.只传入高度，格式：200，高度根据比例得出   3.宽和高都传入  格式：100-100
	 * @param response
	 */
	@RequestMapping(value="/thumbImg")
	public void thumbImg(String path,String wh,HttpServletResponse response){
		
		//缩略图
		BufferedImage thumbnail = null;
		//需要压缩的尺寸，默认20*20
		Integer width = 100;  
		Integer height = 100;
		//组织完整的文件路径
		path = new StringBuilder(ConstantUtils.FILE_PATH).append(path).toString();
		try {
			
			//获取原尺寸的图片
			BufferedImage image = ImageIO.read(new FileInputStream(path));
			//读取原始图片的宽高
			Integer widthOrig = image.getWidth();  
			Integer heightOrig = image.getHeight();
			
			//判断是否传入了需要压缩的尺寸
			if(wh == null ||  //没有传入压缩尺寸
					(StringUtils.isNotBlank(wh) && wh.indexOf("-") > -1)) {	 //传入了完整的需要压缩的宽高，eg：100-100
				
				//传入了宽高则使用出入的宽高
				if(wh != null){
					//需要压缩的宽度
					width = Integer.parseInt(wh.split("-")[0]);
					//需要压缩的高度
					height = Integer.parseInt(wh.split("-")[1]);
				}
				
				//图片 宽高比例
				Float ratioOrig = (float)widthOrig/heightOrig;
				Float ratio = (float)width/height;
				
				//根据尺寸进行压缩，若原图片比例与压缩比例不一致，不损坏原图的情况下，则将相应的缩小尺寸
				if(ratioOrig == ratio){
					//获取缩略图
					thumbnail = Thumbnails.of(image).forceSize(width, height).asBufferedImage();
				}else{
					if(ratioOrig > ratio){
						thumbnail = Thumbnails.of(image).width(width).asBufferedImage();  
					}else{
						thumbnail = Thumbnails.of(image).height(height).asBufferedImage();
					}
					//以中心按压缩比例进行裁剪，这样则一定会得到要求尺寸的图片，但是会损坏原有的图片
					//thumbnail = Thumbnails.of(image).sourceRegion(Positions.CENTER, width, height).size(width,height).asBufferedImage();  
				} 
				
			}else {  //只传入了高度-方便页面设置高度
				
				height = Integer.parseInt(wh);
				//只传入高度时，宽度根据高度压缩比例得到
				width = widthOrig * height/heightOrig;
				thumbnail = Thumbnails.of(image).forceSize(width, height).asBufferedImage();
				
			}
			
			//输出流
			OutputStream os = response.getOutputStream();
			//将缩略图写入输入流，JPEG格式
			ImageIO.write(thumbnail, "JPEG", os);
			os.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>Description: 显示图片的缩略图  使用thumbnailator插件,图片规格不符合将会以中心进行裁剪 </p>  
	 * @author YangHao  
	 * @date 2018年9月3日-下午10:47:27
	 * @param path 图片路径
	 * @param wh 1.不传入，使用默认的100px*100px 2.只传入高度，格式：200，高度根据比例得出   3.宽和高都传入  格式：100-100
	 * @param response
	 */
	@RequestMapping(value="/cutImg")
	public void cutImg(String path,String wh,HttpServletResponse response){
		
		//缩略图
		BufferedImage thumbnail = null;
		//需要压缩的尺寸，默认20*20
		Integer width = 100;  
		Integer height = 100;
		//组织完整的文件路径
		path = new StringBuilder(ConstantUtils.FILE_PATH).append(path).toString();
		try {
			
			//获取原尺寸的图片
			BufferedImage image = ImageIO.read(new FileInputStream(path));
			//读取原始图片的宽高
			Integer widthOrig = image.getWidth();  
			Integer heightOrig = image.getHeight();
			
			//判断是否传入了需要压缩的尺寸
			if(wh == null ||  //没有传入压缩尺寸
					(StringUtils.isNotBlank(wh) && wh.indexOf("-") > -1)) {	 //传入了完整的需要压缩的宽高，eg：100-100
				
				//传入了宽高则使用出入的宽高
				if(wh != null){
					//需要压缩的宽度
					width = Integer.parseInt(wh.split("-")[0]);
					//需要压缩的高度
					height = Integer.parseInt(wh.split("-")[1]);
				}
				
				//图片 宽高比例
				Float ratioOrig = (float)widthOrig/heightOrig;
				Float ratio = (float)width/height;
				
				//根据尺寸进行压缩，若原图片比例与压缩比例不一致，不损坏原图的情况下，则将相应的缩小尺寸
				if(Math.abs(ratioOrig-ratio) <= 0){
					//获取缩略图
					thumbnail = Thumbnails.of(image).forceSize(width, height).asBufferedImage();
				}else{
					if(ratioOrig > ratio){
						thumbnail = Thumbnails.of(image).width(width).asBufferedImage();  
					}else{
						thumbnail = Thumbnails.of(image).height(height).asBufferedImage();
					}
					//以中心按压缩比例进行裁剪，这样则一定会得到要求尺寸的图片，但是会损坏原有的图片
					thumbnail = Thumbnails.of(image).sourceRegion(Positions.CENTER, width, height).size(width,height).asBufferedImage();  
				} 
				
			}else {  //只传入了高度-方便页面设置高度
				
				height = Integer.parseInt(wh);
				//只传入高度时，宽度根据高度压缩比例得到
				width = widthOrig * height/heightOrig;
				thumbnail = Thumbnails.of(image).forceSize(width, height).asBufferedImage();
				
			}
			
			//输出流
			OutputStream os = response.getOutputStream();
			//将缩略图写入输入流，JPEG格式
			ImageIO.write(thumbnail, "JPEG", os);
			os.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
