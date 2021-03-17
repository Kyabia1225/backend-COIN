package com.example.coin.impl;

import com.example.coin.DAO.EntityRepository;
import com.example.coin.DAO.RelationshipRepository;
import com.example.coin.pojo.Entity;
import com.example.coin.pojo.relationship;
import com.example.coin.service.EntityService;
import com.example.coin.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntityServiceImpl implements EntityService {
    @Autowired
    private EntityRepository entityRepository;
    @Autowired
    private RelationshipRepository relationshipRepository;

    //错误信息
    private static final String ENTITY_EXIST = "节点已存在";
    private static final String ID_NOT_EXIST = "该ID不存在";

    @Override
    public ResponseVO createEntity(Entity entity) {
        Entity savedEntity;
        try {
            savedEntity = entityRepository.save(entity);
            if(savedEntity == null)throw new Exception();
        }catch(Exception e){
            return ResponseVO.buildFailure(ENTITY_EXIST);
        }
        return ResponseVO.buildSuccess(savedEntity);
    }

    @Override
    public ResponseVO deleteEntityById(Long id) {
        entityRepository.deleteById(id);
        return ResponseVO.buildSuccess();
    }

    @Override
    public ResponseVO findEntityById(Long id) {
        Entity foundEntity;
        try{
            Optional<Entity>optionalEntity =  entityRepository.findById(id);
            if(optionalEntity.isPresent()){
                foundEntity = optionalEntity.get();
            }else{
                throw new Exception();
            }
        }catch(Exception e){
            return ResponseVO.buildFailure(ID_NOT_EXIST);
        }
        return ResponseVO.buildSuccess(foundEntity);
    }

    @Override
    public ResponseVO findAllEntities() {
        return ResponseVO.buildSuccess((List<Entity>)entityRepository.findAll());
    }

    @Override
    public ResponseVO addRelationship(Entity from, Entity to, String name) {
        relationship rel = new relationship(from, to, name);
        try{
            relationship verify = relationshipRepository.save(rel);
            if(verify == null)throw new Exception();
        }catch(Exception e){
            return ResponseVO.buildFailure(ENTITY_EXIST);
        }
        return ResponseVO.buildSuccess(rel);
    }

    @Override
    public ResponseVO deleteRelationById(Long fromId, Long toId) {
        try{
            relationshipRepository.deleteById(fromId, toId);
        }catch (Exception e){
            return ResponseVO.buildFailure(ID_NOT_EXIST);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public ResponseVO findRelationById(Long id) {
        relationship foundRel;
        try{
            Optional<relationship> rel = relationshipRepository.findById(id);
            if(rel.isPresent()){
                foundRel = rel.get();
            }else{
                throw new Exception();
            }
        }catch (Exception e){
            return ResponseVO.buildFailure(ID_NOT_EXIST);
        }
        return ResponseVO.buildSuccess(foundRel);
    }

    @Override
    public ResponseVO deleteAllEntities() {
        entityRepository.deleteAll();
        return ResponseVO.buildSuccess();
    }

    @Override
    public ResponseVO findAllRelationships() {
        return ResponseVO.buildSuccess((List<relationship>) relationshipRepository.findAll());
    }

    @Override
    public ResponseVO deleteAllRelationships() {
        relationshipRepository.deleteAll();
        return ResponseVO.buildSuccess();
    }
}
