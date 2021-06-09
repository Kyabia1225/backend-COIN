package com.example.coin.service;

import com.example.coin.DAO.*;
import com.example.coin.po.AnimeCV;
import com.example.coin.po.AnimeCharacter;
import com.example.coin.po.Entity;
import com.example.coin.po.Relation;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SpringBootTest
@RunWith(SpringRunner.class)
@Ignore
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class serviceTest {
    @Autowired
    private EntityService entityService;
    @Autowired
    private RelationService relationService;
    @Autowired
    private EntityRepository entityRepository;
    @Autowired
    private RelationRepository relationRepository;
    @Autowired
    private AnimeRepository animeRepository;
    @Autowired
    private AnimeCharacterRepository animeCharacterRepository;
    @Autowired
    private AnimeDirectorRepository animeDirectorRepository;
    @Autowired
    private AnimeCVRepository animeCVRepository;
    @Autowired
    private AnimeCompanyRepository animeCompanyRepository;

    @Test
    public void test01(){
        Entity entity1 = entityService.addEntity(new Entity("test 20210606"));
        Assert.assertNotNull(entity1.getId());
    }

    @Test
    public void test02(){
        int size = entityService.getAllEntities().size();
        entityService.deleteEntityById(entityRepository.findEntitiesByName("shabi").get(0).getId());
        Assert.assertEquals(size-1, entityService.getAllEntities().size());
    }

    @Test
    public void test03(){
        Entity entity1 = new Entity("yuzijiang", "person");
        HashMap<String, String>property1 = new HashMap<>();
        property1.put("age", "22");
        property1.put("sex", "male");
        entity1.setProperties(property1);
        Entity entity2 = new Entity("huixiang", "person");
        HashMap<String, String>property2 = new HashMap<>();
        property2.put("age", "20");
        property2.put("sex", "male");
        entity2.setProperties(property2);
        entityService.updateEntityById(entityRepository.findEntitiesByName("huixiang").get(0).getId(), entity2, true);
        entityService.updateEntityById(entityRepository.findEntitiesByName("yuzijiang").get(0).getId(), entity1, true);
        Assert.assertEquals("22", entityRepository.findEntitiesByName("yuzijiang").get(0).getProperties().get("age"));
        Assert.assertEquals("20", entityRepository.findEntitiesByName("huixiang").get(0).getProperties().get("age"));
    }

    @Test
    public void test04(){
        Entity yuzijiang = entityRepository.findEntitiesByName("yuzijiang").get(0);
        Entity huixiang = entityRepository.findEntitiesByName("huixiang").get(0);
        Relation friendly_to = relationService.addRelationship(new Relation(yuzijiang.getId(), huixiang.getId(), "friendly to"));
        Assert.assertNotNull(friendly_to.getId());
        yuzijiang = entityRepository.findEntitiesByName("yuzijiang").get(0);
        huixiang = entityRepository.findEntitiesByName("huixiang").get(0);
        Assert.assertEquals(1, yuzijiang.getRelatesTo().size());
        Assert.assertEquals(1, huixiang.getRelatesTo().size());
    }

    @Test
    public void test041(){
        Set<String> friendly = relationService.fuzzySearch("friendly");
        Assert.assertTrue(friendly.size()>0);
    }
    @Test
    public void test05(){
        int size = relationService.getAllRelationships().size();
        relationService.deleteRelationById(relationRepository.findRelationsByRelation("friendly to").get(0).getId());
        Assert.assertEquals(size-1, relationService.getAllRelationships().size());
    }

    @Test
    public void test06(){
        Entity yuzijiang = entityRepository.findEntitiesByName("yuzijiang").get(0);
        Entity huixiang = entityRepository.findEntitiesByName("huixiang").get(0);
        Relation friendly_to = relationService.addRelationship(new Relation(yuzijiang.getId(), huixiang.getId(), "friendly to"));
        entityService.deleteEntityById(yuzijiang.getId());
        huixiang = entityRepository.findEntitiesByName("huixiang").get(0);
        Assert.assertEquals(0, huixiang.getRelatesTo().size());
        Assert.assertNull(relationRepository.findRelationById(friendly_to.getId()));
    }

    @Test
    public void test07(){
        Set<String> huixiang = entityService.fuzzySearch("huxiang");
        Assert.assertEquals(1, huixiang.size());
    }

    @Test
    public void test08(){
        entityService.updateLocations(entityService.getAllEntities());

    }
    @Test
    public void test09(){
        entityService.deleteAllEntities();
        relationService.deleteAllRelationships();
    }
    @Test
    public void test10(){
        AnimeCharacter miyuki = animeCharacterRepository.findAnimeCharacterByNameContaining("佐助").get(0);
        if(miyuki!=null) {
            Entity miyukiEntity = entityRepository.findEntityByBgmIdAndType(miyuki.getCharacterId(), "AnimeCharacter");
            Map<String, String> relatesTo = miyukiEntity.getRelatesTo();
            for (String relationId : relatesTo.keySet()) {
                Relation relationById = relationRepository.findRelationById(relationId);
                Entity entityById = entityRepository.findEntityById(relatesTo.get(relationId));
                System.out.println(entityById.getName() + relationById.getRelation() + miyukiEntity.getName());
            }
        }
    }

    @Test
    public void test11(){
        AnimeCharacter mikuru = animeCharacterRepository.findAnimeCharacterByNameContaining("堇").get(0);
        System.out.println(mikuru.getDescription());
        System.out.println(mikuru.getBirthday());
    }
}
