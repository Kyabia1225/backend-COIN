package com.example.coin.controller;

import com.example.coin.pojo.Entity;
import com.example.coin.pojo.relationship;
import com.example.coin.service.EntityService;
import com.example.coin.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coin")
public class EntityController {
    @Autowired
    private EntityService entityService;

    //错误信息
    private static final String ENTITY_EXIST = "节点已存在";
    private static final String ID_NOT_EXIST = "该ID不存在";

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

    @RequestMapping(path = "/getRelationship", method = RequestMethod.GET)
    public ResponseVO getRelationshipById(@RequestParam(value = "id")Long id){
        relationship r;
        try{
            r = entityService.findRelationById(id);
            if(r == null) throw new Exception();
        }catch (Exception exception){
            return ResponseVO.buildFailure(ID_NOT_EXIST);
        }
        return ResponseVO.buildSuccess(r);
    }


    @RequestMapping(path = "/listEntities", method = RequestMethod.GET)
    public ResponseVO getEntityList(){
        List<Entity> allEntities = entityService.findAllEntities();
        return ResponseVO.buildSuccess(allEntities);
    }

    @RequestMapping(path = "/listRelationships", method = RequestMethod.GET)
    public ResponseVO getRelationList(){
        List<relationship>allRelationships = entityService.findAllRelationships();
        return ResponseVO.buildSuccess(allRelationships);
    }

    @RequestMapping(path = "/addRelationship", method = RequestMethod.POST)
    public ResponseVO addRelById(@RequestParam(value = "fromId")Long fromId,
                                   @RequestParam(value = "toId")Long toId,
                                   @RequestParam(value = "name")String name){
        Entity entity1, entity2;
        try{
            entity1 = entityService.findEntityById(fromId);
            entity2 = entityService.findEntityById(toId);
            if(entity1 == null || entity2 == null)throw new Exception();
        }catch (Exception exception){
            return ResponseVO.buildFailure(ID_NOT_EXIST);
        }
        return ResponseVO.buildSuccess(entityService.addRelationship(entity1, entity2, name));
    }

    @RequestMapping(path = "/delReltionship", method = RequestMethod.POST)
    public ResponseVO deleteRelById(@RequestParam(value = "fromId")Long fromId, @RequestParam(value = "toId")Long toId){
        Entity entity1, entity2;
        try{
            entity1 = entityService.findEntityById(fromId);
            entity2 = entityService.findEntityById(toId);
            if(entity1 == null || entity2 == null)throw new Exception();
        }catch (Exception exception){
            return ResponseVO.buildFailure(ID_NOT_EXIST);
        }
        entityService.deleteRelationById(fromId, toId);
        return ResponseVO.buildSuccess();
    }

    @RequestMapping(path = "/deleteAllEntities", method = RequestMethod.GET)
    public ResponseVO deleteAllEntities(){
        entityService.deleteAllEntities();
        return ResponseVO.buildSuccess();
    }

    @RequestMapping(path = "/deleteAllRelationships", method = RequestMethod.GET)
    public ResponseVO deleteAllRelationships(){
        entityService.deleteAllRelationships();
        return ResponseVO.buildSuccess();
    }

}
