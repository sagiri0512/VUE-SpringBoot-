package com.blog.service;

import com.blog.dto.ReportArticle;
import com.blog.dto.ReviewArticle;
import com.blog.entity.ArticleReport;
import com.blog.entity.ArticleReview;
import com.blog.mapper.ArtivleReportMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtivleReportService {
    @Resource
    private ArtivleReportMapper artivleReportMapper;
    public List<ReportArticle> Report(Integer manageId){//获取我管理的举报文章的管理记录
        return artivleReportMapper.Report(manageId);
    }

    public List<ReportArticle> getReportArticle(Integer manageId){//获取我管理的举报文章
        return artivleReportMapper.getReportArticle(manageId);
    }
    public boolean updateArticleReport(ArticleReport articleReport){//更新举报文章审核状态
        return artivleReportMapper.updateById(articleReport) > 0;
    }
}
