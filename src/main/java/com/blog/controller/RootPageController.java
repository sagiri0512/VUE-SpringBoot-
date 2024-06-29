package com.blog.controller;

import com.blog.dto.*;
import com.blog.entity.*;
import com.blog.service.ArticleReviewService;
import com.blog.service.ArtivleReportService;
import com.blog.service.ManageService;
import com.blog.service.RootMailCodeService;
import com.blog.utils.MailUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RootPageController {

    @Resource
    private EmailProperties emailProperties;
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

    //新增代码：
    @Autowired
    private ManageService manageService;
    @PostMapping("/getRootMail")//获取绑定邮箱
    public Result getRootEmil(@RequestBody ID Id) {
        try {
            return Result.success(manageService.getRootById(Id.getId()).getMail());
        }
        catch (Exception e) {
            return Result.error("获取失败");
        }
    }
    private String title = "Blog管理员操作验证码";
    @Autowired
    private RootMailCodeService rootMailCodeService;
    @PostMapping("/rootMailTo")//发送邮件验证码
    public Result rootMailTo(@RequestBody RootMail rootMail) {
        if(rootMail.getMail() == null){
            return Result.error("邮箱不能为空");
        }
        String content = "您的验证码是：";

        if(rootMailCodeService.getMailCodeByColumn("manage_mail", rootMail.getMail()) != null){
            return Result.error("重复发送");
        }
        setEmailProperties();
        String code = generatedMailCode();
        content += code;
        try {
            MailUtils.sendMail(emailProperties, rootMail.getMail(), title, content);
            try {
                rootMailCodeService.insertMailCode(code, rootMail.getMail());
                return Result.success();
            }
            catch (Exception e) {
                System.out.println(e);
                return Result.error("发送成功了，但是数据库异常");
            }
        }
        catch (Exception e) {
            return Result.error("发送失败");
        }
    }
    private void setEmailProperties(){//初始化邮箱配置
        emailProperties.setUser("sagiri0512@qq.com");
        emailProperties.setCode("mfruzdvhdabqgicg");
        emailProperties.setHost("smtp.qq.com");
        emailProperties.setAuth(true);
    }
    private String generatedMailCode() {//生成验证码
        int code = (int) ((Math.random() * 9 + 1) * 100000);
        return Integer.toString(code);
    }
    @PostMapping("/rootUpdateMail")
    public Result rootUpdateMail(@RequestBody RootUpdateMail newMail) {//修改管理员绑定邮箱
        if(manageService.getManageByCompany("mail", newMail.getMail()) != null){
            return Result.error("邮箱已被绑定");
        }
        RootMailCode mailCode = rootMailCodeService.getMailCodeByColumn("manage_mail", newMail.getMail());
        if(mailCode == null){
            return Result.error("请先发送验证码");
        }else{
            if(mailCode.getMailCode().equals(newMail.getCode())){//判断验证码
                if(manageService.updateMailById(newMail.getId(), newMail.getMail())){
                    rootMailCodeService.deleteByMail(newMail.getMail());
                    return Result.success();
                }else{
                    return Result.error("修改失败");
                }
            }else{
                return Result.error("验证码错误");
            }
        }
    }
    @PostMapping("/verifyRootMail")
    public Result verifyRootMail(@RequestBody RootUpdateMail rootUpdateMail) {//验证邮箱,用于登录和修改邮箱前的验证
        if(manageService.getManageByCompany("mail", rootUpdateMail.getMail()) == null){
            return Result.error("没有该用户");
        }
        RootMailCode mailCode = rootMailCodeService.getMailCodeByColumn("manage_mail", rootUpdateMail.getMail());
        if(mailCode == null){
            return Result.error("请先发送验证码");
        }else{
            if(mailCode.getMailCode().equals(rootUpdateMail.getCode())){//判断验证码
                rootMailCodeService.deleteByMail(rootUpdateMail.getMail());
                Manage manage = manageService.getManageByCompany("mail", rootUpdateMail.getMail());
                return Result.success(manage.getId());
            }else{
                return Result.error("验证码错误");
            }
        }
    }
}
