package com.guli.controller;


import com.guli.entity.ResultEntity;
import com.guli.entity.Video;
import com.guli.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author Helen
 * @since 2019-11-15
 */
@Api(description = "小节接口")
@RestController
@CrossOrigin
@RequestMapping("/edu/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    /**
     * 根据小节id删除videoSourceId小节信息
     * @param
     * @return
     */
    @ApiOperation(("根据小节id修改小节信息"))
    @GetMapping("delete/video/source/id/by/id/{videoId}")
    public ResultEntity deleteVideoSourceIdById(@ApiParam(name = "videoId",value = "根据videoId删除videoSourceId")
                                                @PathVariable String videoId){

        videoService.deleteVideoSourceIdById(videoId);

        return ResultEntity.ok();
    }


    /**
     *
     */
    /**
     * 根据小节id删除小节信息
     * @param
     * @return
     */
    @ApiOperation(("根据小节id修改小节信息"))
    @DeleteMapping("delete/by/id/{videoId}")
    public ResultEntity deleteByVideoId(@ApiParam(name = "videoId",value = "要删除的video的id")
                               @PathVariable("videoId") String videoId) {
        //删除阿里云上的视频
        videoService.removeById(videoId);

        return ResultEntity.ok();
    }

        /**
         * 根据小节id修改小节信息
         * @param video
         * @return
         */
    @ApiOperation(("根据小节id修改小节信息"))
    @PostMapping("update")
    public ResultEntity update(@ApiParam(name = "video",value = "要修改的小节信息")
                             @RequestBody Video video){

        videoService.updateById(video);

        return ResultEntity.ok();
    }


    /**
     * 根据小节id获取小节信息
     */
    @ApiOperation(("根据小节id获取小节信息"))
    @GetMapping("get/by/id/{videoId}")
    public ResultEntity getById(@ApiParam(name = "videoId",value = "小节id")
                             @PathVariable("videoId") String videoId){

        Video video = videoService.getById(videoId);

        return ResultEntity.ok().data("video",video);
    }

    /**
     * 保存video信息
     * @param video
     * @return
     */
    @ApiOperation(("保存小节信息"))
    @PutMapping("save")
    public ResultEntity save(@ApiParam(name = "video",value = "要保存的小节信息")
                             @RequestBody Video video){

        videoService.save(video);

        return ResultEntity.ok();
    }

}

