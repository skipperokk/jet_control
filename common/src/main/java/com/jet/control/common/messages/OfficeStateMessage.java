package com.jet.control.common.messages;

import com.jet.control.common.beans.Route;
import com.jet.control.common.beans.Source;
import com.jet.control.common.beans.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfficeStateMessage extends Message {
    private Route route;

    public OfficeStateMessage() {
        this.source = Source.OFFICE;
        this.type = Type.STATE;
    }
}
