package com.yyf.controller;

import com.yyf.constant.MessageConstant;
import com.yyf.entity.*;
import com.yyf.pojo.Awards;
import com.yyf.service.AwardService;
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
@RequestMapping("/awards")
public class AwardController {
    @Autowired
    private AwardService awardService;
    //新增
    @RequestMapping("/add")
    public Result add(@RequestBody Awards awards){
        try{
            awardService.add(awards);//发送请求
            return new Result(true, MessageConstant.ADD_AWARDS_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_AWARD_FAIL);
        }
    }

    //分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean,@RequestParam("sort")Integer sort){
        return awardService.findPage(queryPageBean,sort);
    }

    //根据id删除检查项
    @RequestMapping("/delete")
    /*@PreAuthorize("hasAuthority('CHECKITEM_DELETE')")*/
    public Result delete(Integer id){
        try{
            awardService.delete(id);//发送请求
            return new Result(true, MessageConstant.DELETE_AWARD_SUCCESS);
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
            Awards awards = awardService.findById(id);//发送请求
            return new Result(true, MessageConstant.QUERY_AWARD_SUCCESS,awards);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_AWARD_FAIL);
        }
    }
    @RequestMapping("/findByKind")
    public Result findById(@RequestParam("kind")Integer kind, @RequestParam("sort")Integer sort){
        try{
            List<Awards> awards=awardService.findByKind(kind,sort);//发送请求
            return new Result(true, MessageConstant.QUERY_AWARD_SUCCESS,awards);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_AWARD_FAIL);
        }
    }
    //编辑
    @RequestMapping("/edit")
    public Result edit(@RequestBody Awards awards){
        try{
            awardService.edit(awards);//发送请求
            return new Result(true, MessageConstant.EDIT_AWARD_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_AWARD_FAIL);
        }
    }

    //查询所有检查项
    @RequestMapping("/findAll")
    public Result findAll(){
        try{
            List<Awards> list = awardService.findAll();//发送请求
            return new Result(true, MessageConstant.QUERY_AWARD_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_AWARD_FAIL);
        }
    }
    //根据老师uid找到他对应的奖项id
    @RequestMapping("/findAwardsIdsByTeacherUId")
    public Result findAwardsIdsByTeacherUId(Integer TeacherUid){
        try{
            List<Integer> list = awardService.findAwardsIdsByTeacherUId(TeacherUid);
            return new Result(true,MessageConstant.QUERY_AWARD_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_AWARD_FAIL);
        }
    }
    @RequestMapping("/findByKindUnique")
    public Result findByIdUnique(@RequestParam("kind")Integer kind, @RequestParam("sort")Integer sort){
        try{
            List<String> awardsName=awardService.findByKindUnique(kind,sort);//发送请求
            return new Result(true, MessageConstant.QUERY_AWARD_SUCCESS,awardsName);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_AWARD_FAIL);
        }
    }

}
