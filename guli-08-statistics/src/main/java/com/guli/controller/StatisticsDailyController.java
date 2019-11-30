package com.guli.controller;


import com.guli.entity.ResultEntity;
import com.guli.service.StatisticsDailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author Helen
 * @since 2019-11-22
 */
@Api(description = "统计接口")
@RestController
@CrossOrigin
@RequestMapping("/statistics/daily")
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    /**
     * 生成daily记录
     * @param day
     * @return
     */
    @ApiOperation("生成daily记录")
    @PutMapping("create/{day}")
    public ResultEntity createDaily(@ApiParam(name = "要统计的时间")
                                    @PathVariable("day") String day){

        //保存
        ResultEntity resultEntity = statisticsDailyService.saveStatisticsDaily(day);

        return resultEntity;
    }

    /**
     * 根据类型获取daily记录
     * @param
     * @return
     */
    @ApiOperation("查询daily记录")
    @GetMapping("get/{type}/{begin}/{end}")
    public ResultEntity getDaily(
            @ApiParam(name = "要统计的类型")
            @PathVariable("type") String type,
            @ApiParam(name = "要统计的开始时间")
            @PathVariable("begin") String begin,
            @ApiParam(name = "要统计的结束时间")
            @PathVariable("end") String end){


        ResultEntity resultEntity = statisticsDailyService.getStatisticsDaily(type,begin,end);

        return resultEntity;
    }
}

