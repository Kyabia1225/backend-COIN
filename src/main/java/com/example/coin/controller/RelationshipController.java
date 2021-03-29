package com.example.coin.controller;


import com.example.coin.pojo.Entity;
import com.example.coin.pojo.relationship;
import com.example.coin.service.EntityService;
import com.example.coin.service.RelationshipService;
import com.example.coin.util.RedisUtil;
import com.example.coin.util.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/coin")
public class RelationshipController {
    @Autowired
    private RelationshipService relationshipService;
    @Autowired
    private EntityService entityService;
    @Autowired
    RedisUtil redisUtil;

    //错误信息
    private static final String ENTITY_EXIST = "该关系节点已存在";
    private static final String ID_NOT_EXIST = "该关系节点ID不存在";

    @RequestMapping(path = "/getRelationship", method = RequestMethod.GET)
    public ResponseVO getRelationshipById(@RequestParam(value = "id")Long id){
        relationship r;
        try{
            r = relationshipService.findRelationById(id);
            if(r == null) throw new Exception();
        }catch (Exception exception){
            return ResponseVO.buildFailure(ID_NOT_EXIST);
        }
        return ResponseVO.buildSuccess(r);
    }

    @RequestMapping(path = "/listRelationships", method = RequestMethod.GET)
    public ResponseVO getRelationList(){
        List<relationship> allRelationships = relationshipService.findAllRelationships();
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
        return ResponseVO.buildSuccess(relationshipService.addRelationship(entity1, entity2, name));
    }

    @RequestMapping(path = "/delRelationship", method = RequestMethod.POST)
    public ResponseVO deleteRelById(@RequestParam(value = "fromId")Long fromId, @RequestParam(value = "toId")Long toId){
        Entity entity1, entity2;
        try{
            entity1 = entityService.findEntityById(fromId);
            entity2 = entityService.findEntityById(toId);
            if(entity1 == null || entity2 == null)throw new Exception();
        }catch (Exception exception){
            return ResponseVO.buildFailure(ID_NOT_EXIST);
        }
        relationshipService.deleteRelationById(fromId, toId);
        return ResponseVO.buildSuccess();
    }

    @RequestMapping(path = "/deleteAllRelationships", method = RequestMethod.GET)
    public ResponseVO deleteAllRelationships(){
        relationshipService.deleteAllRelationships();
        return ResponseVO.buildSuccess();
    }
}
