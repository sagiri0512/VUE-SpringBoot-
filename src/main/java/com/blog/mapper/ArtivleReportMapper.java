package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.dto.ReportArticle;
import com.blog.entity.ArticleReport;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArtivleReportMapper extends BaseMapper<ArticleReport> {
    List<ReportArticle> getReportArticle(Integer manageId);
    List<ReportArticle>Report(Integer manageId);
}
