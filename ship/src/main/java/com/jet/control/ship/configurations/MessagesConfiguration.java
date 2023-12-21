package com.jet.control.ship.configurations;

import com.jet.control.common.processors.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagesConfiguration {

    @Bean
    public MessageConverter converter(){
        return new MessageConverter();
    }
}
