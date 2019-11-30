package com.guli.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.entity.Chapter;
import com.guli.exception.GuliException;
import com.guli.mapper.ChapterMapper;
import com.guli.myenum.ResultCodeEnum;
import com.guli.service.ChapterService;
import com.guli.service.VideoService;
import com.guli.vo.ChapterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Helen
 * @since 2019-11-15
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoService videoService;

    /**
     * 根据courseId获取章节信息集合
     * @param courseId
     * @return
     */
    @Override
    public List<ChapterVo> getChapterListInfoByCourseId(String courseId) {

        //声明返回值类型
        List<ChapterVo> chapterVoList = new ArrayList<>();


        //new QueryWrapper
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.orderByAsc("sort");

        //根据课程id获取章节集合
        List<Chapter> chapterList = baseMapper.selectList(wrapper);

        List<String> chapterIdList = new ArrayList<>();
        //遍历课程集合
        for (Chapter chapter : chapterList) {
            //取出课程id集合
            String chapterId = chapter.getId();
            //将课程id加入集合
            chapterIdList.add(chapterId);

            //封装chapterVo
            ChapterVo chapterVo = new ChapterVo();
            chapterVo.setId(chapterId);
            chapterVo.setTitle(chapter.getTitle());

            //将chapterVi加入到准备好的chapterList
            chapterVoList.add(chapterVo);
        }

        //如果章节为空，则返回error
        if (chapterIdList == null && chapterIdList.size() <= 0)
            throw new GuliException(ResultCodeEnum.NULL_POINT_ERROR);

        //根据章节id集合获取小节信息
        Map<String, List<ChapterVo>> videoMap = videoService.getVideoListByChapterIdList(chapterIdList);

        //遍历章节，认儿子
        for (ChapterVo vo : chapterVoList) {
            //取出该章节对应的小姐集合
            List<ChapterVo> videoList = videoMap.get(vo.getId());
            vo.setChildren(videoList);

        }

        return chapterVoList;
    }
}
