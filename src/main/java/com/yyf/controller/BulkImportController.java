package com.yyf.controller;

import com.yyf.constant.MessageConstant;
import com.yyf.constant.RankPosition;
import com.yyf.entity.Result;
import com.yyf.pojo.Project;
import com.yyf.pojo.Teacher;
import com.yyf.pojo.TeacherAwards;
import com.yyf.pojo.User;
import com.yyf.service.ProjectService;
import com.yyf.service.TeacherAwardsService;
import com.yyf.service.TeacherService;
import com.yyf.service.UserService;
import com.yyf.utils.POIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/bulkImport")
public class BulkImportController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TeacherAwardsService teacherAwardsService;
    @RequestMapping("/project")
    public Result project(@RequestParam("excelFile") MultipartFile excelFile){
        int row = 1;
        try {
            //读取Excel文件数据
            List<String[]> list = POIUtils.readExcel(excelFile);
            if(list != null && list.size() > 0){
                List<Project> projectList = new ArrayList<>();
                for (String[] strings : list) {
                    Project project = new Project();
                    project.setTeacherName(strings[0]);
                    project.setProjectName(strings[1]);
                    project.setRankPosition(Double.parseDouble(strings[2]));
                    project.setWeight(Double.parseDouble(strings[3]));
                    project.setAwardId(Integer.parseInt(strings[4]));
                    project.setBeginDate(strings[5]);
                    project.setEndDate(strings[6]);
                    project.setContractAmount(Double.parseDouble(strings[7]));
                    project.setInfo(strings[8]);
                    projectList.add(project);
                    row++;
                }
                projectService.add(projectList);
            }

        } catch (Exception e) {
            if (e.toString().contains("NumberFormatException")){
                return new Result(false, "excel格式有误，具体原因"+e.getMessage());
            }
            else if (e.toString().contains("ArrayIndexOutOfBoundsException")){
                return new Result(false, "excel列超出限制，具体原因："+e.getMessage());
            }
            else if (e.toString().contains("ArrayIndexOutOfBoundsException")){
                return new Result(false, "负责人姓名有误,在第"+row+"行");
            }
            else if (e.toString().contains("NullPointerException")){
                return new Result(false, e.getMessage());
            }

            else {
                return new Result(false, e.toString());
            }


        }
        return new Result(true,MessageConstant.BULK_IMPORT_SUCCESS);
    }
    @RequestMapping("/teacher")
    public Result teacher(@RequestParam("excelFile") MultipartFile excelFile){
        try {
            //读取Excel文件数据
            List<String[]> list = POIUtils.readExcel(excelFile);
            if(list != null && list.size() > 0){
                for (String[] strings : list) {
                    User user = new User();
                    Teacher teacher = new Teacher();
                    teacher.setName(strings[1]);
                    user.setTeacher(teacher);
                    user.setUsername(strings[0]);
                    user.setPassword(strings[0]);
                    userService.add(user);
                    int uid = userService.findByUsername(user.getUsername()).getId();
                    user.getTeacher().setUid(uid);
                    userService.addRole(uid,2);
                    teacherService.add(user.getTeacher());//发送请求
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.BULK_IMPORT_FAIL);
        }
        return new Result(true,MessageConstant.BULK_IMPORT_SUCCESS);
    }
    @RequestMapping("/guideStudents")
    public Result guideStudents(@RequestParam("excelFile") MultipartFile excelFile){
        int row = 1;
        try {
            //读取Excel文件数据
            List<String[]> list = POIUtils.readExcel(excelFile);
            if(list != null && list.size() > 0){
                List<TeacherAwards> teacherAwardsList = new ArrayList<>();
                for (String[] strings : list) {
                    TeacherAwards teacherAwards = new TeacherAwards();
                    teacherAwards.setTeacherUid(teacherService.findByName(strings[0]));
                    int level = Integer.parseInt(strings[1]);
                    int kind = Integer.parseInt(strings[2]);
                    int priceLevel = Integer.parseInt(strings[3]);
                    int rank = Integer.parseInt(strings[4]);
                    int ranking  = Integer.parseInt(strings[6]);
                    double realCredit = RankPosition.GUIDE_STUDENTS_LEVEL[level-1]*
                            RankPosition.GUIDE_STUDENTS_KIND[kind-1]*
                            RankPosition.GUIDE_STUDENTS_PRICELEVEL[priceLevel]*
                            RankPosition.GUIDE_STUDENTS_RANK[rank-1]*
                            RankPosition.GUIDE_STUDENTS_RANKING[ranking-1]*
                            Double.parseDouble(strings[7]);
                    teacherAwards.setRealCredit(realCredit);
                    teacherAwards.setAwardsId(94);
                    String info = strings[10]+"详情：层次：" + (level == 1?"省级":"国家级") +" 类别："
                            +(kind==1?"一类":kind ==2?"二类":"三类")+" 等级："+(priceLevel==0?"特等奖":priceLevel==1?"一等奖":priceLevel==2?"二等奖":"三等奖")+
                            " 排名："+(rank ==1?"第一名":rank == 2?"第二名":rank==3?"第三名":rank==4?"第四名":"第五名")+
                            " 指导老师排名："+((ranking == 1)?"第一指导老师":"第二指导老师");
                    teacherAwards.setInfo(info);
                    teacherAwards.setProjectName(strings[5]);
                    teacherAwards.setBeginDate(strings[8]);
                    teacherAwards.setEndDate(strings[9]);
                    teacherAwards.setAwardsId(94);
                    teacherAwards.setWeight(Double.parseDouble(strings[7]));
                    teacherAwards.setZip("");
                    teacherAwardsList.add(teacherAwards);
                    row++;
                }
                for (TeacherAwards teacherAwards:teacherAwardsList){
                    teacherAwardsService.add(teacherAwards,1);
                }
            }

        } catch (Exception e) {
            if (e.toString().contains("NumberFormatException")){
                return new Result(false, "excel格式有误，具体原因"+e.getMessage());
            }
            else if (e.toString().contains("ArrayIndexOutOfBoundsException")){
                return new Result(false, "excel列超出限制，具体原因："+e.getMessage());
            }
            else if (e.toString().contains("ArrayIndexOutOfBoundsException")){
                return new Result(false, "负责人姓名有误,在第"+row+"行");
            }
            else if (e.toString().contains("NullPointerException")){
                return new Result(false, e.getMessage());
            }

            else {
                return new Result(false, e.toString());
            }


        }
        return new Result(true,MessageConstant.BULK_IMPORT_SUCCESS);
    }

}
