package com.jet.control.office.services;

import com.jet.control.common.beans.Route;
import com.jet.control.common.beans.RoutePath;
import com.jet.control.common.beans.RoutePoint;
import com.jet.control.office.providers.AirportsProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PathService {
    private final AirportsProvider airportsProvider;

    public Route convertLocationsToRoute(List<String> locations) {
        Route route = new Route();
        List<RoutePath> routePaths = new ArrayList<>();
        List<RoutePoint> routePoints = new ArrayList<>();

        locations.forEach(location -> airportsProvider.getAirPorts()
                .stream()
                .filter(airport -> airport.getName().equals(location))
                .findFirst()
                .ifPresent(airport -> routePoints.add(new RoutePoint(airport))));

        for (int i = 0; i < routePoints.size() - 1; i++) {
            routePaths.add(new RoutePath());
        }

        routePaths.forEach(row -> {
            int indx = routePaths.indexOf(row);
            if (Objects.isNull(row.getFrom())) {
                row.setFrom(routePoints.get(indx));
                if (routePoints.size() > indx + 1) {
                    row.setTo(routePoints.get(indx + 1));
                } else {
                    row.setTo(routePoints.get(indx));
                }
            }
        });
        route.setPath(routePaths);
        return route;
    }

    public RoutePath makePath(String from, String to) {
        return new RoutePath(airportsProvider.getRoutePoint(from), airportsProvider.getRoutePoint(to), 0);
    }
}
