package com.jt.manage.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;
import com.jt.manage.service.FileService;

@Controller
public class FileController {
	
	@Autowired
	private FileService fileService;
	
	//实现文件上传的入门案例
	//当实现文件上传后,跳转到文件上传页面
	@RequestMapping("/file")
	public String file(MultipartFile filePic) throws IllegalStateException, IOException {
		//1.获取文件的名称
		String fileName = filePic.getOriginalFilename();
		//2.准备一个文件夹
		String dir = "E:/jt-upload";
		//3.实现文件上传
		File file = new File(dir);
		//3.1如果文件夹不存在
		if(!file.exists()) {
			file.mkdirs();
		}
		filePic.transferTo
		(new File(dir+"/"+fileName));
		return "redirect:/file.jsp";
	}
	
	//实现文件上传
	// {"error":0,"url":"图片的保存路径","width":图片的宽度,"height":图片的高度}
	@RequestMapping("/pic/upload")
	@ResponseBody
	public PicUploadResult uploadFile(MultipartFile uploadFile){
		
		return fileService.uploadFile(uploadFile);
	}

}
