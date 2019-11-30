package com.guli.feign;

import com.guli.entity.ResultEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("guli-07-ucenter")
public interface UcenterFeign {

    /**
     * 统计统一时间注册/登录的人数
     * @param day
     * @return
     */
    @ApiOperation("统计day注册/登录的人数")
    @GetMapping("/ucenter/member/count/{action}/{day}")
    public ResultEntity countRegister(
            @ApiParam(name = "action",value = "register/login")
            @PathVariable("action") String action,  //登录or注册
            @ApiParam(name = "day",value = "注册/登录的时间")
            @PathVariable("day") String day //day
    );
}
