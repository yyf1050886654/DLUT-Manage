package com.yyf.controller;

import com.yyf.constant.MessageConstant;
import com.yyf.entity.Result;
import com.yyf.pojo.Menu;
import com.yyf.pojo.Teacher;
import com.yyf.pojo.User;
import com.yyf.service.MenuService;
import com.yyf.service.TeacherService;
import com.yyf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private MenuService menuService;

    //获取当前登录用户的用户名
    @RequestMapping("/getTeacher")
    public Result getTeacher()throws Exception{
        try{
            org.springframework.security.core.userdetails.User user =
                    (org.springframework.security.core.userdetails.User)
                    SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            com.yyf.pojo.User thisUser = userService.findByUsername(user.getUsername());
            Teacher currentTeacher = teacherService.findById(thisUser.getId());
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,currentTeacher);
        }catch (Exception e){
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }
    @RequestMapping("/getAllTeacher")
    public Result getAllTeacher()throws Exception{
        try{
            return new Result(true, MessageConstant.QUERY_ALL_TEACHER_SUCCESS,teacherService.findAll());
        }catch (Exception e){
            return new Result(false, MessageConstant.QUERY_ALL_TEACHER_SUCCESS);
        }
    }
    @RequestMapping("/getMenu")
    public Result getMenu()throws Exception{
        try{
            org.springframework.security.core.userdetails.User user =
                    (org.springframework.security.core.userdetails.User)
                            SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            com.yyf.pojo.User thisUser = userService.findByUsername(user.getUsername());
            List<Menu> menus = menuService.getMenuByRoles(thisUser.getRoles());
            return new Result(true, MessageConstant.GET_MENU_SUCCESS,menus);
        }catch (Exception e){
            return new Result(false, MessageConstant.GET_MENU_FAIL,e.getMessage());
        }
    }
    @RequestMapping("/register")
    public Result register(@RequestBody User user,@RequestParam("roleId")int roleId)throws Exception{
        try{
            userService.add(user);
            int uid = userService.findByUsername(user.getUsername()).getId();
            user.getTeacher().setUid(uid);
            userService.addRole(uid,roleId);
            teacherService.add(user.getTeacher());//发送请求
            return new Result(true, MessageConstant.ADD_TEACHER_AWARDS_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_TEACHER_AWARD_FAIL);
        }
    }
    @RequestMapping("/delete")
    public Result delete(String id)throws Exception{
        int temp = Integer.parseInt(id);
        try{
            teacherService.delete(temp);//发送请求
            userService.deleteRole(temp);
            userService.delete(temp);
            return new Result(true, MessageConstant.DELETE_USER_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_USER_FAIL);
        }
    }
    @RequestMapping("/changePassword")
    public Result changePassword(@RequestParam("psw") String password,
                                 @RequestParam("id") Integer id){
        try{
            userService.changePassword(password,id);
            return new Result(true, MessageConstant.CHANGE_PASSWORD_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.CHANGE_PASSWORD_FAIL);
        }
    }

}