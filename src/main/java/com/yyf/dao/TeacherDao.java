package com.yyf.dao;

import com.github.pagehelper.Page;
import com.yyf.pojo.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeacherDao {
    public Teacher findById(int id);
    public void add(Teacher teacher);
    public Page<Teacher> findByCondition(@Param(value = "value")String queryString);
    public long selectCountByTeacherUid(Integer teacherUid);
    public void deleteById(Integer id);
    public void edit(Teacher teacher);
    public List<Teacher> findAll();
    public List<Integer> findAwardsIdsByTeacherUId(Integer teacherUid);
    public void addCredit(@Param(value = "uid") int uid,@Param(value = "credit") double credit);
    public void deleteCredit(@Param(value = "uid") int uid,@Param(value = "credit") double credit);
    public int findByName(String name);
    public int findRank(double Credit);
    public double getMaxCredit();
    public double getMinCredit();
    public double getAVGCredit();
}
