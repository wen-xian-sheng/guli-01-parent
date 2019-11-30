package com.guli.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.entity.Teacher;
import com.guli.mapper.TeacherMapper;
import com.guli.service.TeacherService;
import com.guli.vo.TeacherVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author Helen
 * @since 2019-11-15
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Override
    public void query(Page<Teacher> teacherPage, TeacherVo vo) {

        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");

        //没有条件
        if (vo == null){
            baseMapper.selectPage(teacherPage,queryWrapper);
            return ;
        }

        String name = vo.getName();
        String level = vo.getLevel();
        String gmtCreate = vo.getGmtCreate();
        String gmtModified = vo.getGmtModified();

        //名字
        if (StringUtils.isNotBlank(name))
            queryWrapper.like("name",name);

        //等级
        if (StringUtils.isNotBlank(level))
            queryWrapper.eq("level",level);

        //创建时间
        if (StringUtils.isNotBlank(gmtCreate))
            queryWrapper.ge("gmt_create",gmtCreate);

        //截止时间
        if (StringUtils.isNotBlank(gmtModified))
            queryWrapper.le("gmt_modified",gmtModified);

        baseMapper.selectPage(teacherPage,queryWrapper);
    }
}
