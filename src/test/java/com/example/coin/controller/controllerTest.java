package com.example.coin.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.coin.DAO.EntityRepository;
import com.example.coin.DAO.RelationRepository;
import com.example.coin.po.Entity;
import com.example.coin.po.Relation;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class controllerTest {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private EntityRepository entityRepository;
    @Autowired
    private RelationRepository relationRepository;
    private MockMvc mockMvc;
    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void test01() throws Exception {
        Entity entity = new Entity("yuzijiang", "person");
        String requestJson = JSONObject.toJSONString(entity);
        mockMvc.perform(post("/api/coin/addEntity")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();

    }

    @Test
    public void test02()throws Exception{
        String id = entityRepository.findEntitiesByName("yuzijiang").get(0).getId();
        mockMvc.perform(get("/api/coin/getEntity")
                .param("id", id)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();
    }


    @Test
    public void test03()throws Exception{
        mockMvc.perform(get("/api/coin/entitySearch")
                .param("keyword", "yuzijng")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();
    }


    @Test
    public void test04()throws Exception{
        String id = entityRepository.findEntitiesByName("yuzijiang").get(0).getId();
        mockMvc.perform(post("/api/coin/deleteEntity")
                .param("id",id)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();
    }

    @Test
    public void test05()throws Exception{
        Entity entity1 = new Entity("yuzijiang", "person");
        Entity entity2 = new Entity("huixiang", "person");
        String requestJson = JSONObject.toJSONString(entity1);
        mockMvc.perform(post("/api/coin/addEntity")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andReturn();
        requestJson = JSONObject.toJSONString(entity2);
        mockMvc.perform(post("/api/coin/addEntity")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andReturn();
        String id1 = entityRepository.findEntitiesByName("yuzijiang").get(0).getId();
        String id2 = entityRepository.findEntitiesByName("huixiang").get(0).getId();
        mockMvc.perform(post("/api/coin/addRelationship")
                .param("source", id1)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("target", id2)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("relation", "friendly to"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();
    }

    @Test
    public void test06()throws Exception{
        mockMvc.perform(get("/api/coin/listRelationships"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();
    }

    @Test
    public void test07()throws Exception{
        mockMvc.perform(get("/api/coin/relationSearch")
                .param("keyword", "firendly t")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();
    }

    @Test
    public void test08()throws Exception{
        String id = relationRepository.findRelationsByRelation("friendly to").get(0).getId();
        mockMvc.perform(post("/api/coin/delRelationship2")
                .param("id", id)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();
    }





}
