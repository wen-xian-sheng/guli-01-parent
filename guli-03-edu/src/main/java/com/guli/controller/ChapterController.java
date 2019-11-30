package com.guli.controller;


import com.guli.entity.Chapter;
import com.guli.entity.ResultEntity;
import com.guli.service.ChapterService;
import com.guli.vo.ChapterVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Helen
 * @since 2019-11-15
 */
@CrossOrigin
@Api(description = "章节管理")
@RestController
@RequestMapping("/edu/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    /**
     * 根据章节id获取该章节信息
     */
    @ApiOperation("根据章节id获取该章节信息")
    @GetMapping("get/by/id/{chapterId}")
    public ResultEntity getChapterById(@ApiParam(name = "chapterId",value = "章节id")
                                          @PathVariable("chapterId") String courseId){

        Chapter chapter = chapterService.getById(courseId);

        return ResultEntity.ok().data("chapter",chapter);
    }


    /**
     * 根据章节id删除章节信息
     */
    @ApiOperation("根据章节id删除章节信息")
    @DeleteMapping("delete/by/id/{chapterId}")
    public ResultEntity deleteChapterById(@ApiParam(name = "chapterId",value = "章节id")
                                          @PathVariable("chapterId") String courseId){

        //根据章节id获取该章节的小节

        //删除小节对应的视频（阿里云）

        //删除章节信息

        return ResultEntity.ok();
    }

    /**
     * 根据章节id修改章节信息
     */
    @ApiOperation("根据章节id修改章节信息")
    @PostMapping("update/chapter")
    public ResultEntity updateChapter(
            @ApiParam(name = "chapter",value = "章节信息")
            @RequestBody Chapter chapter
    ){
        chapterService.updateById(chapter);

        return ResultEntity.ok();
    }

    /**
     * 保存章节信息
     */
    @ApiOperation("保存章节信息")
    @PutMapping("save/chapter/{courseId}")
    public ResultEntity saveChapter(
            @ApiParam(name = "courseId",value = "课程id")
            @PathVariable("courseId") String courseId,
            //章节信息
            @ApiParam(name = "chapter",value = "章节信息")
            @RequestBody Chapter chapter
            ){

        //没有数据返回
        if (StringUtils.isBlank(chapter.getTitle()))
            return ResultEntity.error().message("没有要保存的数据");

        chapter.setCourseId(courseId);

        chapterService.save(chapter);

        return ResultEntity.ok();
    }

    /**
     * 根据courseId获取章节信息集合
     * @param courseId
     * @return
     */
    @ApiOperation("根据courseId获取章节信息集合")
    @GetMapping("get/by/course/id/{courseId}")
    public ResultEntity getChapterListInfoByCourseId(@ApiParam(name = "courseId",value = "课程id")
                                                         @PathVariable("courseId") String courseId){
        List<ChapterVo> chapterVoList = chapterService.getChapterListInfoByCourseId(courseId);

        return ResultEntity.ok().data("chapterList",chapterVoList);
    }

}

