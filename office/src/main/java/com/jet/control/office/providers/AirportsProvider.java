package com.jet.control.office.providers;

import com.jet.control.common.beans.AirPort;
import com.jet.control.common.beans.RoutePoint;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Getter
@Component
@ConfigurationProperties(prefix = "application")
public class AirportsProvider {
    private final List<AirPort> airPorts = new ArrayList<>();

    public AirPort findAirportAndRemoveBoard(String boardName) {
        AtomicReference<AirPort> res = new AtomicReference<>();
        airPorts.stream()
                .filter(airport -> airport.getBoards().contains(boardName))
                .findFirst()
                .ifPresent(airport -> {
                    airport.removeBoard(boardName);
                    res.set(airport);
                });
        return res.get();
    }

    public AirPort getAirport(String airportName) {
        return airPorts.stream()
                .filter(airport -> airport.getName().equals(airportName))
                .findFirst()
                .orElse(new AirPort());
    }

    public RoutePoint getRoutePoint(String airportName) {
        return new RoutePoint(getAirport(airportName));
    }

}
