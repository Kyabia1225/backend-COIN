package com.example.coin.controller;

import com.example.coin.pojo.Entity;
import com.example.coin.pojo.relationship;
import com.example.coin.service.EntityService;
import com.example.coin.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/coin")
public class EntityController {
    @Autowired
    private EntityService entityService;

    public EntityController(EntityService entityService) {
        this.entityService = entityService;
    }

    @RequestMapping(path = "/addEntity", method = RequestMethod.POST)

    public ResponseVO addEntity(@RequestBody Entity entity){
        return entityService.createEntity(entity);
    }

    @RequestMapping(path = "/deleteEntity", method = RequestMethod.POST)
    public ResponseVO deleteEntityById(@RequestParam(value = "id")Long id){
        return entityService.deleteEntityById(id);
    }

    @RequestMapping(path = "/getEntity", method = RequestMethod.GET)
    public ResponseVO getEntityById(@RequestParam(value = "id")Long id){
        return entityService.findEntityById(id);
    }

    @RequestMapping(path = "/getRelationship", method = RequestMethod.GET)
    public ResponseVO getRelationshipById(@RequestParam(value = "id")Long id){
        return entityService.findRelationById(id);
    }


    @RequestMapping(path = "/listEntities", method = RequestMethod.GET)
    public ResponseVO getEntityList(){
        return entityService.findAllEntities();
    }

    @RequestMapping(path = "/listRelationships", method = RequestMethod.GET)
    public ResponseVO getRelationList(){return entityService.findAllRelationships();}

    @RequestMapping(path = "/addRelationship", method = RequestMethod.POST)
    public ResponseVO addRelById(@RequestParam(value = "fromId")Long fromId,
                                   @RequestParam(value = "toId")Long toId,
                                   @RequestParam(value = "name")String name){
        Entity fromEntity = (Entity) (entityService.findEntityById(fromId)).getContent();
        Entity toEntity = (Entity) (entityService.findEntityById(toId)).getContent();
        return entityService.addRelationship(fromEntity, toEntity, name);
    }

    @RequestMapping(path = "/delReltionship", method = RequestMethod.POST)
    public ResponseVO deleteRelById(@RequestParam(value = "fromId")Long fromId, @RequestParam(value = "toId")Long toId){
        return entityService.deleteRelationById(fromId, toId);
    }

    @RequestMapping(path = "/deleteAllEntities", method = RequestMethod.GET)
    public ResponseVO deleteAllEntities(){
        return entityService.deleteAllEntities();
    }

    @RequestMapping(path = "/deleteAllRelationships", method = RequestMethod.GET)
    public ResponseVO deleteAllRelationships(){
        return entityService.deleteAllRelationships();
    }

}
