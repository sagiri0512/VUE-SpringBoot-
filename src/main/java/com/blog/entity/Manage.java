package com.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

//设置表名
@TableName("manage")
@Data
public class Manage {
    //如果不设置主键，MybatisPlus3进行主键自增时默认使用雪花算法，会自动生成一个19位的id，我要从1开始自增
    //https://www.cnblogs.com/zhujiqian/p/15670037.html
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private Integer status;
    private String remarks;
    private Byte state;
}
