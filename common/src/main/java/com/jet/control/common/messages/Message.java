package com.jet.control.common.messages;

import com.jet.control.common.beans.Source;
import com.jet.control.common.beans.Type;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Message {
    protected Type type;
    protected Source source;

    /**
     * Формируем информацию о сообщении
     *
     * @return
     */
    public String getCode(){
        return source.name() + "_" + type.name();
    }
}
