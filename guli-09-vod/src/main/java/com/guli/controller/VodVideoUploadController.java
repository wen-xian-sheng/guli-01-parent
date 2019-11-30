package com.guli.controller;

import com.guli.entity.ResultEntity;
import com.guli.service.VodVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Api(description = "视频接口")
@RestController
@CrossOrigin
@RequestMapping("/vod/video")
public class VodVideoUploadController {

    @Autowired
    private VodVideoService vodVideoService;


    /**
     * 根据视频资源id获取播放凭证
     */
    @ApiOperation("根据视频资源id获取播放凭证")
    @GetMapping("get/play/auth/by/video/source/id/{videoSourceId}")
    public ResultEntity getPlayAuthByVideoSourceId(@ApiParam(name = "videoSourceId",value = "加密的视频资源id")
                                                   @PathVariable("videoSourceId") String videoSourceId){

        String playAuth = vodVideoService.getPlayAuthByVideoSourceId(videoSourceId);

        return ResultEntity.ok().data("playAuth",playAuth);
    }


    /**
     * 根据视频资源id删除云端视频
     */
    @ApiOperation("根据视频资源id删除云端视频")
    @DeleteMapping("/delete/by/id/{videoId}/{videoSourceId}")
    public ResultEntity deleteByvideoSourceId(
            //videoId
            @ApiParam(name = "videoId",value = "要删除的videoId")
            @PathVariable("videoId") String videoId,
            //视频资源id
            @ApiParam(name = "videoSourceId",value = "要删除的视频资源id")
                                       @PathVariable("videoSourceId") String videoSourceId){

        vodVideoService.deleteByvideoSourceId(videoId,videoSourceId);

        return ResultEntity.ok();
    }


    /**
     * 视频上传
     * @param file
     * @return
     */
    @ApiOperation("视频上传")
    @PostMapping("/upload")
    public ResultEntity upload(@ApiParam(name = "file",value = "上传的视频")
                               MultipartFile file){

        Map<String,Object> map = vodVideoService.upload(file);

        return ResultEntity.ok().data(map);
    }


}
