package com.yyf.controller;

import com.yyf.constant.MessageConstant;
import com.yyf.entity.PageResult;
import com.yyf.entity.QueryPageBean;
import com.yyf.entity.Result;
import com.yyf.pojo.Teacher;
import com.yyf.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 检查项管理
 */

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    //新增
    @RequestMapping("/add")
    public Result add(@RequestBody Teacher awards){
        try{
            teacherService.add(awards);//发送请求
            return new Result(true, MessageConstant.ADD_TEACHER_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_TEACHER_FAIL);
        }
    }

    //分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean,
                               @RequestParam("roleId")int roleId){
        return teacherService.findPage(queryPageBean,roleId);
    }

    //根据id删除检查项
    @RequestMapping("/delete")
    /*@PreAuthorize("hasAuthority('CHECKITEM_DELETE')")*/
    public Result delete(Integer id){
        try{
            teacherService.delete(id);//发送请求
            return new Result(true, MessageConstant.DELETE_TEACHER_SUCCESS);
        }catch (Exception e){
            String message = e.getMessage();
            e.printStackTrace();
            return new Result(false, message);
        }
    }

    //根据id查询检查项
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try{
            Teacher teacher = teacherService.findById(id);//发送请求
            return new Result(true, MessageConstant.QUERY_TEACHER_SUCCESS,teacher);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_TEACHER_FAIL);
        }
    }

    //编辑
    @RequestMapping("/edit")
    public Result edit(@RequestBody Teacher teacher){
        try{
            teacherService.edit(teacher);//发送请求
            return new Result(true, MessageConstant.EDIT_TEACHER_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_TEACHER_FAIL);
        }
    }

    //查询所有老师
    @RequestMapping("/findAll")
    public Result findAll(){
        try{
            List<Teacher> list = teacherService.findAll();//发送请求
            return new Result(true, MessageConstant.QUERY_TEACHER_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_TEACHER_FAIL);
        }
    }
    @RequestMapping("/findRank")
    public Result findRank(@RequestParam("Credit") double credit){
        try{
            return new Result(true, MessageConstant.QUERY_TEACHER_RANK_SUCCESS,teacherService.findRank(credit));
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_TEACHER_RANK_FAIL);
        }
    }

}
