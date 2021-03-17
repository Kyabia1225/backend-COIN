package com.example.coin;

import com.example.coin.controller.EntityController;
import com.example.coin.pojo.Entity;
import com.example.coin.pojo.relationship;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.coin.vo.ResponseVO;

import static org.junit.Assert.*;

@SpringBootTest
class CoinApplicationTests {

	@Autowired
	EntityController entityController;

	@Test
	void findTest(){
		Entity e1 = new Entity("node1");
		Entity e2 = new Entity("node2");
		entityController.addEntity(e1);
		entityController.addEntity(e2);
		ResponseVO response =  entityController.addRelById(e1.getId(), e2.getId(),"connects");
		assertTrue(response.getSuccess());
		Long id = ((relationship)response.getContent()).getId();
		assertTrue(entityController.getRelationshipById(id).getSuccess());
		assertFalse(entityController.getRelationshipById(123l).getSuccess());
	}

}
