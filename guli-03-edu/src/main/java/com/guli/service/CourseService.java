package com.guli.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.entity.Course;
import com.guli.entity.ResultEntity;
import com.guli.vo.CourseVo;
import com.guli.vo.SearchObj;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Helen
 * @since 2019-11-15
 */
public interface CourseService extends IService<Course> {

    /**
     * 保存课程
     * @param courseVo
     * @return
     */
    ResultEntity saveCourse(CourseVo courseVo);


    /**
     * 根据courseId获取courseVo信息
     */
    ResultEntity getCourseVo(String id);


    /**
     * 根据条件查询course
     */
    ResultEntity queryWithCondition(String page, String limit, SearchObj searchObj);

    /**
     * 根据courseId删除course,courseDecription
     */
    ResultEntity removeCourseAndDecriptionById(String courseId);

    /**
     * 根据courseId获取课程发布信息
     */
    ResultEntity getCoursePublishByCourseId(String courseId);

    /**
     * 根据courseId获取课程发布信息
     */
    ResultEntity updateCourseStatus(String courseId);

    /**
     * 根据teacherId查找课程信息
     * @param teacherId
     * @return
     */
    List<Course> getCourseListByTeacherId(String teacherId);

    /**
     * 根据courseId查找课程的基本信息，章节，主讲师
     */
    Map<String, Object> getAllAboutCourseInfoByCourseId(String courseId);
}
