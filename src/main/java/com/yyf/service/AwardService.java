package com.yyf.service;

import com.yyf.entity.PageResult;
import com.yyf.entity.QueryPageBean;
import com.yyf.pojo.Awards;

import java.util.List;

public interface AwardService {
    public void add(Awards awards);
    public PageResult findPage(QueryPageBean queryPageBean,int sort);
    public void delete(Integer id);
    public Awards findById(Integer id);
    public void edit(Awards awards);
    public List<Awards> findAll();
    public List<Integer> findAwardsIdsByTeacherUId(Integer TeacherUid);
    public List<Awards> findByKind(Integer kind, Integer sort);

    public List<String> findByKindUnique(Integer kind, Integer sort);

}
