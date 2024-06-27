package com.blog.service;

import com.blog.dto.ReviewArticle;
import com.blog.entity.ArticleReview;
import com.blog.mapper.ArticleReviewMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleReviewService {
    @Resource
    private ArticleReviewMapper articleReportMapper;
    public List<ReviewArticle> getReviewedArticle(Integer manageId) {
        return articleReportMapper.getReviewedArticle(manageId);
    }
    public boolean updateArticleReport(ArticleReview articleReport) {
        return articleReportMapper.updateById(articleReport) > 0;
    }
    //获取已经审核的记录
    public List<ReviewArticle> Reviewed(Integer manageId) {
        return articleReportMapper.Reviewed(manageId);
    }
}
