package com.example.coin.controller;


import com.example.coin.javaBeans.Entity;
import com.example.coin.javaBeans.relationship;
import com.example.coin.service.EntityService;
import com.example.coin.service.RelationshipService;
import com.example.coin.util.RedisUtil;
import com.example.coin.util.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static com.example.coin.util.RedisUtil.RELATIONSHIP_REDIS_PREFIX;
import static com.example.coin.util.RedisUtil.ENTITY_REDIS_PREFIX;
import static com.example.coin.util.RedisUtil.TWO_HOURS_IN_SECOND;

@RestController
@RequestMapping("/api/coin")
public class RelationshipController {
    @Autowired
    private RelationshipService relationshipService;
    @Autowired
    private EntityService entityService;
    @Autowired
    private RedisUtil redisUtil;

    //错误信息
    private static final String ID_NOT_EXIST = "节点ID不存在";
    private static final String REL_NOT_EXIST = "或两实体节点间不存在这样的关系";
    @RequestMapping(path = "/getRelationship", method = RequestMethod.GET)
    public ResponseVO getRelationshipById(@RequestParam(value = "id")String id){
        relationship r = relationshipService.findRelationById(id);
        if(r == null) return ResponseVO.buildFailure(ID_NOT_EXIST);
        else return ResponseVO.buildSuccess(r);
    }

    @RequestMapping(path = "/addRelationship", method = RequestMethod.POST)
    public ResponseVO addRelById(@RequestParam(value = "fromId")String fromId,
                                 @RequestParam(value = "toId")String toId,
                                 @RequestParam(value = "name")String name){
        relationship newRel = relationshipService.addRelationship(fromId, toId, name);
        if(newRel == null){
            return ResponseVO.buildFailure(ID_NOT_EXIST);
        }
        redisUtil.set(RELATIONSHIP_REDIS_PREFIX+fromId+"-"+toId, newRel);
        redisUtil.expire(RELATIONSHIP_REDIS_PREFIX+fromId+"-"+toId, TWO_HOURS_IN_SECOND);
        return ResponseVO.buildSuccess(newRel);
    }

    @RequestMapping(path = "/delRelationship1", method = RequestMethod.POST)
    public ResponseVO deleteRelById(@RequestParam(value = "source")String source, @RequestParam(value = "target")String target, @RequestParam(value = "name")String name){
        redisUtil.del(RELATIONSHIP_REDIS_PREFIX+source+"-"+target);
        boolean flag = relationshipService.deleteRelationById(source, target, name);
        if(flag) return ResponseVO.buildSuccess();
        else return ResponseVO.buildFailure(ID_NOT_EXIST+REL_NOT_EXIST);
    }

    @RequestMapping(path = "/delRelationship2", method = RequestMethod.POST)
    public ResponseVO deleteRelById(@RequestParam(value = "id")String id){
        relationship rel = relationshipService.findRelationById(id);
        if(rel == null) return ResponseVO.buildFailure(ID_NOT_EXIST);
        redisUtil.del(RELATIONSHIP_REDIS_PREFIX+rel.getSource()+"-"+rel.getTarget());
        relationshipService.deleteRelationById(id);
        return ResponseVO.buildSuccess();
    }

    @RequestMapping(path = "/listRelationships", method = RequestMethod.GET)
    public ResponseVO getRelationList(){
        List<relationship> allRelationships = relationshipService.findAllRelationships();
        return ResponseVO.buildSuccess(allRelationships);
    }

    @RequestMapping(path = "/deleteAllRelationships", method = RequestMethod.GET)
    public ResponseVO deleteAllRelationships(){
        relationshipService.deleteAllRelationships();
        return ResponseVO.buildSuccess();
    }

    @RequestMapping(path = "/updateRelationship", method = RequestMethod.POST)
    public ResponseVO updateRelationship(@RequestParam(value = "id")String id, @RequestBody relationship rel){
        if(relationshipService.findRelationById(id) == null)return ResponseVO.buildFailure(ID_NOT_EXIST);
        relationshipService.updateRelationshipById(id, rel);
        redisUtil.set(RELATIONSHIP_REDIS_PREFIX+rel.getSource()+"-"+rel.getTarget(), rel);
        redisUtil.expire(RELATIONSHIP_REDIS_PREFIX+rel.getSource()+"-"+rel.getTarget(), TWO_HOURS_IN_SECOND);
        return ResponseVO.buildSuccess();
    }

}
