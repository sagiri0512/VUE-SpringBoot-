package com.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class ArticleReview {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer articleId;
    private Integer manageId;
    private Byte state;
}
