package com.guli.service;

import com.guli.entity.ResultEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    /**
     * 文件上传至阿里云
     * @param file
     * @return
     */
    ResultEntity upload(String host,MultipartFile file);
}
