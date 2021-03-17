package com.example.coin.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.coin.controller.EntityController;
import com.example.coin.pojo.Entity;
import com.example.coin.service.EntityService;

import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest
class EntityServiceTests {
	@Autowired
	EntityService service;
	@Autowired
	EntityController controller;

	@Test
	void createTest(){
		Entity e1=new Entity("node1");
		controller.addEntity(e1);
		Long id=e1.getId();
		assertEquals(service.findEntityById(id).get().getName(), "node1");
	}


	@Test
	void findByIdTest(){
		Entity e1=new Entity("node2");
		service.createEntity(e1);
		Long id=e1.getId();
		assertNotNull(id);
	}
	@Test
	void findAllTest(){
		Entity e1=new Entity("node2");
		service.createEntity(e1);
		assertTrue(service.findAllEntities().size()>0);
	}
	@Test
	void deleteByIdTest(){
		Entity e1=new Entity("node3");
		service.createEntity(e1);
		Long id=e1.getId();
		service.deleteEntityById(id);
	}


}