package com.example.coin.controller;

import com.example.coin.javaBeans.Entity;
import com.example.coin.service.EntityService;
import com.example.coin.util.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/coin")
public class EntityController {
    @Autowired
    private EntityService entityService;

    //错误信息
    private static final String ENTITY_EXIST = "该实体节点已存在";
    private static final String ID_NOT_EXIST = "该实体节点ID不存在";

    @RequestMapping(path = "/addEntity", method = RequestMethod.POST)
    public ResponseVO addEntity(@RequestBody Entity entity){
        Entity e = entityService.createEntity(entity);
        if(e == null) return ResponseVO.buildFailure(ENTITY_EXIST);
        return ResponseVO.buildSuccess(e);
    }

    @RequestMapping(path = "/deleteEntity", method = RequestMethod.POST)
    public ResponseVO deleteEntityById(@RequestParam(value = "id")String id){
        boolean flag = entityService.deleteEntityById(id);
        if(flag) return ResponseVO.buildSuccess();
        else return ResponseVO.buildFailure(ID_NOT_EXIST);
    }

    @RequestMapping(path = "/getEntity", method = RequestMethod.GET)
    public ResponseVO getEntityById(@RequestParam(value = "id")String id){
        Entity foundEntity = entityService.findEntityById(id);
        if(foundEntity == null) return ResponseVO.buildFailure(ID_NOT_EXIST);
        else return ResponseVO.buildSuccess(foundEntity);
    }

    @RequestMapping(path = "/listEntities", method = RequestMethod.GET)
    public ResponseVO getEntityList(){
        List<Entity> allEntities = entityService.findAllEntities();
        return ResponseVO.buildSuccess(allEntities);
    }

    @RequestMapping(path = "/deleteAllEntities", method = RequestMethod.GET)
    public ResponseVO deleteAllEntities(){
        entityService.deleteAllEntities();
        return ResponseVO.buildSuccess();
    }

    @RequestMapping(path = "/updateEntity", method = RequestMethod.POST)
    public ResponseVO updateEntity(@RequestParam(value = "id")String id, @RequestBody Entity entity){
        boolean flag = entityService.updateEntityById(id, entity);
        if(!flag) return ResponseVO.buildFailure(ID_NOT_EXIST);
        else return ResponseVO.buildSuccess();
    }


}
