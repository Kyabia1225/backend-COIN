package com.example.coin.service;

import com.example.coin.po.*;
import com.example.coin.util.ResponseVO;
import com.example.coin.vo.EntityVO;

import java.util.List;
import java.util.Set;

public interface QuestionService {
    String answer(String question) throws Exception;
}
