package com.guli.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeacherVo {

    private String id;

    @ApiModelProperty(value = "讲师姓名")
    private String name;


    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    private String level;


    @ApiModelProperty(value = "创建时间")
    private String gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private String gmtModified;

}
