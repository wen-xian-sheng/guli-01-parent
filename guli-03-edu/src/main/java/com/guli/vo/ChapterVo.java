package com.guli.vo;

import lombok.Data;

import java.util.List;

@Data
public class ChapterVo {

    private String id;

    private String title;

    private boolean free;

    private String videoSourceId;

    private List<ChapterVo> children;

}
