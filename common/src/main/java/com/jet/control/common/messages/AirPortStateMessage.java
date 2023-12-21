package com.jet.control.common.messages;

import com.jet.control.common.beans.AirPort;
import com.jet.control.common.beans.Source;
import com.jet.control.common.beans.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AirPortStateMessage extends Message {
    private AirPort airPort;

    public AirPortStateMessage() {
        this.source = Source.AIRPORT;
        this.type = Type.STATE;
    }

    public AirPortStateMessage(AirPort airPort) {
        this();
        this.airPort = airPort;
    }
}
