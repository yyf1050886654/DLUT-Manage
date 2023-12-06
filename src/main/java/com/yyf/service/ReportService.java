package com.yyf.service;

import java.util.List;
import java.util.Map;

public interface ReportService {
    public List<Map<String,Object>> findSortCount(int teacherUid);
    public List<Map<String,Object>> getTeacherCreditRank();
    public Map<String,Object> getReportData() throws Exception;
}
