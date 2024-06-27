package com.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.blog.entity.Manage;
import com.blog.mapper.ManageMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

@Service
public class ManageService {
    @Resource
    private ManageMapper manageMapper;
    public List<Manage> getRoot(){
        QueryWrapper<Manage> queryWrapper = Wrappers.query();
        queryWrapper.eq("status", 0); // 设置查询条件
        return manageMapper.selectList(queryWrapper); // 执行查询并返回结果
    }
    public ArrayList<Integer> getAssignment() {
        return manageMapper.getAssignment();
    }
    public Manage getRootById(Integer id) {
        return manageMapper.selectById(id);
    }
    public boolean deleteRoot(Integer id) {
        return manageMapper.deleteById(id) > 0;
    }
    public boolean updatePassWord(Integer id, String password) {
        Manage manage = new Manage();
        manage.setId(id);
        manage.setPassword(password);
        return manageMapper.updateById(manage) > 0;
    }
    //修改备注
    public boolean updateRemarks(Manage manage) {
        return manageMapper.updateById(manage) > 0;
    }
    public boolean addRoot(Manage root) {
        return manageMapper.insert(root) > 0;
    }
    //模糊查询
    public List<Manage> getLikeManage(String username) {//根据姓名模糊查询
        QueryWrapper<Manage> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", username);
        return manageMapper.selectList(queryWrapper);
    }
    public Manage getManageByName(String username) {//根据姓名查询非模糊查询
        QueryWrapper<Manage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return manageMapper.selectOne(queryWrapper);
    }
}
