package com.jet.control.office.configs;

import com.github.benmanes.caffeine.cache.Cache;
import com.jet.control.common.processors.MessageConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class OfficeSocketConfiguration implements WebSocketConfigurer {

    private final KafkaTemplate<String, String> template;
    private final Cache<String, WebSocketSession> cache;
    private final MessageConverter messageConverter;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(new OfficeSocketHandler(template, cache, messageConverter), "/websocket")
                .setAllowedOrigins("*");
    }
}
