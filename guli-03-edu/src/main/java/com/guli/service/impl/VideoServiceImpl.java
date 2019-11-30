package com.guli.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.entity.Video;
import com.guli.mapper.VideoMapper;
import com.guli.service.VideoService;
import com.guli.vo.ChapterVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author Helen
 * @since 2019-11-15
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    /**
     * 根据章节id集合获取小节信息
     * @param chapterIdList
     * @return
     */
    @Override
    public Map<String, List<ChapterVo>> getVideoListByChapterIdList(List<String> chapterIdList) {

        //设置返回值map<chapterId,ChapterVo>
        Map<String,List<ChapterVo>> map = new HashMap<>();


        //封装条件
        QueryWrapper<Video> wrapper = new QueryWrapper<>();

        wrapper.in("chapter_id",chapterIdList);

        //根据chapterId集合查询video对象集合
        List<Video> videoList = baseMapper.selectList(wrapper);

        if (videoList == null)
            return map;


        for (Video video : videoList) {

            ChapterVo chapterVo = new ChapterVo();
            chapterVo.setId(video.getId());
            chapterVo.setTitle(video.getTitle());
            chapterVo.setFree(video.getFree());
            chapterVo.setVideoSourceId(video.getVideoSourceId());

            //取出chapterId对应的集合
            List<ChapterVo> chapterVoList = map.get(video.getChapterId());
            if (chapterVoList == null)
                chapterVoList = new ArrayList<>();

            //将chapterVi添加 进 chapterVoList
            chapterVoList.add(chapterVo);

            //将chapterVoList放入对应的位置
            map.put(video.getChapterId(),chapterVoList);

        }

        return map;
    }

    /**
     * 根据小节id删除videoSourceId小节信息
     * @param
     * @return
     */
    @Override
    public void deleteVideoSourceIdById(String videoId) {

        baseMapper.deleteVideoSourceIdById(videoId);

    }
}
