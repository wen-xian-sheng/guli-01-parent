package com.guli.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.entity.Course;
import com.guli.entity.ResultEntity;
import com.guli.entity.Teacher;
import com.guli.service.CourseService;
import com.guli.service.TeacherService;
import com.guli.vo.TeacherVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author Helen
 * @since 2019-11-15
 */
@RestController
@RequestMapping("/edu/teacher")
@Api( description = "讲师接口")
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    /**
     *根据讲师id查找讲师个人信息
     * 及课程信息
     */
    @ApiOperation("根据讲师id查找讲师个人信息及课程信息")
    @GetMapping("get/teacher/and/course/by/id/{teacherId}")
    public ResultEntity getTeacherInfoAndCourseInfoByTeacherId(
            @ApiParam(name = "teacherId",value = "讲师的id")
            @PathVariable("teacherId") String teacherId
    ){
        //根据讲师id查找讲师个人信息
        Teacher teacher = teacherService.getById(teacherId);

        //根据teacherId查找课程信息
        List<Course> courseList = courseService.getCourseListByTeacherId(teacherId);

        return ResultEntity.ok().data("teacher",teacher).data("courseList",courseList).data("total",courseList.size());

    }


    /**
     * 前台查询讲师列表
     */
    @ApiOperation("查询讲师列表，分页")
    @GetMapping("get/page/list/{current}/{limit}")
    public ResultEntity getPageList(
            @ApiParam(name = "current",value = "当前页")//当前页
            @PathVariable("current") int current,
            @ApiParam(name = "limit",value = "限制大小")//限制大小
            @PathVariable("limit") int limit
    ){

        Page<Teacher> page = new Page<>(current, limit);

        teacherService.page(page,null);

        return ResultEntity.ok().data("data",page);
    }



    /**
     * 查询讲师集合（id,name）
     * 没有分页、条件
     */
    @ApiOperation("查询讲师列表，没有分页、条件")
    @GetMapping("query")
    public ResultEntity queryWithoutConfiditon(){

        List<Teacher> teacherList = teacherService.list(null);

        return ResultEntity.ok().data("teacherList",teacherList);
    }

    /**
     * 查询讲师列表
     * 根据条件
     * @param currentPage
     * @param size
     * @param vo
     * @return
     */
    @ApiOperation("讲师查询")
    @PostMapping("query/{currentPage}/{size}")
    public ResultEntity query(@ApiParam(name = "currentPage",value = "当前页")//当前页
                              @PathVariable("currentPage") Long currentPage,
                              @ApiParam(name = "size",value = "每页的size")//一页显示的数目
                              @PathVariable("size") Long size,
                              @ApiParam(name = "teacherVo",value = "条件",required = false)//条件
                              @RequestBody(required = false) TeacherVo vo){

        Page<Teacher> teacherPage = new Page<Teacher>(currentPage,size);

        teacherService.query(teacherPage,vo);


        return ResultEntity.ok().data("page",teacherPage.getPages()).data("total",teacherPage.getTotal()).data("rows",teacherPage.getRecords());

    }

    /**
     * 增加一个讲师信息
     */
    @ApiOperation("保存讲师信息")
    @PostMapping("save")
    public ResultEntity save(@RequestBody Teacher teacher){

        teacherService.save(teacher);

        return ResultEntity.ok();
    }

    /**
     * 逻辑删除讲师数据
     */
    @ApiOperation("删除讲师信息")
    @DeleteMapping("delete/{id}")
    public ResultEntity delete(@ApiParam(name = "id",value = "要删除的讲师id") @PathVariable("id") String id){

        teacherService.removeById(id);

        return ResultEntity.ok();
    }

    /**
     * 根据id查找讲师
     */
    @ApiOperation("根据id查找讲师信息")
    @GetMapping("get/{id}")
    public ResultEntity getById(@ApiParam(name = "id",value = "讲师id") @PathVariable("id") String id){

        Teacher teacher = teacherService.getById(id);

        return ResultEntity.ok().data("item",teacher);
    }

    /**
     * 修改讲师资料
     */
    @ApiOperation("修改讲师信息")
    @PostMapping("modify")
    public ResultEntity modify(@ApiParam(name = "teacher",value = "讲师资料") @RequestBody Teacher teacher){

        boolean b = teacherService.updateById(teacher);

        if (!b)
            return ResultEntity.error();

        return ResultEntity.ok();
    }

}

