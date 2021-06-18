package com.example.coin.controller;

import com.example.coin.po.Entity;
import com.example.coin.service.QuestionService;
import com.example.coin.util.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coin")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @RequestMapping(path = "/question",method = RequestMethod.GET)
    public ResponseVO addEntity(@RequestParam String question)  throws Exception{
        String answer = questionService.answer(question);
        if(answer.equals(""))
            return ResponseVO.buildFailure("无法理解你的问题");
        return ResponseVO.buildSuccess(answer);
    }
}