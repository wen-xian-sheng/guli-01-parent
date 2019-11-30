package com.guli.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.entity.ResultEntity;
import com.guli.entity.StatisticsDaily;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author Helen
 * @since 2019-11-22
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    /**
     * 生成daily记录
     * @param day
     * @return
     */
    ResultEntity saveStatisticsDaily(String day);

    /**
     * 根据类型获取daily记录
     * @param
     * @return
     */
    ResultEntity getStatisticsDaily(String type, String begin, String end);
}
