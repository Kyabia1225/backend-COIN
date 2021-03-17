package com.example.coin.vo;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j; //log.info()


@Data
@Slf4j
public class ResponseVO {
    @NonNull
    private Boolean success;
    private String message;
    private Object content;

    public static ResponseVO buildSuccess(){
        return new ResponseVO(true);
    }
    public static ResponseVO buildSuccess(Object content){
        ResponseVO response = new ResponseVO(true);
        response.setContent(content);
        return response;
    }
    public static ResponseVO buildFailure(String message){
        ResponseVO response = new ResponseVO(false);
        response.setMessage(message);
        log.info(message);
        return response;
    }

}