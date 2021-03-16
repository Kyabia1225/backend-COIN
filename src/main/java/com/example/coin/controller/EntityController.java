package com.example.coin.controller;

import com.example.coin.pojo.Entity;
import com.example.coin.pojo.relationship;
import com.example.coin.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/entity")
public class EntityController {
    @Autowired
    private EntityService entityService;

    public EntityController(EntityService entityService) {
        this.entityService = entityService;
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)

    public Entity addEntity(@RequestBody Entity entity){
        return entityService.create(entity);
    }

    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    public void deleteEntityById(@RequestParam(value = "id")long id){
        entityService.deleteById(id);
        System.out.println("删除"+id+"号实体");
    }

    @RequestMapping(path = "/get", method = RequestMethod.GET)
    public Entity getEntityById(@RequestParam(value = "id")long id){
        Optional<Entity> optionalEntities = entityService.findById(id);
        if(optionalEntities.isPresent()){
            return optionalEntities.get();
        }
        else{
            return null;
        }
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public List<Entity>getEntityList(){
        return entityService.findAll();
    }

    @RequestMapping(path = "/addRel", method = RequestMethod.POST)
    public relationship addRelById(@RequestParam(value = "fromId")long fromId,
                                   @RequestParam(value = "toId")long toId,
                                   @RequestParam(value = "name")String name){
        Optional<Entity>fromEntity = entityService.findById(fromId);
        Optional<Entity>toEntity = entityService.findById(toId);
        if(fromEntity.isPresent()&&toEntity.isPresent()){
            return entityService.addRelationship(fromEntity.get(), toEntity.get(),name);
        }
        else{
            return null;
        }
    }

    @RequestMapping(path = "/delRel", method = RequestMethod.POST)
    public int deleteRelById(@RequestParam(value = "fromId")long fromId, @RequestParam(value = "toId")long toId){
        Optional<Entity>fromEntity = entityService.findById(fromId);
        Optional<Entity>toEntity = entityService.findById(toId);
        if(fromEntity.isPresent()&&toEntity.isPresent()){
            entityService.deleteRelationById(fromId, toId);
            return 1; //success
        }
        else{
            return 0; //fail
        }
    }

    @RequestMapping(path = "/rm", method = RequestMethod.GET)
    public int deleteAll(){
        entityService.deleteAll();
        return 1;
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
