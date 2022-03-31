package com.dan.reactivejava.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ReactiveConfiguration {

    @Bean("client")
    public WebClient getWebClient(){
        return WebClient.create("http://localhost:80");
    }

}
