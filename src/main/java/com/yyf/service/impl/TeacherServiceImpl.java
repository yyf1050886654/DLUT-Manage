package com.yyf.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yyf.constant.MessageConstant;
import com.yyf.dao.TeacherDao;
import com.yyf.dao.UserDao;
import com.yyf.entity.PageResult;
import com.yyf.entity.QueryPageBean;
import com.yyf.pojo.Teacher;
import com.yyf.pojo.User;
import com.yyf.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private UserDao userDao;
    @Override
    public Teacher findById(int id) {
        Teacher temp = teacherDao.findById(id);
        return temp;
    }
    //新增检查项
    public void add(Teacher teacher) {
        if (teacher.getTelephone() == null){
            teacher.setTelephone("无");
        }
        teacherDao.add(teacher);
    }

    //分页查询
    public PageResult findPage(QueryPageBean queryPageBean,int adminKind) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();//ThreadLocal
        if (queryString != null){
            queryString = "%"+queryString+"%";
        }
        PageHelper.startPage(currentPage,pageSize);//分页插件，会在执行sql之前将分页关键字追加到SQL后面
        Page<User> page = userDao.findByCondition(queryString,adminKind);
        List<User> userList = page.getResult();
        for (User user:userList){
            user.setTeacher(teacherDao.findById(user.getId()));
        }
        return new PageResult(page.getTotal(),userList);
    }

    //根据id删除检查项
    public void delete(Integer id) {
        long count = teacherDao.selectCountByTeacherUid(id);
        if(count > 0){
            //已经被关联，不能删除
            throw new RuntimeException(MessageConstant.DELETE_TEACHER_AWARD_FAIL);
        }else{
            //可以删除
            teacherDao.deleteById(id);
        }
    }


    public void edit(Teacher teacher) {
        teacherDao.edit(teacher);
    }

    public List<Teacher> findAll() {
        return teacherDao.findAll();
    }

    //根据检查组id查询关联的检查项id
    public List<Integer> findAwardsIdsByTeacherUId(Integer teacherUid) {
        return teacherDao.findAwardsIdsByTeacherUId(teacherUid);
    }

    @Override
    public void addCredit(Integer teacherUid, Integer credit) {
        teacherDao.addCredit(teacherUid,credit);
    }

    @Override
    public int findRank(double credit) {
        return teacherDao.findRank(credit)+1;
    }

    @Override
    public int findByName(String name) {
        return teacherDao.findByName(name);
    }
}
