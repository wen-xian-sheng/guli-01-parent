package com.guli.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AliyunPropertiesConfig implements InitializingBean {

    @Value("${aliyun.vod.file.keyid}")
    private String keyId;

    @Value("${aliyun.vod.file.keysecret}")
    private String keySecret;

    public static String KEY_ID ;

    public static String KEY_SECRET ;

    private DefaultAcsClient client;

    @Override
    public void afterPropertiesSet() throws Exception {

        KEY_ID = keyId;

        KEY_SECRET = keySecret;

    }

    //初始化Acs客户端
    @Bean
    public DefaultAcsClient initVodClient(){
        //如果化Acs客户端不为空，则直接返回client
        if (this.client != null)
            return  this.client;

        //如果化Acs客户端为空，初始化一个客户端（单例）
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, KEY_ID, KEY_SECRET);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }
}
