package com.yyf.service.impl;

import com.yyf.constant.RankPosition;
import com.yyf.dao.AwardDao;
import com.yyf.dao.TeacherAwardDao;
import com.yyf.dao.TeacherDao;
import com.yyf.pojo.Awards;
import com.yyf.pojo.Project;
import com.yyf.pojo.Teacher;
import com.yyf.pojo.TeacherAwards;
import com.yyf.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private TeacherAwardDao teacherAwardDao;
    @Autowired
    private AwardDao awardDao;

    @Override
    public void add(List<Project> list) {
        int row = 1;
        List<TeacherAwards> teacherAwardsList = new ArrayList<>();
        for (Project project:list){
            TeacherAwards temp = new TeacherAwards();
            temp.setTeacherUid(teacherDao.findByName(project.getTeacherName()));
            //temp.setTeacherUid(1);
            temp.setInfo(project.getInfo());

            temp.setBeginDate(dateFormat(project.getBeginDate()));
            temp.setEndDate(dateFormat(project.getEndDate()));
            temp.setProjectName(project.getProjectName());
            temp.setWeight(project.getWeight());
            temp.setAwardsId(project.getAwardId());
            temp.setZip("");
            double position ;
                    Awards awards = awardDao.findById(project.getAwardId());
                    if (awards == null){
                        throw new NullPointerException("业绩编号错误，在第"+row+"行："+project.getAwardId());
                    }
            try {
                position = calculatePosition((int) project.getRankPosition()-1,awards.getSort(),awards.getKind());
            }catch (java.lang.ArrayIndexOutOfBoundsException e){
                String info = project.getInfo()+"\n排名超出限制，不计入分数";
                temp.setInfo(info);
                position = 0;
            }
            temp.setRankPosition(position);



            //其他项目编码为224
            if (project.getAwardId() == 224){
                temp.setRealCredit(position*temp.getWeight()*project.getContractAmount()/20);
            }
            else if (project.getAwardId() == 223){
                double tempCredit = 0;
                if (project.getContractAmount() >= 500){
                    tempCredit = project.getContractAmount()/500*10;
                }
                else if (project.getContractAmount() < 500 && project.getContractAmount() >=50){
                    tempCredit = 5;
                }
                temp.setRealCredit(tempCredit*position*temp.getWeight());
            }
            else {
                temp.setRealCredit(awardDao.findById(project.getAwardId()).getCredit()*position*temp.getWeight());
            }
            teacherAwardsList.add(temp);
            row++;
        }
        for (TeacherAwards temp:teacherAwardsList){
            teacherAwardDao.add(temp);
            teacherDao.addCredit(temp.getTeacherUid(),temp.getRealCredit());
        }


    }
    public String dateFormat(String date){
        String[] year_month_day = new String[3];
        //说明是以/分割的
        if (date.split("/").length != 1){
            year_month_day = date.split("/");
        }
        else if (date.split("\\.").length != 1){
            year_month_day = date.split("\\.");
        }
        else if (date.split("-").length != 1){
            year_month_day = date.split("-");
        }
        System.out.println(Arrays.toString(year_month_day));
        return year_month_day[0]+"-"+year_month_day[1]+"-"+year_month_day[2];
    }
    public double calculatePosition(int rankPosition,int sort,int kind){
        if (sort == 1){
            if (kind == 1 || kind == 2){
                return RankPosition.AWARDS_RANKING[rankPosition];
            }
            else if (kind == 3){
                return RankPosition.GUIDE_STUDENTS_RANKING[rankPosition];
            }
            else if (kind == 4){
                return RankPosition.RESEARCH_PROJECTS_RANKING[rankPosition];
            }
            else if (kind == 5){
                return RankPosition.WORKS_AND_TEXTBOOKS_RANKING[rankPosition];
            }
            else if (kind == 6){
                return RankPosition.PAPER_AUTHOR_RANKING[rankPosition];
            }
            else return RankPosition.COURSE_RANKING[rankPosition];
        }
        else if (sort == 2){
            if (kind == 1){
                return RankPosition.AWARDS_RANKING[rankPosition];
            }
            else if (kind >= 2 && kind <= 7){
                return RankPosition.RESEARCH_PROJECTS_RANKING[rankPosition];
            }
            else if (kind == 8 || kind == 9){
                return RankPosition.PAPER_AUTHOR_RANKING[rankPosition];
            }
            else if (kind == 10){
                return RankPosition.PATENT_RANKING[rankPosition];
            }
        }
        else if (sort == 4){
            return RankPosition.RESEARCH_PLATFORM_RANKING[rankPosition];
        }
        else return 1;

        return 1;
    }

}
