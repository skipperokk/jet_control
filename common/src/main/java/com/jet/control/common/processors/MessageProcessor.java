package com.jet.control.common.processors;

import com.jet.control.common.messages.Message;

public interface MessageProcessor<T extends Message> {

    void process(String jsonMessage);
}
