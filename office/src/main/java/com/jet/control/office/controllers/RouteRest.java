package com.jet.control.office.controllers;

import com.jet.control.common.beans.Route;
import com.jet.control.office.services.PathService;
import com.jet.control.office.services.WaitingRoutesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/routes")
public class RouteRest {

    private final PathService pathService;
    private final WaitingRoutesService waitingRoutesService;

    @PostMapping(path = "route")
    public void addRoute(@RequestBody List<String> routeLocations) {
        Route route = pathService.convertLocationsToRoute(routeLocations);
        waitingRoutesService.add(route);
    }
}
