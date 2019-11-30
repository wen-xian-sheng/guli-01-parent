package com.guli.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guli.entity.Video;

/**
 * <p>
 * 课程视频 Mapper 接口
 * </p>
 *
 * @author Helen
 * @since 2019-11-15
 */
public interface VideoMapper extends BaseMapper<Video> {

    //根据小节id删除videoSourceId小节信息
    void deleteVideoSourceIdById(String videoId);
}
