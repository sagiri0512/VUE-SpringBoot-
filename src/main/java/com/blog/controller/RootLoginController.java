package com.blog.controller;

import com.blog.dto.Result;
import com.blog.dto.RootInfo;
import com.blog.entity.Manage;
import com.blog.service.ManageService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootLoginController {

    @Resource
    private ManageService manageService;
    @PostMapping("/rootLogin")
    public Result rootLogin(@RequestBody RootInfo rootInfo) {
        Manage manage = manageService.getManageByName(rootInfo.getUsername());
        if (manage == null) {
            return Result.error("用户名不存在");
        }else{
            if (manage.getPassword().equals(rootInfo.getPassword())) {
                if(manage.getStatus() == 1){
                    return Result.success(1);
                }else{
                    return Result.success(manage.getId());
                }
            }else{
                return Result.error("密码错误");
            }
        }
    }

}
