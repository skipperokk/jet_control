package com.jet.control.common.messages;

import com.jet.control.common.beans.Route;
import com.jet.control.common.beans.Source;
import com.jet.control.common.beans.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfficeRouteMessage extends Message{
    private Route route;

    public OfficeRouteMessage() {
        this.source = Source.OFFICE;
        this.type = Type.ROUTE;
    }

    public OfficeRouteMessage(Route route) {
        this();
        this.route = route;
    }
}
