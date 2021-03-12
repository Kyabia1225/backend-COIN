package com.example.coin.controller;

import com.example.coin.pojo.Entity;
import com.example.coin.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class EntityController {
    @Autowired
    private EntityService entityService;

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public Entity addEntity(@RequestBody Entity entity){
        return entityService.create(entity);
    }

    @GetMapping("/test")
    public HashMap<String, String> test(){
        HashMap<String, String> a = new HashMap<>();
        a.put("Hello", "world");
        return a;
    }
}
