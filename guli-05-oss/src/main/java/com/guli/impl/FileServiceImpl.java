package com.guli.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.guli.entity.ResultEntity;
import com.guli.exception.GuliException;
import com.guli.myenum.ResultCodeEnum;
import com.guli.service.FileService;
import com.guli.util.OSSPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public ResultEntity upload(String host,MultipartFile file) {

		/**
		 * 1、上传文件的内容判断：内容图片的属性；
		 * 2、上传图片的解析规则
		 */
		//获取文件全名
		String fileOriginalFilename = file.getOriginalFilename();
		//1、怎麽判断上传文件的格式
		String[] typeStr = {".png",".jpg",".gif",".jpeg"};
		boolean flag = false;
		for (String type : typeStr) {
			if (StringUtils.endsWithIgnoreCase(fileOriginalFilename,type)){

				flag = true;
				break;
			}
		}
		if (!flag)
			return ResultEntity.error().message("文件格式不对");

		//2、图片内容
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(file.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (bufferedImage == null)
			return ResultEntity.error().message("图片格式不对");


		//获取创建OSS所需的参数
		String endPoint = OSSPropertiesUtil.END_POINT;
		String accessKeyId = OSSPropertiesUtil.ACCESS_KEY_ID;
		String accessKeySecret = OSSPropertiesUtil.ACCESS_KEY_SECRET;
		String bucketName = OSSPropertiesUtil.BUCKET_NAME;
		//如果host不为空,存放文件夹为host(课程封面)
		String filehost = OSSPropertiesUtil.FILE_HOST;
		if (!StringUtils.isEmpty(host))
			filehost = host;

		//判断oss实例是否存在：如果不存在则创建，如果存在则获取
		OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
		try {

			if (!ossClient.doesBucketExist(bucketName)) {
				//创建bucket
				ossClient.createBucket(bucketName);
				//设置oss实例的访问权限：公共读
				ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
			}

			//获取文件流
			InputStream inputStream = file.getInputStream();

			//拼接文件路径
			//文件存储的文件夹
			String folder = new DateTime().toString("yyyy/MM/dd");
			//获取文件后缀(例 .jpg)
			String suffix = fileOriginalFilename.substring(fileOriginalFilename.lastIndexOf("."));
			//唯一文件名
			String fileName = String.valueOf(UUID.randomUUID());
			//存放路径
			String fileUrl = filehost + "/" + folder + "/" + fileName + suffix;

			//文件上传至阿里云
			ossClient.putObject(bucketName, fileUrl, inputStream);

			//访问图片url
			String uploadUrl = "https://" + bucketName + "." + endPoint + "/" + fileUrl;

			return ResultEntity.ok().message("文件上传成功").data("url", uploadUrl);

		} catch (IOException e) {
			e.printStackTrace();
			throw new GuliException(ResultCodeEnum.FIELD_IO_ERROR);
		} finally {
			ossClient.shutdown();
		}
	}
}