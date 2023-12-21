package com.jet.control.ship.listeners.processors;

import com.jet.control.common.messages.BoardStateMessage;
import com.jet.control.common.processors.MessageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("BOARD_STATE")
public class BoardStateProcessor implements MessageProcessor<BoardStateMessage> {

    @Override
    public void process(String jsonMessage) {
        //ignore
    }
}
