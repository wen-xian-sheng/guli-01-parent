package com.guli.feign;

import com.guli.entity.ResultEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("guli-03-edu")
public interface EduFeign {

    /**
     * 根据小节id修改小节信息
     * @param
     * @return
     */
    @ApiOperation(("根据小节id修改小节信息"))
    @GetMapping("/edu/video/delete/video/source/id/by/id/{videoId}")
    public ResultEntity deleteVideoSourceIdById(@ApiParam(name = "videoId",value = "根据videoId删除videoSourceId")
                               @PathVariable String videoId);
}
