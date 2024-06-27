package com.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
@Data
public class Article {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String title;
    private String content;
    private Integer likesNumber;
    private Integer viewsNumber;
    private java.sql.Timestamp createTime;
    private java.sql.Timestamp updateTime;
    private String status;
    private Integer createUser;
    private Integer categoryId;
    private Integer state;
    private Integer check;
}
