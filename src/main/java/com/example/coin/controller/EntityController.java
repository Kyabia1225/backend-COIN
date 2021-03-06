package com.example.coin.controller;

import com.example.coin.po.Entity;
import com.example.coin.service.EntityService;
import com.example.coin.util.ResponseVO;
import com.example.coin.vo.EntityVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


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
        Entity e = entityService.addEntity(entity);
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
        EntityVO foundEntity = entityService.getEntityById(id);
        if(foundEntity == null) return ResponseVO.buildFailure(ID_NOT_EXIST);
        else return ResponseVO.buildSuccess(foundEntity);
    }

    @RequestMapping(path = "/listEntities", method = RequestMethod.GET)
    public ResponseVO getEntityList(){
        List<Entity> allEntities = entityService.getAllEntities();
        return ResponseVO.buildSuccess(allEntities);
    }

    @RequestMapping(path = "/deleteAllEntities", method = RequestMethod.GET)
    public ResponseVO deleteAllEntities(){
        entityService.deleteAllEntities();
        return ResponseVO.buildSuccess();
    }

    @RequestMapping(path = "/updateEntity", method = RequestMethod.POST)
    public ResponseVO updateEntity(@RequestParam(value = "id")String id, @RequestBody Entity entity){
        boolean flag = entityService.updateEntityById(id, entity, true);
        if(!flag) return ResponseVO.buildFailure(ID_NOT_EXIST);
        else return ResponseVO.buildSuccess();
    }

    @RequestMapping(path = "/updateAllLocation", method = RequestMethod.POST)
    public ResponseVO updateAllLocation(@RequestBody List<Entity> entities){
        return entityService.updateLocations(entities);
    }

/*
    //该方法过时了 不再使用
    @GetMapping(path = "/entitySearch")
    public ResponseVO searchEntity(@RequestParam(value = "keyword")String keyword){
        Set<String> result = entityService.fuzzySearch(keyword);
        if(result.isEmpty()) return ResponseVO.buildFailure("未查找到相关实体");
        else{
            return ResponseVO.buildSuccess(result);
        }
    }*/
    @GetMapping(path = "/searchByName")
    public ResponseVO searchByName(@RequestParam String name){
        List<EntityVO> list = entityService.searchByName(name);
        if(list == null||list.size() == 0){
            return ResponseVO.buildFailure("未查找到相关结果");
        }else {
            return ResponseVO.buildSuccess(list);
        }
    }

    @GetMapping(path = "/getAssociatedEntities")
    public ResponseVO getAssociatedEntities(@RequestParam String id){
        List<EntityVO> associatedEntities = entityService.getAssociatedEntities(id);
        if(associatedEntities == null){
            return ResponseVO.buildFailure("id错误");
        }else{
            return ResponseVO.buildSuccess(associatedEntities);
        }
    }
}
