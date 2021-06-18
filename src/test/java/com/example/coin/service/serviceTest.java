package com.example.coin.service;

import com.example.coin.DAO.*;
import com.example.coin.po.AnimeCV;
import com.example.coin.po.AnimeCharacter;
import com.example.coin.po.Entity;
import com.example.coin.po.Relation;
import com.example.coin.vo.EntityVO;
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
import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootTest

@RunWith(SpringRunner.class)
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
    private QuestionService questionService;
    @Ignore
    @Test
    public void test01(){
        Entity entity1 = entityService.addEntity(new Entity("test 20210606"));
        Assert.assertNotNull(entity1.getId());
    }
    @Ignore
    @Test
    public void test02(){
        int size = entityService.getAllEntities().size();
        entityService.deleteEntityById(entityRepository.findEntitiesByName("shabi").get(0).getId());
        Assert.assertEquals(size-1, entityService.getAllEntities().size());
    }
    @Ignore
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
    @Ignore
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
    @Ignore
    @Test
    public void test041(){
        Set<String> friendly = relationService.fuzzySearch("friendly");
        Assert.assertTrue(friendly.size()>0);
    }
    @Ignore
    @Test
    public void test05(){
        int size = relationService.getAllRelationships().size();
        relationService.deleteRelationById(relationRepository.findRelationsByRelation("friendly to").get(0).getId());
        Assert.assertEquals(size-1, relationService.getAllRelationships().size());
    }
    @Ignore
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
    @Ignore
    @Test
    public void test07(){
        Set<String> huixiang = entityService.fuzzySearch("huxiang");
        Assert.assertEquals(1, huixiang.size());
    }
    @Ignore
    @Test
    public void test08(){
        entityService.updateLocations(entityService.getAllEntities());

    }
    @Ignore
    @Test
    public void test09(){
        entityService.deleteAllEntities();
        relationService.deleteAllRelationships();
    }
    @Ignore
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
    @Ignore
    @Test
    public void test11(){
        List<EntityVO> associatedEntities = entityService.getAssociatedEntities("60bce771b138d704caa70619");
        System.out.println(associatedEntities);
    }

    @Test
    public void test12() throws Exception {
        String answer = questionService.answer("佐助也叫什么");
        System.out.println("_______________________________________________________________________");
        System.out.println(answer);
    }
    @Test
    public void testQ_19() throws Exception {
        String answer = questionService.answer("雪菜是谁");
        System.out.println("_______________________________________________________________________");
        System.out.println(answer);
    }
    @Test
    public void testQ_20() throws Exception {
        String answer = questionService.answer("小木曾雪菜生日是什么时候");
        System.out.println("_______________________________________________________________________");
        System.out.println(answer);
    }
    @Test
    public void testQ_21() throws Exception {
        String answer = questionService.answer("动画工房别名");
        System.out.println("_______________________________________________________________________");
        System.out.println(answer);
    }
    @Test
    public void testQ_22() throws Exception {
        String answer = questionService.answer("动画工房的创办日期是什么时候");
        System.out.println("_______________________________________________________________________");
        System.out.println(answer);
    }
    @Test
    public void testQ_23() throws Exception {
        String answer = questionService.answer("动画工房概况");
        System.out.println("_______________________________________________________________________");
        System.out.println(answer);
    }
    @Test
    public void testQ_24() throws Exception {
        String answer = questionService.answer("竹内顺子扮演的角色有哪些");
        System.out.println("_______________________________________________________________________");
        System.out.println(answer);
    }
    @Test
    public void testQ_25() throws Exception {
        String answer = questionService.answer("田中美海出演的动画有哪些");
        System.out.println("_______________________________________________________________________");
        System.out.println(answer);
    }
    @Test
    public void testQ_26() throws Exception {
        String answer = questionService.answer("佐助出演的动画有哪些");
        System.out.println("_______________________________________________________________________");
        System.out.println(answer);
    }
    @Test
    public void testQ_27() throws Exception {
        String answer = questionService.answer("漩涡鸣人的声优是谁");
        System.out.println("_______________________________________________________________________");
        System.out.println(answer);
    }
    @Test
    public void testQ_28() throws Exception {
        String answer = questionService.answer("动画工房制作的动画有哪些");
        System.out.println("_______________________________________________________________________");
        System.out.println(answer);
    }
    @Test
    public void testQ_29() throws Exception {
        String answer = questionService.answer("今敏执导的动画有哪些");
        System.out.println("_______________________________________________________________________");
        System.out.println(answer);
    }
    @Test
    public void testQ_30() throws Exception {
        String answer = questionService.answer("ahshd的制作公司是谁");
        System.out.println("_______________________________________________________________________");
        System.out.println(answer);
    }
    @Test
    public void testQ_31() throws Exception {
        String answer = questionService.answer("XXX的导演是谁");
        System.out.println("_______________________________________________________________________");
        System.out.println(answer);
    }
    @Test
    public void testQ_32() throws Exception {
        String answer = questionService.answer("评分>9.0的动画有哪些");
        System.out.println("_______________________________________________________________________");
        System.out.println(answer);
    }
    @Test
    public void testQ_33() throws Exception {
        String answer = questionService.answer("评分小于5.0的动画有哪些");
        System.out.println("_______________________________________________________________________");
        System.out.println(answer);
    }
}
