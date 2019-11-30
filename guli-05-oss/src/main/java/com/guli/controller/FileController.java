package com.guli.controller;

import com.guli.entity.ResultEntity;
import com.guli.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api("阿里云文件管理")
@CrossOrigin //跨域
@RestController
@RequestMapping("/oss/file")
public class FileController {

	@Autowired
	private FileService fileService;

	/**
	 * 文件上传
	 *
	 * @param file
	 */
	@ApiOperation(value = "文件上传")
	@PostMapping("upload")
	public ResultEntity upload(
			@ApiParam(name = "file", value = "文件", required = true)
			@RequestParam("file") MultipartFile file,
			//可选参数（文件夹，课程封面还是人物头像）
			@ApiParam(name = "host",value = "课程封面还是人物头像")
			@RequestParam(value = "host", required = false) String host ) {

		ResultEntity resultEntity = fileService.upload(host,file);
		//返回r对象
		return resultEntity;

	}
}