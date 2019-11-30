package com.guli.entity;

import com.guli.myenum.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data

@ApiModel(value = "全局统一返回结果")
public class ResultEntity {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    private ResultEntity(){}

    /*
    成功
     */
    public static ResultEntity ok(){
        ResultEntity r = new ResultEntity();
        r.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return r;
    }

    /*
    失败
     */
    public static ResultEntity error(){
        ResultEntity r = new ResultEntity();
        r.setSuccess(ResultCodeEnum.UNKNOWN_REASON.getSuccess());
        r.setCode(ResultCodeEnum.UNKNOWN_REASON.getCode());
        r.setMessage(ResultCodeEnum.UNKNOWN_REASON.getMessage());
        return r;
    }

    public static ResultEntity setResult(ResultCodeEnum resultCodeEnum){
        ResultEntity r = new ResultEntity();
        r.setSuccess(resultCodeEnum.getSuccess());
        r.setCode(resultCodeEnum.getCode());
        r.setMessage(resultCodeEnum.getMessage());
        return r;
    }

    //设置成功/失败
    public ResultEntity success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    //设置message
    public ResultEntity message(String message){
        this.setMessage(message);
        return this;
    }

    //设置返回code
    public ResultEntity code(Integer code){
        this.setCode(code);
        return this;
    }

    //设置数据key,value
    public ResultEntity data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    //设置数据map
    public ResultEntity data(Map<String, Object> map){
        this.setData(map);
        return this;
    }

}
