package com.yyf.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yyf.constant.MessageConstant;
import com.yyf.dao.AwardDao;
import com.yyf.entity.PageResult;
import com.yyf.entity.QueryPageBean;
import com.yyf.pojo.Awards;
import com.yyf.service.AwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 检查项服务
 */

@Service
@Transactional
public class AwardServiceImpl implements AwardService {
    @Autowired
    private AwardDao awardDao;

    //新增检查项
    public void add(Awards awards) {
        awardDao.add(awards);
    }

    //分页查询
    public PageResult findPage(QueryPageBean queryPageBean,int sort) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();//ThreadLocal
        if (queryString != null){
            queryString = "%"+queryString+"%";
        }
        PageHelper.startPage(currentPage,pageSize);//分页插件，会在执行sql之前将分页关键字追加到SQL后面
        Page<Awards> page = awardDao.findByCondition(queryString,sort);
        return new PageResult(page.getTotal(),page.getResult());
    }

    //根据id删除检查项
    public void delete(Integer id) {
        long count = awardDao.selectCountByAwardsId(id);
        if(count > 0){
            //已经被关联，不能删除
            throw new RuntimeException(MessageConstant.DELETE_TEACHER_AWARD_FAIL);
        }else{
            //可以删除
            awardDao.deleteById(id);
        }
    }

    public Awards findById(Integer id) {
        return awardDao.findById(id);
    }

    public void edit(Awards awards) {
        awardDao.edit(awards);
    }

    public List<Awards> findAll() {
        return awardDao.findAll();
    }

    //根据检查组id查询关联的检查项id
    public List<Integer> findAwardsIdsByTeacherUId(Integer teacherUid) {
        return awardDao.findAwardsIdsByTeacherUId(teacherUid);
    }

    @Override
    public List<Awards> findByKind(Integer kind, Integer sort) {
        return awardDao.findByKind(kind,sort);
    }
    @Override
    public List<String> findByKindUnique(Integer kind, Integer sort) {
        return awardDao.findByKindUnique(kind,sort);
    }
}