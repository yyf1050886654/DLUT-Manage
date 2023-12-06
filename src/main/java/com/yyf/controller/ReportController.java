package com.yyf.controller;

import com.yyf.constant.MessageConstant;
import com.yyf.entity.Result;
import com.yyf.service.ReportService;
import com.yyf.service.TeacherAwardsService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportService reportService;
    @Autowired
    private TeacherAwardsService teacherAwardsService;
    /**
     * 套餐占比统计
     * @return
     */
    @RequestMapping("/getPieReport")
    public Result getPieReport(@RequestParam("teacherUid") String teacherUid){
        try {
            List<Map<String, Object>> list = reportService.findSortCount(Integer.parseInt(teacherUid));

            Map<String,Object> map = new HashMap<>();
            map.put("sortCount",list);

            List<String> sortNames = new ArrayList<>();
            for(Map<String,Object> m : list){
                String sort = (String) m.get("name");
                sortNames.add(sort);
            }
            map.put("sortNames",sortNames);

            return new Result(true, MessageConstant.GET_RANK_REPORT_SUCCESS,map);
        }catch (Exception e){
            return new Result(false, MessageConstant.GET_RANK_REPORT_FAIL);
        }

    }
    @RequestMapping("getTeacherCreditRank")
    public Result getTeacherCreditRank(){
        try {
            List<Map<String, Object>> list = reportService.getTeacherCreditRank();
            Map<String, Object> map = new HashMap<>();
            List<String> teacherNames = new ArrayList<>();
            List<Object> teacherCredit = new ArrayList<>();
            for(Map<String, Object> m : list){
                String name = (String) m.get("name");
                teacherNames.add(name);
                Object credit = m.get("value");
                teacherCredit.add(credit);
            }


            for (int i=1;i<=6;i++){
                String temp = "sort";
                List<Object> suchSort = new ArrayList<>();
                for(Map<String, Object> m : list){
                    String name = (String) m.get("name");
                    suchSort.add(teacherAwardsService.addCreditBySort(name,i));
                }
                temp+=i;
                map.put(temp,suchSort);
            }
            map.put("teacherNames",teacherNames);
            map.put("teacherCredit",teacherCredit);
            return new Result(true, MessageConstant.GET_RANK_REPORT_SUCCESS,map);
        }catch (Exception e){
            return new Result(false, MessageConstant.GET_RANK_REPORT_FAIL);
        }

    }
    @RequestMapping("rankExcel")
    public Result rankExcel(HttpServletRequest request, HttpServletResponse response){
        try{
            List<Map<String, Object>> list = reportService.getTeacherCreditRank();
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("排名数据");
            //一个老师对应的所选种类的全部项目
            XSSFRow row = sheet.createRow(0);
            row.createCell(0).setCellValue("排名");
            row.createCell(1).setCellValue("姓名");
            row.createCell(2).setCellValue("总分");
            int count=1;
            for (Map<String, Object> m : list){
                XSSFRow rowTemp = sheet.createRow(count);
                rowTemp.createCell(0).setCellValue(count++);
                rowTemp.createCell(1).setCellValue((String) m.get("name"));
                rowTemp.createCell(2).setCellValue(Double.parseDouble(m.get("value").toString()));
            }
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition", "attachment;filename=rankExcel.xlsx");
            workbook.write(out);

            out.flush();
            out.close();
            workbook.close();
            return new Result(true,MessageConstant.BULK_IMPORT_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            //图片上传失败
            return new Result(false,MessageConstant.BULK_IMPORT_FAIL);
        }
    }

    /**
     * 获取运营统计数据
     * @return
     */
    @RequestMapping("/getReportData")
    public Result getReportData(){
        try {
            Map<String, Object> result = reportService.getReportData();
            return new Result(true,MessageConstant.GET_REPORT_DATA_SUCCESS,result);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_REPORT_DATA_FAIL);
        }
    }
}
