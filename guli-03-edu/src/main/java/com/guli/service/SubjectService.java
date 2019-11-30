package com.guli.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.entity.ResultEntity;
import com.guli.entity.Subject;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Helen
 * @since 2019-11-15
 */
public interface SubjectService extends IService<Subject> {

    /**
     * 保存xls的数据
     * @param file
     * @return
     */
    ResultEntity saveXls(MultipartFile file);

    /**
     * 获取subject的数据(tree)
     */
    ResultEntity getList();
}
