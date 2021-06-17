package com.example.coin.config;

import com.example.coin.core.CoreProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Value("${rootDirPath}")
    private String rootDirPath;

    @Bean(name = "coreProcessor")
    public CoreProcessor modelProcess() throws Exception{
        return new CoreProcessor(rootDirPath);
    }
}
