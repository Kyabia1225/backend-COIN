package com.example.coin.controller;

import com.example.coin.pojo.Entity;
import com.example.coin.service.EntityService;
import com.example.coin.util.RedisUtil;
import com.example.coin.util.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coin")
public class EntityController {
    @Autowired
    private EntityService entityService;
    @Autowired
    RedisUtil redisUtil;

    //错误信息
    private static final String ENTITY_EXIST = "该实体节点已存在";
    private static final String ID_NOT_EXIST = "该实体节点ID不存在";


    public EntityController(EntityService entityService) {
        this.entityService = entityService;
    }

    @RequestMapping(path = "/addEntity", method = RequestMethod.POST)
    public ResponseVO addEntity(@RequestBody Entity entity){
        Entity e;
        try{
            e = entityService.createEntity(entity);
            if(e == null) throw new Exception();
        }catch (Exception exception){
            return ResponseVO.buildFailure(ENTITY_EXIST);
        }
        return ResponseVO.buildSuccess(e);
    }

    @RequestMapping(path = "/deleteEntity", method = RequestMethod.POST)
    public ResponseVO deleteEntityById(@RequestParam(value = "id")Long id){
        entityService.deleteEntityById(id);
        return ResponseVO.buildSuccess();
    }

    @RequestMapping(path = "/getEntity", method = RequestMethod.GET)
    public ResponseVO getEntityById(@RequestParam(value = "id")Long id){
        Entity e;
        try{
            e = entityService.findEntityById(id);
            if(e == null)throw new Exception();
        }catch (Exception exception){
            return ResponseVO.buildFailure(ID_NOT_EXIST);
        }
        return ResponseVO.buildSuccess(e);
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

}
