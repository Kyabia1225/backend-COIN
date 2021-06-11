package com.example.coin.service.impl;

import com.example.coin.DAO.*;
import com.example.coin.po.*;
import com.example.coin.service.EntityService;
import com.example.coin.service.RelationService;
import com.example.coin.util.ResponseVO;
import com.example.coin.util.StringDistance;
import com.example.coin.vo.EntityVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class EntityServiceImpl implements EntityService {
    @Autowired
    private EntityRepository entityRepository;
    @Autowired
    private RelationService relationService;
    @Autowired
    private AnimeRepository animeRepository;
    @Autowired
    private AnimeCharacterRepository animeCharacterRepository;
    @Autowired
    private AnimeCVRepository animeCVRepository;
    @Autowired
    private AnimeCompanyRepository animeCompanyRepository;
    @Autowired
    private AnimeDirectorRepository animeDirectorRepository;

    /**
     * 将关系节点持久化到数据库中
     * @param entity 关系节点
     * @return
     */
    @Override
    public Entity addEntity(Entity entity) {
        return entityRepository.save(entity);
    }

    /**
     * 根据id删除
     * @param id
     */
    @Override
    public boolean deleteEntityById(String id) {
        Entity entity = entityRepository.findEntityById(id);
        if(entity == null) return false;
        //删除与节点相关的所有关系、与该节点有关的节点中relatesTo的记录
        Set<String> associatedRelationships = getAssociatedRelations(entity.getId());
        for(String relId : associatedRelationships){
            //根据这个关系节点找到对应关系后，删除对方实体节点中关于本实体节点的信息
            String correspondingEntityId = entity.getRelatesTo().get(relId);
            Entity correspondingEntity = entityRepository.findEntityById(correspondingEntityId);
            correspondingEntity.getRelatesTo().remove(relId);
            updateEntityById(correspondingEntityId, correspondingEntity, false);
            //然后删除这个关系
            relationService.deleteRelationById(relId);
        }
        entityRepository.deleteById(id);
        return true;
    }

    /**
     * 根据id寻找
     * @param id
     * @return
     */
    @Override
    public EntityVO getEntityById(String id) {
        if(id == null) return null;
        Entity entity = entityRepository.findEntityById(id);
        if(entity == null) return null;
        switch (entity.getType()) {
            case "Anime": {
                Anime anime = animeRepository.findAnimeByAnimeId(entity.getBgmId());
                EntityVO entityVO = new EntityVO(entity);
                Map<String, String> entityVOProperties = new HashMap<>();
                entityVOProperties.put("animeId", anime.getAnimeId());
                entityVOProperties.put("japaneseName", anime.getJapaneseName());
                entityVOProperties.put("title", anime.getTitle());
                entityVOProperties.put("length", anime.getLength());
                entityVOProperties.put("company", anime.getCompany());
                entityVOProperties.put("director", anime.getDirector());
                entityVOProperties.put("description", anime.getDescription());
                entityVOProperties.put("startDate", anime.getStartDate());
                entityVOProperties.put("ranking", String.valueOf(anime.getRanking()));
                entityVOProperties.put("score", String.valueOf(anime.getScore()));
                entityVO.setProperties(entityVOProperties);
                entityVO.setRelatesTo(new HashMap<>(entity.getRelatesTo()));
                return entityVO;
            }
            case "AnimeCharacter": {
                AnimeCharacter animeCharacter = animeCharacterRepository.findAnimeCharacterByCharacterId(entity.getBgmId());
                EntityVO entityVO = new EntityVO(entity);
                Map<String, String> entityVOProperties = new HashMap<>();
                entityVOProperties.put("characterId", animeCharacter.getCharacterId());
                entityVOProperties.put("name", animeCharacter.getName());
                entityVOProperties.put("description", animeCharacter.getDescription());
                entityVOProperties.put("birthday", animeCharacter.getBirthday());
                entityVOProperties.put("gender", animeCharacter.getGender());
                StringBuilder stringBuilder = new StringBuilder();
                for (String name : animeCharacter.getOtherNames()) {
                    stringBuilder.append(name).append(" ");
                }
                entityVOProperties.put("otherNames", stringBuilder.toString());
                entityVO.setProperties(entityVOProperties);
                entityVO.setRelatesTo(new HashMap<>(entity.getRelatesTo()));
                return entityVO;
            }
            case "AnimeCV": {
                AnimeCV animeCV = animeCVRepository.findAnimeCVByCvId(entity.getBgmId());
                EntityVO entityVO = new EntityVO(entity);
                Map<String, String> entityVOProperties = new HashMap<>();
                entityVOProperties.put("CvId", animeCV.getCvId());
                entityVOProperties.put("birthday", animeCV.getBirthday());
                entityVOProperties.put("gender", animeCV.getGender());
                entityVOProperties.put("name", animeCV.getName());
                entityVOProperties.put("description", animeCV.getDescription());
                entityVOProperties.put("profession", animeCV.getProfession());
                StringBuilder stringBuilder = new StringBuilder();
                for (String name : animeCV.getOtherNames()) {
                    stringBuilder.append(name).append(" ");
                }
                entityVOProperties.put("otherNames", stringBuilder.toString());
                entityVO.setProperties(entityVOProperties);
                entityVO.setRelatesTo(new HashMap<>(entity.getRelatesTo()));
                return entityVO;
            }
            case "AnimeCompany": {
                AnimeCompany animeCompany = animeCompanyRepository.findAnimeCompanyByCompanyId(entity.getBgmId());
                EntityVO entityVO = new EntityVO(entity);
                Map<String, String> entityVOProperties = new HashMap<>();
                entityVOProperties.put("CompanyId", animeCompany.getCompanyId());
                entityVOProperties.put("birthday", animeCompany.getBirthday());
                entityVOProperties.put("name", animeCompany.getName());
                entityVOProperties.put("description", animeCompany.getDescription());
                entityVOProperties.put("profession", animeCompany.getProfession());
                StringBuilder stringBuilder = new StringBuilder();
                for (String name : animeCompany.getOtherNames()) {
                    stringBuilder.append(name).append(" ");
                }
                entityVOProperties.put("otherNames", stringBuilder.toString());
                entityVO.setProperties(entityVOProperties);
                entityVO.setRelatesTo(new HashMap<>(entity.getRelatesTo()));
                return entityVO;
            }
            case "AnimeDirector": {
                AnimeDirector animeDirector = animeDirectorRepository.findAnimeDirectorByDirectorId(entity.getBgmId());
                EntityVO entityVO = new EntityVO(entity);
                Map<String, String> entityVOProperties = new HashMap<>();
                entityVOProperties.put("directorId", animeDirector.getDirectorId());
                entityVOProperties.put("birthday", animeDirector.getBirthday());
                entityVOProperties.put("gender", animeDirector.getGender());
                entityVOProperties.put("name", animeDirector.getName());
                entityVOProperties.put("description", animeDirector.getDescription());
                entityVOProperties.put("profession", animeDirector.getProfession());
                StringBuilder stringBuilder = new StringBuilder();
                for (String name : animeDirector.getOtherNames()) {
                    stringBuilder.append(name).append(" ");
                }
                entityVOProperties.put("otherNames", stringBuilder.toString());
                entityVO.setProperties(entityVOProperties);
                entityVO.setRelatesTo(new HashMap<>(entity.getRelatesTo()));
                return entityVO;
            }
            default: {
                EntityVO entityVO = new EntityVO(entity);
                entityVO.setProperties(entity.getProperties());
                entityVO.setRelatesTo(entity.getRelatesTo());
                return entityVO;
            }
        }
    }

    /**
     * 更新指定id的节点
     * @param id    节点id
     * @param e     更新内容
     * @param updateAll     是否全部更新（出于效率问题， 一般只更新relatesTo）
     * @return  是否成功
     */
    @Override
    public boolean updateEntityById(String id, Entity e, boolean updateAll) {
        Entity origin = entityRepository.findEntityById(id);
        if(origin == null) return false;
        if(updateAll) { //出于效率考虑，大多数更新只更新relatesTo，忽略以下变量
            origin.setName(e.getName());
            origin.setType(e.getType());
            origin.setFx(e.getFx());
            origin.setFy(e.getFy());
            origin.setProperties(e.getProperties());
        }
        origin.setRelatesTo(e.getRelatesTo());
        entityRepository.save(origin);
        return true;
    }

    /**
     * 返回所有实体节点
     * @return
     */
    @Override
    public List<Entity> getAllEntities() {
        return entityRepository.findAll();
    }

    /**
     * 删除所有实体节点
     */
    @Override
    public void deleteAllEntities() {
        entityRepository.deleteAll();
        //删除所有实体节点的同时删除所有关系节点
        relationService.deleteAllRelationships();
    }

    @Override
    public Set<String> getAssociatedRelations(String id) {
        Entity entity = entityRepository.findEntityById(id);
        if(entity == null) return null;
        return new HashSet<>(entity.getRelatesTo().keySet());
    }

    @Override
    public List<EntityVO> getAssociatedEntities(String id) {
        Entity entity = entityRepository.findEntityById(id);
        if(entity == null) return null;
        List<EntityVO> entityVOList = new LinkedList<>();
        Set<String> entityIds = new HashSet<>(entity.getRelatesTo().values());
        for(String entityId:entityIds){
            entityVOList.add(getEntityById(entityId));
        }
        entityVOList.add(getEntityById(id));
        return entityVOList;
    }

    @Override
    public ResponseVO updateLocations(List<Entity> entities) {
        for(Entity before:entities){
            Entity after = entityRepository.findEntityById(before.getId());
            after.setX(before.getX());
            after.setY(before.getY());
            after.setFx(before.getFx());
            after.setFy(before.getFy());
            after.setVx(before.getVx());
            after.setVy(before.getVy());
            entityRepository.save(after);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public Set<String> fuzzySearch(String condition) {
        if (condition == null) return null;
        Set<String> res = new HashSet<>(); //长度>=3
        List<Entity> entities = entityRepository.findAll();
        for (Entity entity : entities) {
            String id = entity.getId();
            //名字距离
            if (StringDistance.matches(entity.getName(), condition)) {
                res.add(id);
                continue;
            }
            //类型距离
            else if (StringDistance.matches(entity.getType(), condition)) {
                res.add(id);
                continue;
            }

            boolean keyFlag = false;
            //属性距离
            for (String key : entity.getProperties().keySet()) {
                if (StringDistance.matches(key, condition)) {
                    res.add(id);
                    keyFlag = true;
                    break;
                }
            }
            if (keyFlag) continue;
            for (String value : entity.getProperties().values()) {
                if (StringDistance.matches(value, condition)) {
                    res.add(id);
                    break;
                }
            }
        }
        return res;
    }
}
