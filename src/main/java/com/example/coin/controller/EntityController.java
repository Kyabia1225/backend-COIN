package com.example.coin.controller;

import com.example.coin.pojo.Entity;
import com.example.coin.service.EntityService;
import com.example.coin.util.RedisUtil;
import com.example.coin.util.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.coin.util.RedisUtil.ENTITY_REDIS_PREFIX;
import static com.example.coin.util.RedisUtil.TWO_HOURS_IN_SECOND;

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
        //加入缓存
        redisUtil.set(ENTITY_REDIS_PREFIX+e.getId(), e);
        //设置缓存时长
        redisUtil.expire(ENTITY_REDIS_PREFIX+e.getId(), TWO_HOURS_IN_SECOND);
        return ResponseVO.buildSuccess(e);
    }

    @RequestMapping(path = "/deleteEntity", method = RequestMethod.POST)
    public ResponseVO deleteEntityById(@RequestParam(value = "id")Long id){
        redisUtil.del(ENTITY_REDIS_PREFIX+id);
        entityService.deleteEntityById(id);
        return ResponseVO.buildSuccess();
    }

    @RequestMapping(path = "/getEntity", method = RequestMethod.GET)
    public ResponseVO getEntityById(@RequestParam(value = "id")Long id){
        //尝试加载缓存
        Entity e = (Entity) redisUtil.get(ENTITY_REDIS_PREFIX+id);
        if(e!=null)return ResponseVO.buildSuccess(e);
        //缓存中找不到
        try{
            e = entityService.findEntityById(id);
            if(e == null)throw new Exception();
        }catch (Exception exception){
            return ResponseVO.buildFailure(ID_NOT_EXIST);
        }
        //加载到缓存中
        redisUtil.set(ENTITY_REDIS_PREFIX+id, e);
        redisUtil.expire(ENTITY_REDIS_PREFIX+id, TWO_HOURS_IN_SECOND);
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
