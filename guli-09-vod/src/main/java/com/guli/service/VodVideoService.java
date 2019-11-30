package com.guli.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface VodVideoService {

    /**
     * 视频上传
     * @param file
     * @return
     */
    Map<String,Object> upload(MultipartFile file);

    /**
     * 根据视频资源id删除云端视频
     */
    void deleteByvideoSourceId(String videoId, String videoSourceId);

    /**
     * 根据视频资源id获取播放凭证
     */
    String getPlayAuthByVideoSourceId(String videoSourceId);
}
