package com.blog.dto;

import lombok.Data;
@Data
public class ReportArticle {
    //传输举报文章信息
    private Integer id;//ArticleReport表id
    private Integer articleId;//文章id
    private String title;
    private String cause;//举报原因
    private Byte state;//状态
}
