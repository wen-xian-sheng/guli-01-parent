package com.guli.controller;


import com.guli.entity.ResultEntity;
import com.guli.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author Helen
 * @since 2019-11-15
 */
@RestController
@CrossOrigin
@RequestMapping("/edu/subject")
@Api( description = "课程分类接口")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    /**
     * 保存xls(03)表格的内容
     * @param file
     * @return
     */
    @ApiOperation("保存xls内容")
    @PostMapping("import")
    public ResultEntity importXls(MultipartFile file){

        ResultEntity resultEntity = subjectService.saveXls(file);

        return resultEntity;
    }


    /**
     * 获取subject的数据(tree)
     */
    @ApiOperation("获取subject的列表（tree）")
    @GetMapping("get/list")
    public ResultEntity getList(){

        ResultEntity resultEntity = subjectService.getList();

        return resultEntity;
    }


}

