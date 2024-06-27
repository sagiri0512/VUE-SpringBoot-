package com.blog.controller;

import com.blog.dto.*;
import com.blog.entity.Manage;
import com.blog.service.ManageService;
import com.blog.utils.AssignmentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SuperRootController {
    @Autowired
    private ManageService manageService;

    @PostMapping("/getManage")
    public List<Manage> getManage(){
        return manageService.getRoot();
    }
    @PostMapping("/deleteRoot")
    public Result deleteRoot(@RequestBody ID Id){
        if(manageService.deleteRoot(Id.getId())){
            return Result.success();
        }else{
            return Result.error("删除失败");
        }
    }
    @PostMapping("/updatePassWord")
    public Result updatePassWord(@RequestBody ID Id){
        if(manageService.updatePassWord(Id.getId(), "123456")){
            return Result.success();
        }else{
            return Result.error("修改失败");
        }
    }
    @PostMapping("/addRoot")
    public Result addRoot(@RequestBody Manage root){
        root.setPassword("123456");
        root.setStatus(0);
        if(manageService.addRoot(root)){
            return Result.success();
        }else{
            return Result.error("添加失败");
        }
    }
    @PostMapping("/updateRemarks")
    public Result updateRemarks(@RequestBody Manage root){
        if(manageService.updateRemarks(root)){
            return Result.success();
        }else{
            return Result.error("修改失败");
        }
    }
    @PostMapping("/getLikeManage")
    public List<Manage> getLikeManage(@RequestBody Name name){
        return manageService.getLikeManage(name.getName());
    }
    @GetMapping("/getAssignment")
    public ArrayList<Integer> getAssignment(){
        setAssignment();
        return AssignmentUtils.getAssignment();
    }

    @PostMapping("/superUpdatePass")//超级管理员修改密码
    public Result superUpdatePass(@RequestBody PWD pwd){
        Manage manage = manageService.getRootById(pwd.getId());
        if(manage.getPassword().equals(pwd.getOldPwd())){
            if(manageService.updatePassWord(pwd.getId(), pwd.getNewPwd())){
                return Result.success();
            }else{
                return Result.error("修改失败");
            }
        }else{
            return Result.error("旧密码错误");
        }
    }

    @PostMapping("/superUpdateName")
    public Result superUpdateName(@RequestBody SuperUpdateName info){
        Manage manage = manageService.getRootById(info.getId());
        if(manage.getPassword().equals(info.getPassword())){
            manage.setUsername(info.getName());
            manage.setId(info.getId());
            if(manageService.updateRemarks(manage)){
                return Result.success();
            }else{
                return Result.error("修改失败");
            }
        }else if(manage.getUsername().equals(info.getName())){
            return Result.error("新用户名和旧用户名重复");
        }else{
            return Result.error("密码错误");
        }
    }
    //初始化Assignment
    private void setAssignment(){
        if(AssignmentUtils.getAssignment() == null){
            ArrayList<Integer> idList = manageService.getAssignment();
            AssignmentUtils.setIdList(idList);
            AssignmentUtils.setSize();
        }
    }
}
