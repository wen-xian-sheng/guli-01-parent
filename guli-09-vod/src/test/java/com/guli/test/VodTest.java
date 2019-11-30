package com.guli.test;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class VodTest {

//    初始化Acs客户端
    private static DefaultAcsClient initVodClient(String accessKeyId,String accessKeySecret){
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    //获取播放凭证
    @Test
    public void getPlayAuth() throws ClientException {
        //获取Acs客户端
        DefaultAcsClient client = initVodClient("LTAIjDanEwf9RVXP", "v23qo8tptfAscACX44QO56XedG5Owk");

        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        request.setVideoId("9c199d1fc4584eb9b816a66f20fc39ef");

        response = client.getAcsResponse(request);

        System.err.println(response.getPlayAuth());

    }

    //获取播放Url(未加密的)
    @Test
    public void getPlayUrl() throws ClientException {
        DefaultAcsClient client = initVodClient("LTAIjDanEwf9RVXP", "v23qo8tptfAscACX44QO56XedG5Owk");

        GetPlayInfoRequest request = new GetPlayInfoRequest();

        GetPlayInfoResponse response = new GetPlayInfoResponse();


        request.setVideoId("d05a3ef1662c4224bea644e1005f7d6e");

        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();

        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.err.println(playInfo.getPlayURL());
        }
    }
}
