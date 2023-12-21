package com.jet.control.office.jobs;

import com.jet.control.common.beans.AirPort;
import com.jet.control.common.beans.Board;
import com.jet.control.common.beans.Route;
import com.jet.control.common.beans.RoutePath;
import com.jet.control.common.messages.AirPortStateMessage;
import com.jet.control.common.messages.OfficeRouteMessage;
import com.jet.control.common.processors.MessageConverter;
import com.jet.control.office.providers.AirportsProvider;
import com.jet.control.office.providers.BoardsProvider;
import com.jet.control.office.services.PathService;
import com.jet.control.office.services.WaitingRoutesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class RouteDistributeJob {

    private final PathService pathService;
    private final BoardsProvider boardsProvider;
    private final WaitingRoutesService waitingRoutesService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final MessageConverter messageConverter;
    private final AirportsProvider airportsProvider;

    @Scheduled(initialDelay = 500, fixedDelay = 2500)
    private void distribute() {
        waitingRoutesService.getRoutes().stream()
                .filter(Route::notAssigned)
                .forEach(route -> {
                    String startLocation = route.getPath().get(0).getFrom().getName();

                    boardsProvider.getBoards().stream()
                            .filter(board -> board.noBusy() && startLocation.equals(board.getLocation()))
                            .findFirst()
                            .ifPresent(board -> sendBoardToRoute(route, board));

                    if (route.notAssigned()) {
                        boardsProvider.getBoards().stream()
                                .filter(Board::noBusy)
                                .findFirst()
                                .ifPresent(board -> {
                                    String currentLocation = board.getLocation();
                                    if (!currentLocation.equals(startLocation)) {
                                        RoutePath routePath = pathService.makePath(currentLocation, startLocation);
                                        route.getPath().add(0, routePath);
                                    }
                                    sendBoardToRoute(route, board);
                                });
                    }
                });
    }

    private void sendBoardToRoute(Route route, Board board) {
        route.setBoardName(board.getName());
        AirPort airport = airportsProvider.findAirportAndRemoveBoard(board.getName());
        board.setLocation(null);
        kafkaTemplate.sendDefault(messageConverter.toJson(new OfficeRouteMessage(route)));
        kafkaTemplate.sendDefault(messageConverter.toJson(new AirPortStateMessage(airport)));
    }
}
