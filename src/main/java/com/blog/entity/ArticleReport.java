package com.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class ArticleReport {
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
  private Integer articleId;
  private Integer manageId;
  private String cause;
  private Byte state;
}
