package com.blog.utils;

import java.util.ArrayList;

//用于分配给管理员任务的单例工具类
public class AssignmentUtils {
    private static AssignmentUtils instance;
    private static ArrayList<Integer> idList;
    private static Integer num;
    private static Integer size;
    private AssignmentUtils() {
        idList = new ArrayList<>();
        num = 0;
        size = 0;
    }
    public static ArrayList<Integer> getAssignment() {
        return idList;
    }
    public static Integer getNum() {
        return num;
    }
    public static Integer getSize() {
        return size;
    }
    public static void setNum(Integer num) {
        AssignmentUtils.num ++;
    }
    public static void setSize(){
        AssignmentUtils.size = idList.size();
    }
    public static void setIdList(ArrayList<Integer> idList) {
        AssignmentUtils.idList = idList;
    }
}
