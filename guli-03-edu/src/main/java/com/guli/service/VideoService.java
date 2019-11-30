package com.guli.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.entity.Video;
import com.guli.vo.ChapterVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author Helen
 * @since 2019-11-15
 */
public interface VideoService extends IService<Video> {

    /**
     * 根据章节id集合获取小节信息
     * @param chapterIdList
     * @return
     */
    Map<String, List<ChapterVo>> getVideoListByChapterIdList(List<String> chapterIdList);

    //根据小节id删除videoSourceId小节信息
    void deleteVideoSourceIdById(String videoId);
}
