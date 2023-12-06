package com.yyf.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ReportDao {
    public List<Map<String,Object>> findSortCount(@Param(value = "teacherUid") int teacherUid);
    public List<Map<String, Object>> getTeacherCreditRank();
    public List<Double> getRankList();
}
