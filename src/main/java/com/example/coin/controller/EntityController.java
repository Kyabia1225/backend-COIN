package com.example.coin.controller;

import com.example.coin.pojo.Entity;
import com.example.coin.pojo.relationship;
import com.example.coin.service.EntityService;
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

    public Entity addEntity(@RequestBody Entity entity){
        return entityService.createEntity(entity);
    }

    @RequestMapping(path = "/deleteEntity", method = RequestMethod.POST)
    public void deleteEntityById(@RequestParam(value = "id")Long id){
        entityService.deleteEntityById(id);
        System.out.println("删除"+id+"号实体");
    }

    @RequestMapping(path = "/getEntity", method = RequestMethod.GET)
    public Entity getEntityById(@RequestParam(value = "id")Long id){
        Optional<Entity> optionalEntities = entityService.findEntityById(id);
        if(optionalEntities.isPresent()){
            return optionalEntities.get();
        }
        else{
            return null;
        }
    }

    @RequestMapping(path = "/getRelationship", method = RequestMethod.GET)
    public relationship getRelationshipById(@RequestParam(value = "id")Long id){
        Optional<relationship>optionalRelationship = entityService.findRelationById(id);
        if(optionalRelationship.isPresent()){
            return optionalRelationship.get();
        }
        else{
            return null;
        }
    }


    @RequestMapping(path = "/listEntities", method = RequestMethod.GET)
    public List<Entity>getEntityList(){
        return entityService.findAllEntities();
    }

    @RequestMapping(path = "/listRelationships", method = RequestMethod.GET)
    public List<relationship>getRelationList(){return entityService.findAllRelationships();}

    @RequestMapping(path = "/addRelationship", method = RequestMethod.POST)
    public relationship addRelById(@RequestParam(value = "fromId")Long fromId,
                                   @RequestParam(value = "toId")Long toId,
                                   @RequestParam(value = "name")String name){
        Optional<Entity>fromEntity = entityService.findEntityById(fromId);
        Optional<Entity>toEntity = entityService.findEntityById(toId);
        if(fromEntity.isPresent()&&toEntity.isPresent()){
            return entityService.addRelationship(fromEntity.get(), toEntity.get(),name);
        }
        else{
            return null;
        }
    }

    @RequestMapping(path = "/delReltionship", method = RequestMethod.POST)
    public int deleteRelById(@RequestParam(value = "fromId")Long fromId, @RequestParam(value = "toId")Long toId){
        Optional<Entity>fromEntity = entityService.findEntityById(fromId);
        Optional<Entity>toEntity = entityService.findEntityById(toId);
        if(fromEntity.isPresent()&&toEntity.isPresent()){
            entityService.deleteRelationById(fromId, toId);
            return 1; //success
        }
        else{
            return 0; //fail
        }
    }

    @RequestMapping(path = "/deleteAllEntities", method = RequestMethod.GET)
    public int deleteAllEntities(){
        entityService.deleteAllEntities();
        return 1;
    }

    @RequestMapping(path = "/deleteAllRelationships", method = RequestMethod.GET)
    public int deleteAllRelationships(){
        entityService.deleteAllRelationships();
        return 2;
    }


    /*测试
    @GetMapping("/test")
    public HashMap<String, String> test(){
        HashMap<String, String> a = new HashMap<>();
        a.put("Hello", "world");
        return a;
    }
    */
}
