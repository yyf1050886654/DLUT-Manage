package com.yyf.service;

import com.yyf.entity.PageResult;
import com.yyf.entity.QueryPageBean;
import com.yyf.pojo.Awards;
import com.yyf.pojo.Teacher;
import com.yyf.pojo.TeacherAwards;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface TeacherAwardsService {
    public PageResult findPage(QueryPageBean queryPageBean,int sort,int teacherUid) throws UnsupportedEncodingException;

    public PageResult findPage4All(QueryPageBean queryPageBean,int sort) throws UnsupportedEncodingException;
    public PageResult manageFindPage(QueryPageBean queryPageBean);
    public List<TeacherAwards> findAwardsByTeacherAndSorts(List<Integer> sorts,int teacherUid);
    public boolean add(TeacherAwards teacherAwards,int sort);
    public void delete(Integer id,double credit,Integer teacherUid);
    public double addCreditBySort(String teacherName,int sort);
    public TeacherAwards findById(Integer id);
    public void edit(TeacherAwards teacherAwards);
}
