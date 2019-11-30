package com.guli.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.entity.Chapter;
import com.guli.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Helen
 * @since 2019-11-15
 */
public interface ChapterService extends IService<Chapter> {

    /**
     * 根据courseId获取章节信息集合
     * @param courseId
     * @return
     */
    List<ChapterVo> getChapterListInfoByCourseId(String courseId);
}
