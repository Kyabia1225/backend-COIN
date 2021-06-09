package com.example.coin.service;

import com.example.coin.po.*;
import com.example.coin.util.ResponseVO;
import com.example.coin.vo.EntityVO;

import java.util.List;
import java.util.Set;

public interface EntityService {
    //增加实体
    Entity addEntity (Entity entity);
    //删除实体
    boolean deleteEntityById(String id);
    //查询实体
    EntityVO getEntityById(String id);
    //更新实体
    boolean updateEntityById(String id, Entity e, boolean updateAll);
    //获取所有实体
    List<Entity> getAllEntities();
    //删除所有实体
    void deleteAllEntities();
    //获取实体的所有关系id
    Set<String> getAssociatedRelations(String id);
    //获取实体所有相关的一级实体id
    List<EntityVO> getAssociatedEntities(String id);
    //更新所有entity的位置信息
    ResponseVO updateLocations(List<Entity>entities);

    //搜索相关功能
    Set<String> fuzzySearch(String condition);
}
