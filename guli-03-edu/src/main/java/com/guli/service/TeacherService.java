package com.guli.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.entity.Teacher;
import com.guli.vo.TeacherVo;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author Helen
 * @since 2019-11-15
 */
public interface TeacherService extends IService<Teacher> {


    /**
     * 查询讲师列表
     * 根据条件
     * @param vo
     * @return
     */
    void query(Page<Teacher> teacherPage, TeacherVo vo);
}
