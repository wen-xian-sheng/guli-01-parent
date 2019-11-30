package com.guli.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.entity.ResultEntity;
import com.guli.entity.StatisticsDaily;
import com.guli.feign.UcenterFeign;
import com.guli.mapper.StatisticsDailyMapper;
import com.guli.service.StatisticsDailyService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author Helen
 * @since 2019-11-22
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterFeign ucenterFeign;

    /**
     * 生成daily记录
     * @param day
     * @return
     */
    @Override
    public ResultEntity saveStatisticsDaily(String day) {

        //根据要记录的day查找
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.likeRight("date_calculated",day);
        StatisticsDaily statisticsDailyFromDb = baseMapper.selectOne(wrapper);
        //如果数据库存在则删除
        if (statisticsDailyFromDb != null)
            baseMapper.delete(wrapper);

        //new StatisticsDaily
        StatisticsDaily statisticsDaily = new StatisticsDaily();

        //设值要统计的day
        statisticsDaily.setDateCalculated(day);

        //设值注册人数
        Integer loginNum = (Integer) ucenterFeign.countRegister("login", day).getData().get("count");
        statisticsDaily.setLoginNum(loginNum);

        //设值登录人数
        Integer registerNum = (Integer) ucenterFeign.countRegister("register", day).getData().get("count");
        statisticsDaily.setRegisterNum(registerNum);

        //设置每日播放视频数
        statisticsDaily.setVideoViewNum(RandomUtils.nextInt(100,200));

        //设置每日新增course
        statisticsDaily.setCourseNum(RandomUtils.nextInt(100,200));

        //保存
        baseMapper.insert(statisticsDaily);

        return ResultEntity.ok();
    }


    /**
     * 根据类型获取daily记录
     * @param
     * @return
     */
    @Override
    public ResultEntity getStatisticsDaily(String type, String begin, String end) {

        //声明返回值标题
        String title = "";
        //声明返回值 日期集合
        List<String> xData = new ArrayList<>();
        //声明返回值（记录数量）
        List<Integer> yData = new ArrayList<>();

        //new 查询条件
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        //被统计的日期date_calculated begin 到 end
        wrapper.between("date_calculated",begin,end);
        //要查询的字段
        wrapper.select("id","date_calculated",type);
        List<StatisticsDaily> statisticsDailieList = baseMapper.selectList(wrapper);


        //根据字段不同,取出对应的对象属性,加入yData
        switch (type){

            case "login_num":
                for (StatisticsDaily statisticsDaily : statisticsDailieList) {
                    //将集合date_calculated加入到xData
                    xData.add(statisticsDaily.getDateCalculated());
                    title = "学员登录数统计";
                    yData.add(statisticsDaily.getLoginNum());
                }
                break;

            case "register_num":
                for (StatisticsDaily statisticsDaily : statisticsDailieList) {
                    //将集合date_calculated加入到xData
                    xData.add(statisticsDaily.getDateCalculated());
                    title = "学员登录数统计";
                    yData.add(statisticsDaily.getRegisterNum());
                }
                break;

            case "video_view_num":
                for (StatisticsDaily statisticsDaily : statisticsDailieList) {
                    //将集合date_calculated加入到xData
                    xData.add(statisticsDaily.getDateCalculated());
                    title = "学员登录数统计";
                    yData.add(statisticsDaily.getVideoViewNum());
                }
                break;

            case "course_num":
                for (StatisticsDaily statisticsDaily : statisticsDailieList) {
                    //将集合date_calculated加入到xData
                    xData.add(statisticsDaily.getDateCalculated());
                    title = "学员登录数统计";
                    yData.add(statisticsDaily.getCourseNum());
                }
                break;
        }

        return ResultEntity.ok().data("title",title).data("xData",xData).data("yData",yData);
    }
}
