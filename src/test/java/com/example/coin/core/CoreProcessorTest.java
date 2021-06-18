package com.example.coin.core;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class CoreProcessorTest {
    @Autowired
    private CoreProcessor coreProcessor;
    @Test
    public void test01() throws Exception {
        String question = "伊莉雅是谁";
        System.out.println(coreProcessor.analysis(question));
    }
}
