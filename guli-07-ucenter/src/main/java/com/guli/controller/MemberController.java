package com.guli.controller;


import com.guli.entity.ResultEntity;
import com.guli.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author Helen
 * @since 2019-11-22
 */
@Api( description = "用户中心管理")
@RestController
@RequestMapping("/ucenter/member")
@CrossOrigin
public class MemberController {

    @Autowired
    private MemberService memberService;

    /**
     * 统计统一时间注册/登录的人数
     * @param day
     * @return
     */
    @ApiOperation("统计day注册/登录的人数")
    @GetMapping("count/{action}/{day}")
    public ResultEntity countRegister(
            @ApiParam(name = "action",value = "register/login")
            @PathVariable("action") String action,  //登录or注册
            @ApiParam(name = "day",value = "注册/登录的时间")
            @PathVariable("day") String day //day
    ){

        ResultEntity resultEntity = memberService.countRegister(action,day);

        return resultEntity;
    }



}

