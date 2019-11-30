package com.guli.service;

import com.guli.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.entity.ResultEntity;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Helen
 * @since 2019-11-22
 */
public interface MemberService extends IService<Member> {

    /**
     * 统计统一时间注册/登录的人数
     * @param day
     * @return
     */
    ResultEntity countRegister(String action,String day);
}
