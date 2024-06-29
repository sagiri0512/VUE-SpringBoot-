package com.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.blog.entity.RootMailCode;
import com.blog.mapper.RootMailCodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class RootMailCodeService {
    @Autowired
    private RootMailCodeMapper rootMailCodeMapper;;
    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;
    public void insertMailCode(String code, String userMail) {
        RootMailCode rootMailCode  = new RootMailCode();
        // 设置 MailCode 对象的属性
        rootMailCode.setMailCode(code);
        rootMailCode.setManageMail(userMail);
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        rootMailCode.setCodeTime(timestamp);
        // 调用 Mapper 的 insert 方法插入数据
        rootMailCodeMapper.insert(rootMailCode);
        // 调度删除任务在5分钟后执行
        scheduleDeletion(rootMailCode, 5 * 60 * 1000); // 5分钟后删除
    }

    @Async//异步操作
    public void scheduleDeletion(RootMailCode mailCodeToDelete, long delay) {
        Date scheduledTime = new Date(mailCodeToDelete.getCodeTime().getTime() + delay);
        taskScheduler.schedule(() -> deleteByMail(mailCodeToDelete.getManageMail()), scheduledTime);//延时，在指定时间执行值方法，后项为时间
        System.out.println(taskScheduler);
    }
    public RootMailCode getMailCodeByColumn(String columnName, String columnValue) {//根据列名的值查找一行
        QueryWrapper<RootMailCode> queryWrapper = Wrappers.query();
        queryWrapper.eq(columnName, columnValue);
        return rootMailCodeMapper.selectOne(queryWrapper);
    }
    public void deleteByMail(String mail){//删除验证码
        rootMailCodeMapper.deleteByMail(mail);
    }
    public boolean isExceeding120Seconds(String mail){//判断120s内是否发送过验证码
        RootMailCode mailCode = getMailCodeByColumn("Mail", mail);
        if(mailCode == null){
            return true;
        }
        // 获取验证码发送的时间并计算120秒后的时间
        Date mailCodeTime = mailCode.getCodeTime();
        Date scheduledTime = new Date(mailCodeTime.getTime() + 120 * 1000);
        // 获取当前时间
        Date currentTime = new Date();
        // 返回当前时间是否超过了120秒后的时间
        return currentTime.after(scheduledTime);
    }
}
