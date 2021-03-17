package com.example.coin;

import com.example.coin.controller.EntityController;
import com.example.coin.pojo.Entity;
import com.example.coin.pojo.relationship;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

@SpringBootTest
class CoinApplicationTests {

	@Autowired
	EntityController entityController;

	@Test
	void createEntityTest(){
		Entity e1 = new Entity("node1");
		Entity e2 = entityController.addEntity(e1);
		System.out.println(e2.getId());
		assertEquals(e1,e2);
	}

	@Test
	void deleteEntityTest(){
		entityController.deleteAllEntities();
		Entity e3 = new Entity("node2");
		entityController.addEntity(e3);
		entityController.deleteEntityById(e3.getId());
		assertEquals(0, entityController.getEntityList().size());
	}

	@Test
	void findEntityTest(){
		entityController.deleteAllEntities();
		Entity e4 = new Entity("node3");
		Entity e5 = entityController.addEntity(e4);
		System.out.println("e5: "+ e5.getId());
		Entity e6 = entityController.getEntityById(e5.getId());
		System.out.println("e6: "+ e6.getId());
		assertEquals(e5,e6);
	}

	@Test
	void RelationshipTest(){
		entityController.deleteAllEntities();
		Entity e1 = new Entity("zhangsan");
		Entity e2 = new Entity("lisi");
		entityController.addEntity(e1);
		entityController.addEntity(e2);
		relationship r = entityController.addRelById(e1.getId(), e2.getId(), "kills");
		assertEquals(1, entityController.getRelationList().size());
		assertEquals(r, entityController.getRelationshipById(r.getId()));
	}


	@After
	void testFinished(){
		entityController.deleteAllEntities();
		entityController.deleteAllRelationships();
	}



}
