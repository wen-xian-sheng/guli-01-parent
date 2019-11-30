package com.guli.vo;

import lombok.Data;

import java.util.List;

@Data
public class SubjectVo {

    private String id;

    private String title;

    private List<SubjectVo> children;

}
