package com.youscada.youstream;

import com.youscada.youstream.kafka.consumers.ConsumerConfigHelper;
import com.youscada.youstream.util.JsonHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ConsumerConfigHelper consumerConfigHelper() {
        return new ConsumerConfigHelper();
    }

    @Bean
    public JsonHelper jsonHelper() {
        return new JsonHelper();
    }

}
