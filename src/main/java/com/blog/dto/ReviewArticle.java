package com.blog.dto;

import lombok.Data;

@Data
public class ReviewArticle {
    private Integer id;

    private Integer articleId;

    private String title;

    private Byte state;
}

