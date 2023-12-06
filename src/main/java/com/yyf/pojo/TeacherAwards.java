package com.yyf.pojo;

import java.io.Serializable;

public class TeacherAwards implements Serializable {
    private int id;
    private int awardsId;
    private int teacherUid;
    private String projectName;
    private double weight;
    private String info;
    private String beginDate;
    private String endDate;
    private Awards awards;
    private double realCredit;
    private Teacher teacher;
    private String zip;
    private double rankPosition;

    public double getRankPosition() {
        return rankPosition;
    }

    public void setRankPosition(double rankPosition) {
        this.rankPosition = rankPosition;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public double getRealCredit() {
        return realCredit;
    }

    public void setRealCredit(double realCredit) {
        this.realCredit = realCredit;
    }

    public Awards getAwards() {
        return awards;
    }

    public void setAwards(Awards awards) {
        this.awards = awards;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAwardsId() {
        return awardsId;
    }

    public void setAwardsId(int awardsId) {
        this.awardsId = awardsId;
    }

    public int getTeacherUid() {
        return teacherUid;
    }

    public void setTeacherUid(int teacherUid) {
        this.teacherUid = teacherUid;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
