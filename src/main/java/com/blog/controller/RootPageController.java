package com.blog.controller;

import com.blog.dto.*;
import com.blog.entity.ArticleReport;
import com.blog.entity.ArticleReview;
import com.blog.service.ArticleReviewService;
import com.blog.service.ArtivleReportService;
import com.blog.service.ManageService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RootPageController {
    @Resource
    private ManageService manageService;

    @Resource
    private ArticleReviewService articleReportService;
    @Resource
    private ArtivleReportService artivleReportService;
    @PostMapping("/getReviewedArticle")//获取审核文章
    public List<ReviewArticle> getAuditArticle(@RequestBody ID Id) {
        return articleReportService.getReviewedArticle(Id.getId());
    }
    @PostMapping("/updateState")//更新审核状态
    public Result updateState(@RequestBody UpdateState state) {
        ArticleReview articleReport = new ArticleReview();
        articleReport.setId(state.getId());
        articleReport.setState(state.getState());
        if(articleReportService.updateArticleReport(articleReport)){
            return Result.success();
        }else{
            return Result.error("修改失败");
        }
    }
    @PostMapping("/Reviewed")//获取审核文章的审核记录
    public List<ReviewArticle> Reviewed(@RequestBody ID Id) {
        return articleReportService.Reviewed(Id.getId());
    }

    @PostMapping("/getReportArticle")//获取举报文章
    public List<ReportArticle> getReportArticle(@RequestBody ID Id) {
        return artivleReportService.getReportArticle(Id.getId());
    }

    @PostMapping("/updateReportState")//更新举报状态
    public Result updateReportState(@RequestBody UpdateState state) {
        ArticleReport articleReport = new ArticleReport();
        articleReport.setId(state.getId());
        articleReport.setState(state.getState());
        if(artivleReportService.updateArticleReport(articleReport)){
            return Result.success();
        }else{
            return Result.error("修改失败");
        }
    }
    @PostMapping("/Report")//获取举报文章的举报记录
    public List<ReportArticle> Report(@RequestBody ID Id) {
        return artivleReportService.Report(Id.getId());
    }
    @PostMapping("/getRootName")
    public String getRootName(@RequestBody ID Id) {
        return manageService.getRootById(Id.getId()).getUsername();
    }

    @PostMapping("/updateRootPwd")
    public Result updateRootPwd(@RequestBody PWD pwd) {
        if (manageService.getRootById(pwd.getId()).getPassword().equals(pwd.getOldPwd())){
            if(manageService.updatePassWord(pwd.getId(), pwd.getNewPwd())){
                return Result.success();
            }else{
                return Result.error("修改失败");
            }
        }else{
            return Result.error("旧密码错误");
        }
    }
}
