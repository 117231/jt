package com.jt.manage.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;

@Service
public class FileServiceImpl implements FileService {
	@Value("${image.dirPath}")//从spring容器中取值
	private String dirPath;
	@Value("${image.urlPath}")
	private String urlPath;
	/**
	 * 编程:永远不要相信前台传递的数据
	 * 1.判断是否为图片 jpg|png|gif....
	 * 2.判断是否为恶意程序.
	 * 3.为了方便图片读取,一般分文件存储.
	 * 	 3.1 hash值 8位一截
	 * 	 3.2  时间分割  yyyy/MM/dd
	 * 4.应该防止图片重名 UUID
	 */
	@Override
	public PicUploadResult uploadFile(MultipartFile uploadFile) {
		PicUploadResult result = new PicUploadResult();
		//1.获取图片信息  abc.jpg
		String fileName = uploadFile.getOriginalFilename();
		//2.将字符全部小写.
		fileName = fileName.toLowerCase();
		//3.判断是否为图片类型
		if(!fileName.matches("^.+\\.(jpg|png|gif)$")) {
			//表示不是图片类型
			result.setError(1);
			return result;
		}
		/**
		 * 4.判断文件是否为恶意程序
		 */
		try {
			BufferedImage image = 
					ImageIO.read(uploadFile.getInputStream());
			int height = image.getHeight();
			int width  = image.getWidth();
			if(height==0 || width==0) {
				result.setError(1);
				return result;
			}
			
			//3.文件存储 yyyy/MM/dd
			String datePath = new SimpleDateFormat("yyyy/MM/dd")
				.format(new Date());
			//3.1创建文件夹
			File dirFile = new File(dirPath+datePath);
			if(!dirFile.exists()) {
				//如果文件不存在,则创建文件夹
				dirFile.mkdirs();
			}
			//3.2为文件生成唯一名称 UUID+后缀
			String uuid = UUID.randomUUID()
							  .toString()
							  .replace("-","");
			//图片名称  abc.jpg  截串的规则:包头不包尾
			String fileType = 
				fileName.substring(fileName.lastIndexOf("."));
			String realFileName = uuid + fileType;
			
			//4.将文件实现上传  E:/jt-upload/yyyy/MM/dd/UUID.jpg
			File realFile = new File(dirPath+datePath+"/"+realFileName);
			uploadFile.transferTo(realFile);
			/**封装返回值数据*/
			result.setHeight(height+"");
			result.setWidth(width+"");
			String realUrlPath = urlPath + datePath +"/" + realFileName;
			result.setUrl(realUrlPath);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(1);
			return result;
		}
	}
}
