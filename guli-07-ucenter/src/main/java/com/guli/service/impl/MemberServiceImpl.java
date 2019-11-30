package com.guli.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.entity.Member;
import com.guli.entity.ResultEntity;
import com.guli.mapper.MemberMapper;
import com.guli.service.MemberService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author Helen
 * @since 2019-11-22
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {


    /**
     * 统计统一时间注册/登录的人数
     * @param day
     * @return
     */
    @Override
    public ResultEntity countRegister(String action,String day) {

        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.select("id");
        //统计注册的人数
        if ("register".equals(action))
            wrapper.likeRight("gmt_create",day);

        else
            wrapper.likeRight("gmt_modified",day);
        Integer count = baseMapper.selectCount(wrapper);

        return ResultEntity.ok().data("count",count);
    }
}
