package com.yyf.service;

import com.yyf.entity.PageResult;
import com.yyf.entity.QueryPageBean;
import com.yyf.pojo.Awards;
import com.yyf.pojo.Teacher;
import com.yyf.pojo.User;

import java.util.List;

public interface TeacherService {
    public Teacher findById(int id);
    public void add(Teacher teacher);
    public PageResult findPage(QueryPageBean queryPageBean,int roleId);
    public void delete(Integer id);
    public void edit(Teacher teacher);
    public List<Teacher> findAll();
    public List<Integer> findAwardsIdsByTeacherUId(Integer TeacherUid);
    public void addCredit(Integer teacherUid,Integer credit);
    public int findRank(double credit);
    public int findByName(String name);
}
