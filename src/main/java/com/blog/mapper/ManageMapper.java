package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.entity.Manage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface ManageMapper extends BaseMapper<Manage> {
    @Select("SELECT id FROM manage WHERE status = 0")
    ArrayList<Integer> getAssignment();
}
