package com.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
//已发布未审核文章
@Data
public class AuditArticle {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String title;
}
