package com.jet.control.office.configs;

import com.github.benmanes.caffeine.cache.Cache;
import com.jet.control.common.messages.OfficeStateMessage;
import com.jet.control.common.processors.MessageConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class OfficeSocketHandler extends TextWebSocketHandler {
    private final KafkaTemplate<String, String> template;
    private final Cache<String, WebSocketSession> cache;
    private final MessageConverter messageConverter;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        if (session.isOpen()) {
            cache.put(session.getId(), session);
        }
        if (message.getPayload().equals("update")) {
            template.sendDefault(messageConverter.toJson(new OfficeStateMessage()));
        }
    }
}
