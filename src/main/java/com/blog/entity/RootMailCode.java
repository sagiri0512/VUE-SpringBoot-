package com.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class RootMailCode {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String manageMail;
    private String mailCode;
    private java.sql.Timestamp codeTime;
}
