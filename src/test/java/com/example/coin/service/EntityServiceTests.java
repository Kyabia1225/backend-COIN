package com.example.coin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.coin.controller.EntityController;
import com.example.coin.pojo.Entity;
import com.example.coin.service.EntityService;
import static org.junit.Assert.*;

@SpringBootTest
class CoinApplicationTests {
	@Autowired
	EntityService service;

	@Test
	void contextLoads() {
	}
	@Test
	void addNodeTest(){
		Entity e1=new Entity("node1");
		EntityController controller=new EntityController();
		controller.addEntity(e1);
	}

}
