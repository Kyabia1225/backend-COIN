package com.example.coin;

import com.example.coin.controller.EntityController;
import com.example.coin.pojo.Entity;
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
	void createTest(){
		Entity e1 = new Entity("node1");
		Entity e2 = entityController.addEntity(e1);
		assertEquals(e1,e2);
	}

	@Test
	void deleteTest(){
		entityController.deleteAll();
		Entity e3 = new Entity("node2");
		entityController.addEntity(e3);
		entityController.deleteEntityById(e3.getId());
		assertEquals(0, entityController.getEntityList().size());
	}

	@Test
	void findTest(){
		entityController.deleteAll();
		Entity e4 = new Entity("node3");
		Entity e5 = entityController.addEntity(e4);
		System.out.println("e5: "+ e5.getId());
		Entity e6 = entityController.getEntityById(e5.getId());
		System.out.println("e6: "+ e6.getId());
		assertEquals(e5,e6);
	}
	@After
	void testFinished(){
		entityController.deleteAll();
	}




}
