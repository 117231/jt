package com.jt.manage.service;

import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;

public interface FileService {

	PicUploadResult uploadFile(MultipartFile uploadFile);

}
