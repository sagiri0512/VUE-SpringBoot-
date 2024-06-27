package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.dto.ReviewArticle;
import com.blog.entity.ArticleReview;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleReviewMapper extends BaseMapper<ArticleReview> {
    List<ReviewArticle> getReviewedArticle(Integer manageId);
    List<ReviewArticle>Reviewed(Integer manageId);
}
