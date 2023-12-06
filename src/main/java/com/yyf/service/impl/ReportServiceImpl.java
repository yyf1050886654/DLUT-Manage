package com.yyf.service.impl;

import com.yyf.dao.ReportDao;
import com.yyf.dao.TeacherDao;
import com.yyf.dao.UserDao;
import com.yyf.service.ReportService;
import com.yyf.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Transactional
@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportDao reportDao;
    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private UserDao userDao;

    @Override
    public List<Map<String, Object>> findSortCount(int teacherUid) {
        return reportDao.findSortCount(teacherUid);
    }

    @Override
    public List<Map<String, Object>> getTeacherCreditRank() {
        return reportDao.getTeacherCreditRank();
    }

    @Override
    public Map<String, Object> getReportData () throws Exception {
        //获得当前日期
        String today = DateUtils.parseDate2String(DateUtils.getToday());
        Map<String,Object> result = new HashMap<>();
        result.put("reportDate",today);
        result.put("teacherNum",userDao.getUserNum(2));
        result.put("adminNum",userDao.getUserNum(3));
        result.put("maxCredit",teacherDao.getMaxCredit());
        result.put("minCredit",teacherDao.getMinCredit());
        result.put("averageCredit",teacherDao.getAVGCredit());
        List<Double> list = reportDao.getRankList();
        int middleNum = list.size()/2;
        result.put("middleCredit",list.get(middleNum));
        System.out.println(result);
        return result;
    }
}
