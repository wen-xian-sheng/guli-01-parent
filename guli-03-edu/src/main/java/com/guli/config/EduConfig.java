package com.guli.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Date;

@MapperScan("com.guli.mapper")
@Configuration
@EnableTransactionManagement
public class EduConfig implements MetaObjectHandler {
    /*
    自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {

        //save时间
        setFieldValByName("gmtCreate",new Date(),metaObject);

        //修改时间
        setFieldValByName("gmtModified",new Date(),metaObject);
    }

    /*
    自动填充
     */
    @Override
    public void updateFill(MetaObject metaObject) {

        //修改时间
        setFieldValByName("gmtModified",new Date(),metaObject);

    }

    /*
    乐观锁
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor(){
        return  new OptimisticLockerInterceptor();
    }

    /*
    逻辑删除
     */
    @Bean
    public ISqlInjector sqlInjector(){
        return new LogicSqlInjector();
    }

    /*
    分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }

}
