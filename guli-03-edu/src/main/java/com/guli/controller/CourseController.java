package com.guli.controller;


import com.guli.entity.ResultEntity;
import com.guli.service.ChapterService;
import com.guli.service.CourseService;
import com.guli.vo.ChapterVo;
import com.guli.vo.CourseVo;
import com.guli.vo.SearchObj;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Helen
 * @since 2019-11-15
 */
@Api(description = "课程接口")
@CrossOrigin
@RestController
@RequestMapping("/edu/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    /**
     * 根据courseId查找课程的基本信息，章节，主讲师
     */
    @ApiOperation("根据courseId查找课程的基本信息，章节，主讲师")
    @GetMapping("get/all/about/course/by/id/{courseId}")
    public ResultEntity getAllAboutCourseInfoByCourseId(@ApiParam(name = "courseId",value = "课程id")
                                                        @PathVariable("courseId") String courseId){

        Map<String,Object> map = new HashMap<>();
        //查找课程信息
        Map<String,Object> courseMap = courseService.getAllAboutCourseInfoByCourseId(courseId);

        //查找章节信息（包含小节信息）
        List<ChapterVo> chapterList = chapterService.getChapterListInfoByCourseId(courseId);

        map.put("course",courseMap);

        map.put("chapterList",chapterList);

        return ResultEntity.ok().data(map);
    }


    /**
     * 根据courseId修改课程发布信息
     */
    @ApiOperation("根据courseId修改课程发布状态")
    @PutMapping("update/status/by/id/{courseId}")
    public ResultEntity updateCourseStatus(@ApiParam(name = "courseId",value = "课程id")
                                                   @PathVariable("courseId") String courseId){
        ResultEntity resultEntity = courseService.updateCourseStatus(courseId);

        return resultEntity;
    }


    /**
     * 根据courseId获取课程发布信息
     */
    @ApiOperation("根据courseId获取课程发布信息")
    @GetMapping("get/course/publish/by/id/{courseId}")
    public ResultEntity getCoursePublishByCourseId(@ApiParam(name = "courseId",value = "课程id")
                                                   @PathVariable("courseId") String courseId){
        ResultEntity resultEntity = courseService.getCoursePublishByCourseId(courseId);

        return resultEntity;
    }


    /**
     * 根据courseId删除course,courseDecription
     */
    @ApiOperation("根据courseId删除course,courseDecription")
    @DeleteMapping("remove/{courseId}")
    public ResultEntity removeById(@ApiParam(name = "courseId",value = "要删除的课程id")
                                   @PathVariable("courseId") String courseId){

        ResultEntity resultEntity = courseService.removeCourseAndDecriptionById(courseId);

        return resultEntity;

    }

    /**
     * 保存课程
     * @param courseVo
     * @return
     */
    @ApiOperation("保存课程")
    @PutMapping("save")
    public ResultEntity saveCourse(
            @ApiParam(name = "course",value = "课程信息")
            @RequestBody CourseVo courseVo
            ){

        ResultEntity resultEntity = courseService.saveCourse(courseVo);

        return resultEntity;

    }

    /**
     * 根据courseId获取courseVo信息
     */
    @ApiOperation("根据courseId获取course信息")
    @GetMapping("get/{id}")
    public ResultEntity getCourse(
            @ApiParam(name = "id",value = "课程id")
            @PathVariable("id") String id
    ){

        ResultEntity resultEntity = courseService.getCourseVo(id);

        return resultEntity;
    }


    /**
     * 根据条件查询course
     */
    @ApiOperation("根据条件查询course")
    @PostMapping("query/{page}/{limit}")
    public ResultEntity queryWithCondition(
            //当前页数
            @ApiParam(name = "page",value = "当前页数")
            @PathVariable("page") String page,
            //没有数目
            @ApiParam(name = "limit",value = "每页总数")
            @PathVariable("limit") String limit,
            //条件
            @ApiParam(name = "searchObj",value = "查询条件",required = false)
            @RequestBody SearchObj searchObj
    ){

        ResultEntity resultEntity = courseService.queryWithCondition(page,limit,searchObj);

        return resultEntity;
    }



}

