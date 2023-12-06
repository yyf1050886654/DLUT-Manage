package com.yyf.dao;

import com.github.pagehelper.Page;
import com.yyf.pojo.Awards;
import com.yyf.pojo.TeacherAwards;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface TeacherAwardDao {
    public Page<TeacherAwards> findByCondition(@Param(value = "value")String queryString,
                                               @Param(value = "sort")int sort,
                                               @Param(value = "teacherUid") int teacherUid);
    public Page<TeacherAwards> findByCondition4All(@Param(value = "value")String queryString,
                                               @Param(value = "sort")int sort);
    public void add(TeacherAwards teacherAwards);
    public void deleteById(Integer id);
    public Page<TeacherAwards> manageFindByCondition(@Param(value = "value")String queryString);
    public String findZipNameById(@Param(value = "id") int id);
    public List<TeacherAwards> findAwardsByTeacherAndSorts(@Param(value = "sort")int sort,
                                                           @Param(value = "teacherUid")int teacherUid);
    public String sortXSum(@Param(value = "sort")int sort,
                           @Param(value = "teacherUid") int teacherUid);
    public TeacherAwards findById(Integer id);
    public void edit(TeacherAwards awards);
}