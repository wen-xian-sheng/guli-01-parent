package com.guli.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.entity.Course;
import com.guli.entity.CourseDescription;
import com.guli.entity.ResultEntity;
import com.guli.mapper.CourseMapper;
import com.guli.service.CourseDescriptionService;
import com.guli.service.CourseService;
import com.guli.vo.CourseVo;
import com.guli.vo.SearchObj;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Helen
 * @since 2019-11-15
 */
@Service
@Transactional
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {


    @Autowired
    private CourseDescriptionService courseDescriptionService;

    /**
     * 保存课程
     * @param courseVo
     * @return
     */
    @Override
    public ResultEntity saveCourse(CourseVo courseVo) {

        String courseId = courseVo.getId();
        //如果有id则更改
        String operation = "save";
        if (StringUtils.isNotBlank(courseId))
            operation = "update";

        //构建保存到数据库的对象 course
        Course course = new Course();
        BeanUtils.copyProperties(courseVo,course);


        //取出课程简介
        String description = courseVo.getDescription();
        //构建课程描述 对像
        CourseDescription courseDescription = new CourseDescription();

        courseDescription.setDescription(description);


        if ("save".equals(operation)){

            //保存source
            baseMapper.insert(course);

            courseId = course.getId();

            //保存课程描述(CourseDescription)
            courseDescription.setId(courseId);
            courseDescriptionService.save(courseDescription);
        }

        if ("update".equals(operation)){

            //修改
            baseMapper.updateById(course);

            courseDescription.setId(courseId);
            courseDescriptionService.updateById(courseDescription);

        }




        return ResultEntity.ok().data("courseId",courseId);
    }


    /**
     * 根据courseId获取courseVo信息
     */
    @Override
    public ResultEntity getCourseVo(String id) {

        //获取cource信息
        Course course = baseMapper.selectById(id);

        //获取课程描述信息
        CourseDescription description = courseDescriptionService.getById(id);

        //new courseVo 并赋值
        CourseVo courseVo = new CourseVo();
        BeanUtils.copyProperties(course,courseVo);

        //设置courseVo的description属性
        //如果有描述信息才设置
        if (description != null)
            courseVo.setDescription(description.getDescription());

        return ResultEntity.ok().data("course",courseVo);
    }


    /**
     * 根据条件查询course
     */
    @Override
    public ResultEntity queryWithCondition(String page, String limit, SearchObj searchObj) {

        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        //封装subjectParentId条件
        if (StringUtils.isNotBlank(searchObj.getSubjectParentId()))
            wrapper.eq("subject_parent_id",searchObj.getSubjectParentId());

        //封装subjectId条件
        if (StringUtils.isNotBlank(searchObj.getSubjectId()))
            wrapper.eq("subject_id",searchObj.getSubjectId());

        //封装title条件
        if (StringUtils.isNotBlank(searchObj.getTitle()))
            wrapper.eq("title",searchObj.getTitle());

        //封装teacherId条件
        if (StringUtils.isNotBlank(searchObj.getTeacherId()))
            wrapper.eq("teacher_id",searchObj.getTeacherId());

        //设置排序
        wrapper.orderByDesc("gmt_modified");

        Page<Course> coursePage = new Page<>();
        //设置当前页
        coursePage.setCurrent(Long.valueOf(page));
        //设置每页数目
        coursePage.setSize(Long.valueOf(limit));

        //查询
        baseMapper.selectPage(coursePage,wrapper);

        //取出total
        long total = coursePage.getTotal();

        //取出list
        List<Course> list = coursePage.getRecords();

        return ResultEntity.ok().data("total",total).data("page",coursePage).data("list",list);
    }

    /**
     * 根据courseId删除course,courseDecription
     */
    @Override
    public ResultEntity removeCourseAndDecriptionById(String courseId) {

        //根据courseId删除course
        baseMapper.deleteById(courseId);

        //根据courseId删除courseDecription
        courseDescriptionService.removeById(courseId);

        return ResultEntity.ok();
    }

    /**
     * 根据courseId获取课程发布信息
     */
    @Override
    public ResultEntity getCoursePublishByCourseId(String courseId) {

        Map<String,Object> map = baseMapper.selectCoursePublishByCourseId(courseId);

        return ResultEntity.ok().data(map);
    }

    /**
     * 根据courseId修改课程发布状态
     */
    @Override
    public ResultEntity updateCourseStatus(String courseId) {

        baseMapper.updateCourseStatus(courseId);

        return ResultEntity.ok();
    }

    /**
     * 根据teacherId查找课程信息
     * @param teacherId
     * @return
     */
    @Override
    public List<Course> getCourseListByTeacherId(String teacherId) {

        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        wrapper.eq("status","Normal");

        List<Course> courseList = baseMapper.selectList(wrapper);

        return courseList;
    }


    /**
     * 根据courseId查找课程的基本信息，章节，主讲师
     */
    @Override
    public Map<String, Object> getAllAboutCourseInfoByCourseId(String courseId) {

        Map<String,Object> map = baseMapper.getAllAboutCourseInfoByCourseId(courseId);

        return map;
    }
}
