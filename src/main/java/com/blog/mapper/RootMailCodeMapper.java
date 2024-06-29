package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.entity.RootMailCode;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RootMailCodeMapper extends BaseMapper<RootMailCode> {
    @Delete("DELETE FROM root_mail_code WHERE manage_mail = #{mail}")
    boolean deleteByMail(String mail);
}
