package com.example.coin.controller;


import com.example.coin.pojo.Entity;
import com.example.coin.pojo.relationship;
import com.example.coin.service.EntityService;
import com.example.coin.service.RelationshipService;
import com.example.coin.util.RedisUtil;
import com.example.coin.util.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import static com.example.coin.util.RedisUtil.RELATIONSHIP_REDIS_PREFIX;
import static com.example.coin.util.RedisUtil.ENTITY_REDIS_PREFIX;
import static com.example.coin.util.RedisUtil.TWO_HOURS_IN_SECOND;

@Controller
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

    @RequestMapping(path = "/addRelationship", method = RequestMethod.POST)
    public ResponseVO addRelById(@RequestParam(value = "fromId")Long fromId,
                                 @RequestParam(value = "toId")Long toId,
                                 @RequestParam(value = "name")String name){
        Entity entity1, entity2;
        try{
            entity1 = (Entity) redisUtil.get(ENTITY_REDIS_PREFIX+fromId);
            if(entity1 == null) entity1 = entityService.findEntityById(fromId);
            entity2 = (Entity) redisUtil.get(ENTITY_REDIS_PREFIX+toId);
            if(entity2 == null) entity2 = entityService.findEntityById(toId);
            if(entity1 == null || entity2 == null)throw new Exception();
        }catch (Exception exception){
            return ResponseVO.buildFailure(ID_NOT_EXIST);
        }
        redisUtil.set(ENTITY_REDIS_PREFIX+fromId, entity1);
        redisUtil.expire(ENTITY_REDIS_PREFIX+fromId, TWO_HOURS_IN_SECOND);
        redisUtil.set(ENTITY_REDIS_PREFIX+toId, entity2);
        redisUtil.expire(ENTITY_REDIS_PREFIX+toId, TWO_HOURS_IN_SECOND);
        relationship newRel = relationshipService.addRelationship(entity1, entity2, name);
        redisUtil.set(RELATIONSHIP_REDIS_PREFIX+fromId+"-"+toId, newRel);
        redisUtil.expire(RELATIONSHIP_REDIS_PREFIX+fromId+"-"+toId, TWO_HOURS_IN_SECOND);
        return ResponseVO.buildSuccess(newRel);
    }

    @RequestMapping(path = "/listRelationships", method = RequestMethod.GET)
    public ResponseVO getRelationList(){
        List<relationship> allRelationships = (List<relationship>) redisUtil.get(RELATIONSHIP_REDIS_PREFIX+"list");
        if(allRelationships == null) {
            allRelationships = relationshipService.findAllRelationships();
        }
        redisUtil.set(RELATIONSHIP_REDIS_PREFIX+"list", allRelationships);
        return ResponseVO.buildSuccess(allRelationships);
    }

    @RequestMapping(path = "/delRelationship", method = RequestMethod.POST)
    public ResponseVO deleteRelById(@RequestParam(value = "fromId")Long fromId, @RequestParam(value = "toId")Long toId){
        Entity entity1, entity2;
        try{
            entity1 = (Entity) redisUtil.get(ENTITY_REDIS_PREFIX+fromId);
            if(entity1 == null) entity1 = entityService.findEntityById(fromId);
            entity2 = (Entity) redisUtil.get(ENTITY_REDIS_PREFIX+toId);
            if(entity2 == null) entity2 = entityService.findEntityById(toId);
            if(entity1 == null || entity2 == null)throw new Exception();
        }catch (Exception exception){
            return ResponseVO.buildFailure(ID_NOT_EXIST);
        }
        redisUtil.set(ENTITY_REDIS_PREFIX+fromId, entity1);
        redisUtil.expire(ENTITY_REDIS_PREFIX+fromId, TWO_HOURS_IN_SECOND);
        redisUtil.set(ENTITY_REDIS_PREFIX+toId, entity2);
        redisUtil.expire(ENTITY_REDIS_PREFIX+toId, TWO_HOURS_IN_SECOND);
        redisUtil.del(RELATIONSHIP_REDIS_PREFIX+fromId+"-"+toId);
        relationshipService.deleteRelationById(fromId, toId);
        return ResponseVO.buildSuccess();
    }

    @RequestMapping(path = "/deleteAllRelationships", method = RequestMethod.GET)
    public ResponseVO deleteAllRelationships(){
        relationshipService.deleteAllRelationships();
        return ResponseVO.buildSuccess();
    }
}
