package com.yyf.pojo;

public class Project {
    private int awardId;
    private String teacherName;
    private String projectName;
    private String beginDate;
    private String endDate;
    private double weight;
    private String info;
    private double rankPosition;
    private double contractAmount;//合同金额

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

    public double getRankPosition() {
        return rankPosition;
    }

    public void setRankPosition(double rankPosition) {
        this.rankPosition = rankPosition;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public int getAwardId() {
        return awardId;
    }

    public void setAwardId(int awardId) {
        this.awardId = awardId;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public double getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(double contractAmount) {
        this.contractAmount = contractAmount;
    }

    @Override
    public String toString() {
        return "Project{" +
                "awardId=" + awardId +
                ", teacherName='" + teacherName + '\'' +
                ", projectName='" + projectName + '\'' +
                ", beginDate='" + beginDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", contractAmount=" + contractAmount +
                '}';
    }
}
