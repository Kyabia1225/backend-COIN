package com.example.coin.controller;

import com.example.coin.po.Relation;
import com.example.coin.service.RelationService;
import com.example.coin.util.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/coin")
public class RelationController {
    @Autowired
    private RelationService relationService;

    //错误信息
    private static final String ID_NOT_EXIST = "节点ID不存在";
    private static final String REL_NOT_EXIST = "或两实体节点间不存在这样的关系";
    @RequestMapping(path = "/getRelationship", method = RequestMethod.GET)
    public ResponseVO getRelationshipById(@RequestParam(value = "id")String id){
        Relation r = relationService.getRelationById(id);
        if(r == null) return ResponseVO.buildFailure(ID_NOT_EXIST);
        else return ResponseVO.buildSuccess(r);
    }

    @RequestMapping(path = "/addRelationship", method = RequestMethod.POST)
    public ResponseVO addRelById(@RequestParam(value = "source")String source,
                                 @RequestParam(value = "target")String target,
                                 @RequestParam(value = "relation")String relation){
        Relation newRel = relationService.addRelationship(source, target, relation);
        if(newRel == null){
            return ResponseVO.buildFailure(ID_NOT_EXIST);
        }
        return ResponseVO.buildSuccess(newRel);
    }

    @RequestMapping(path = "/delRelationship1", method = RequestMethod.POST)
    public ResponseVO deleteRelById(@RequestParam(value = "source")String source, @RequestParam(value = "target")String target, @RequestParam(value = "name")String name){
        boolean flag = relationService.deleteRelationById(source, target, name);
        if(flag) return ResponseVO.buildSuccess();
        else return ResponseVO.buildFailure(ID_NOT_EXIST+REL_NOT_EXIST);
    }

    @RequestMapping(path = "/delRelationship2", method = RequestMethod.POST)
    public ResponseVO deleteRelById(@RequestParam(value = "id")String id){
        boolean flag = relationService.deleteRelationById(id);
        if(!flag) return ResponseVO.buildFailure(ID_NOT_EXIST);
        else return ResponseVO.buildSuccess();
    }

    @RequestMapping(path = "/listRelationships", method = RequestMethod.GET)
    public ResponseVO getRelationList(){
        List<Relation> allRelations = relationService.getAllRelationships();
        return ResponseVO.buildSuccess(allRelations);
    }

    @RequestMapping(path = "/deleteAllRelationships", method = RequestMethod.GET)
    public ResponseVO deleteAllRelationships(){
        relationService.deleteAllRelationships();
        return ResponseVO.buildSuccess();
    }

    @RequestMapping(path = "/updateRelationship", method = RequestMethod.POST)
    public ResponseVO updateRelationship(@RequestParam(value = "id")String id, @RequestBody Relation rel){
        boolean flag = relationService.updateRelationshipById(id, rel);
        if(!flag) return ResponseVO.buildFailure(ID_NOT_EXIST);
        else return ResponseVO.buildSuccess();
    }

    @GetMapping(path = "relationSearch")
    public  ResponseVO searchRelation(@RequestParam(value = "keyword")String keyword){
        Set<String> res = relationService.fuzzySearch(keyword);
        if(res.isEmpty()){
            return ResponseVO.buildFailure("未查找到相关关系");
        }else{
            return ResponseVO.buildSuccess(res);
        }
    }

}
