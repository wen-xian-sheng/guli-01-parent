package com.guli.controller;

import com.guli.entity.ResultEntity;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@Api("user管理")
@RestController
@CrossOrigin
@RequestMapping("user")
public class UserController {

    /**
     * 1、请求登陆的login
     */
    @PostMapping("login")
    public ResultEntity login(){
        return ResultEntity.ok().data("token","admin");
    }

    /**
     * 2、根据token获取用户信息
     */
    @GetMapping("info")
    public ResultEntity info(String token){
        /**
         * {"code":20000,
         * "data":
         *  {
         *      "roles":["admin"],
         *      "name":"admin",
         *      "avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"}}
         */
        return ResultEntity.ok()
                    .data("roles","[\"admin\"]")
                    .data("name","admin")
                    .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

    @PostMapping("logout")
    public ResultEntity logout(){
        return ResultEntity.ok();
    }

}
