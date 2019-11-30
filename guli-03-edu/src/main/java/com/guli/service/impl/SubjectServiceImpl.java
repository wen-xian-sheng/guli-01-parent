package com.guli.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.entity.ResultEntity;
import com.guli.entity.Subject;
import com.guli.exception.GuliException;
import com.guli.mapper.SubjectMapper;
import com.guli.myenum.ResultCodeEnum;
import com.guli.service.SubjectService;
import com.guli.vo.SubjectVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Helen
 * @since 2019-11-15
 */
@Transactional
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {


    /**
     * 保存xls的数据
     * @param file
     * @return
     */
    @Override
    public ResultEntity saveXls(MultipartFile file) {

        List<String> warnMsgList = new ArrayList<>();

        try {
            //1.根据流创建hssfWorkerBook(03:hssf)
            Workbook workbook = new HSSFWorkbook(file.getInputStream());

            //2.获取表格
            Sheet sheet = workbook.getSheetAt(0);

            //3.获取最后一列的index
            int lastRowNum = sheet.getLastRowNum();
            if (lastRowNum < 1)
                return ResultEntity.ok().data("warnMsgList",warnMsgList.add("没有数据"));

            //4.判断row是否为空
            for (int rowNum = 1;rowNum <= lastRowNum;rowNum++){
                //5.判断cell是否为空
                Row row = sheet.getRow(rowNum);
                if (row == null){
                    warnMsgList.add("第" + (rowNum + 1) + "行为空");
                    continue;
                }

                //判断1列是否为空
                Cell cell1 = row.getCell(0);

                if (cell1 == null || StringUtils.isBlank( cell1.getStringCellValue().trim() ) ) {
                    warnMsgList.add("第" + (rowNum + 1) + "行第1列为空");
                    continue;
                }

                //6.判断数据库是否存在,不存在插入数据库,获取id,作为第二列parentId
                String title1 = cell1.getStringCellValue().trim();
                Subject subject1 = this.checkIsExist(title1);

                //判断2列是否为空
                Cell cell2 = row.getCell(1);
                if (cell2 == null || StringUtils.isBlank( cell2.getStringCellValue().trim() )) {
                    warnMsgList.add("第" + (rowNum + 1) + "行第2列为空");
                    continue;
                }

                //6.判断数据库是否存在,不存在才插入
                String title2 = cell2.getStringCellValue();
                String id = subject1.getId();
                Subject subject2 = this.checkIsExist(title2,id);
            }


            return ResultEntity.ok().data("warnMsgList",warnMsgList);
        } catch (IOException e) {
            e.printStackTrace();
            throw new GuliException(ResultCodeEnum.FIELD_IO_ERROR);
        }
    }


    /**
     * 获取subject的数据(tree)
     */
    @Override
    public ResultEntity getList() {
        //声明返回的变量
        List<SubjectVo> subjectVoList = new ArrayList<>();

        //查询出所有subject
        List<Subject> subjects = baseMapper.selectList(null);

        //声明map，用于存放subject  map(parentId,subjectList)
        Map<String,List<SubjectVo>> map = new HashMap<>();
        //遍历
        for (Subject subject : subjects) {

            //取出subject属性
            String id = subject.getId();
            String title = subject.getTitle();

            //声明一个subjectVo
            SubjectVo subjectVoTemp = new SubjectVo();
            //设值
            subjectVoTemp.setId(id);
            subjectVoTemp.setTitle(title);


            //parentId == "0" ,不需要认爹
            String parentId = subject.getParentId();
            if ("0".equals(parentId))            {
                //加入subjectVoList集合
                subjectVoList.add(subjectVoTemp);
                continue;
            }

            //parentId ！= "0" 则需要认爹
            //根据parentId从map取出儿子集合
            List<SubjectVo> subjectVosTemp = map.get(parentId);
            //如果儿子集合为空，就新建一个
            if (subjectVosTemp == null)
                subjectVosTemp= new ArrayList<>();
            //添加进儿子集合
            subjectVosTemp.add(subjectVoTemp);
            //将儿子集合放入map
            map.put(parentId,subjectVosTemp);
        }

        //遍历subjectVoList设置children
        for (SubjectVo subjectVo : subjectVoList) {

            //从map取出subjectVo作为son集合
            List<SubjectVo> son = map.get(subjectVo.getId());

            //如果son == null,则没有儿子，不需要认
            if (son == null)
                continue;

            //认儿子集合
            subjectVo.setChildren(son);
        }


        return ResultEntity.ok().data("subjectList",subjectVoList);
    }

    /**
     * 检查数据库是否存在，不存在则插入
     * @param title
     * @return
     */
    private Subject checkIsExist(String title, String... parentId) {

        //构建条件
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();

        if (parentId != null && parentId.length > 0)
            queryWrapper.eq("parent_id",parentId[0]);

        queryWrapper.eq("title",title);
         Subject subject= baseMapper.selectOne(queryWrapper);

        if (subject != null)
            return subject;

        subject = new Subject();
        //如果为空,插入
        subject.setTitle(title);

        subject.setParentId("0");

        if (parentId != null && parentId.length > 0)
            subject.setParentId(parentId[0]);

        baseMapper.insert(subject);

        return subject;
    }
}
