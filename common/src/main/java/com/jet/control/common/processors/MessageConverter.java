package com.jet.control.common.processors;

import com.google.gson.Gson;
import com.jet.control.common.messages.Message;

// конвертирует сообщения из и в json в зависимости от типа сообщения
public class MessageConverter {
    private final Gson gson = new Gson();

    public String extractCode(String data) {
        return gson.fromJson(data, Message.class).getCode();
    }

    public <T extends Message> T extractMessage(String data, Class<T> tClass) {
        return gson.fromJson(data, tClass);
    }

    public String toJson(Object message) {
        return gson.toJson(message);
    }
}
