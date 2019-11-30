package com.guli.handler;

import com.guli.entity.ResultEntity;
import com.guli.exception.GuliException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GuliExceptionHandler {



    /**
     * 处理自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public ResultEntity guliExceptionHandler(GuliException e){

        e.printStackTrace();

        return ResultEntity.error().code(e.getCode()).message(e.getMessage());
    }


    /**
     * 处理大异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultEntity exceptionHandler(Exception e){

        e.printStackTrace();

        return ResultEntity.error();
    }

}
