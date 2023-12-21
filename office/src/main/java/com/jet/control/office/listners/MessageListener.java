package com.jet.control.office.listners;

import com.github.benmanes.caffeine.cache.Cache;
import com.jet.control.common.messages.Message;
import com.jet.control.common.processors.MessageConverter;
import com.jet.control.common.processors.MessageProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageListener {
    private final MessageConverter messageConverter;
    private final Cache<String, WebSocketSession> sessionCache;

    @Autowired
    private Map<String, MessageProcessor<? extends Message>> processors = new HashMap<>();

    @KafkaListener(id = "OfficeGroupId", topics = "OfficeRoutes")
    public void kafkaListen(String data) {
        sendKafkaMessageToSocket(data);
        String code = messageConverter.extractCode(data);
        try {
            processors.get(code).process(data);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
    }

    private void sendKafkaMessageToSocket(String data) {
        sessionCache.asMap().values()
                .forEach(webSocketSession -> {
                    try {
                        if (webSocketSession.isOpen()) {
                            webSocketSession.sendMessage(new TextMessage(data));
                        }
                    } catch (IOException e) {
                        log.error(e.getLocalizedMessage());
                    }
                });
    }
}
