package com.guli.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guli.entity.Course;

import java.util.Map;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author Helen
 * @since 2019-11-15
 */
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * 根据courseId获取课程发布信息
     */
    Map<String, Object> selectCoursePublishByCourseId(String courseId);

    /**
     * 根据courseId修改课程发布状态
     */
    void updateCourseStatus(String courseId);


    /**
     * 根据courseId查找课程的基本信息，章节，主讲师
     * @param courseId
     * @return
     */
    Map<String, Object> getAllAboutCourseInfoByCourseId(String courseId);
}
