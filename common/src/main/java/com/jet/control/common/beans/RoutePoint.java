package com.jet.control.common.beans;

import lombok.*;

@Data
public class RoutePoint {
    private String name;
    // точка маршрута
    private double x;
    private double y;

    public RoutePoint(AirPort airPort) {
        this.name = airPort.getName();
        this.x = airPort.getX();
        this.y = airPort.getY();
    }
}
