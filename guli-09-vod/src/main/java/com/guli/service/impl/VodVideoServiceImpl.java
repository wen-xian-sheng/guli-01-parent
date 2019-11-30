package com.guli.service.impl;


import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.guli.config.AliyunPropertiesConfig;
import com.guli.exception.GuliException;
import com.guli.myenum.ResultCodeEnum;
import com.guli.service.VodVideoService;
import com.guli.feign.EduFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class VodVideoServiceImpl implements VodVideoService {

    //获取阿里云客户端
    @Autowired
    private DefaultAcsClient client;

    //edu远程调用
    @Autowired
    private EduFeign eduFeign;

    /**
     * 视频上传
     * @param file
     * @return
     */
    @Override
    public Map<String,Object> upload(MultipartFile file) {

        //声明返回的视频资源id
        Map<String,Object> map = new HashMap<>();

        //声明上传请求
        UploadStreamRequest request = null;
        try {

            //构建请求
            request = new UploadStreamRequest(AliyunPropertiesConfig.KEY_ID,
                    AliyunPropertiesConfig.KEY_SECRET,
                    file.getName(),
                    file.getOriginalFilename(),
                    file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //声明上传实现类
        UploadVideoImpl uploader = new UploadVideoImpl();

        //上传
        UploadStreamResponse response = uploader.uploadStream(request);

        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            //成功,获取videoSourceId
            map.put("videoSourceId",response.getVideoId());

            //设置videoOriginalName
            map.put("videoOriginalName",file.getOriginalFilename());
        }


        return map;
    }


    /**
     * 根据视频资源id删除云端视频
     * 并修改数据库
     */
    @Override
    public void deleteByvideoSourceId(String videoId, String videoSourceId) {

        //获取阿里云客户端
        //构建删除请求
        DeleteVideoRequest request = new DeleteVideoRequest();
        request.setVideoIds(videoSourceId);

        try {
            //删除
            DeleteVideoResponse response = client.getAcsResponse(request);

            //调用远程服务修改小节信息，删除videoSourceId
            eduFeign.deleteVideoSourceIdById(videoId);

        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(ResultCodeEnum.VIDEO_DELETE_ALIYUN_ERROR);
        }


    }

    /**
     * 根据视频资源id获取播放凭证
     */
    @Override
    public String getPlayAuthByVideoSourceId(String videoSourceId) {
        String playAuth = "";

        try {
            //new 请求对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

            //new 响应对象
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

            //设置视频资源id
            request.setVideoId(videoSourceId);

            //客户端获取
            response = client.getAcsResponse(request);

            //获取播放凭证
            playAuth = response.getPlayAuth();

        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(ResultCodeEnum.VIDEO_NOT_EXIT_ERROR);
        }

        return playAuth;
    }
}
