package com.example.coin.controller;


import com.example.coin.pojo.Entity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@WebAppConfiguration
@SpringBootTest
@RunWith(SpringRunner.class)

class EntityControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    private void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void addEntity() throws Exception {
        String uri = "/api/coin/addEntity";
        Entity entity = new Entity("addEntityTest");
        //将参数转换成JSON对象
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(entity);
        //执行请求（使用POST请求，传递对象参数）
        MvcResult mvcResult = this.mockMvc
                .perform(MockMvcRequestBuilders.post(uri).content(json).contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        //获取返回编码
        int status = mvcResult.getResponse().getStatus();
        //获取返回结果
        String content = mvcResult.getResponse().getContentAsString();

        assertEquals(200, status);
        assertTrue(content.contains("true"));
        System.out.println(content);
    }

    @Test
    void deleteEntityById() throws Exception {
        String uri = "/api/coin/deleteEntity";
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(123l);
        MvcResult mvcResult = this.mockMvc
                .perform(MockMvcRequestBuilders.post(uri).content(json).contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

}