package com.yyf.controller;

import com.yyf.constant.MessageConstant;
import com.yyf.entity.Result;
import com.yyf.service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class TextController {
    @Autowired
    private TextService textService;
    @RequestMapping("/getInfo")
    public Result getInfo(@RequestParam("sort") Integer sort,
                          @RequestParam("kind") Integer kind){
        try{

            return new Result(true, MessageConstant.QUERY_TEXT_SUCCESS,textService.getText(kind,sort));
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_TEXT_FAIL);
        }
    }
}
