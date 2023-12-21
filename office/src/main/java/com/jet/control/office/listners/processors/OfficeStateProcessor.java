package com.jet.control.office.listners.processors;

import com.jet.control.common.messages.AirPortStateMessage;
import com.jet.control.common.messages.OfficeStateMessage;
import com.jet.control.common.processors.MessageConverter;
import com.jet.control.common.processors.MessageProcessor;
import com.jet.control.office.providers.AirportsProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component("OFFICE_STATE")
@RequiredArgsConstructor
public class OfficeStateProcessor implements MessageProcessor<OfficeStateMessage> {
    private final MessageConverter messageConverter;
    private final AirportsProvider airportsProvider;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void process(String jsonMessage) {
        airportsProvider.getAirPorts().forEach(airport -> {
            kafkaTemplate.sendDefault(messageConverter.toJson(new AirPortStateMessage(airport)));
        });
    }
}
