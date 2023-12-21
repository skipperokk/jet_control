package com.jet.control.ship.listeners.processors;

import com.jet.control.common.beans.Route;
import com.jet.control.common.messages.OfficeRouteMessage;
import com.jet.control.common.processors.MessageConverter;
import com.jet.control.common.processors.MessageProcessor;
import com.jet.control.ship.providers.BoardProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("OFFICE_ROUTE")
@RequiredArgsConstructor
public class OfficeRouteProcessor implements MessageProcessor<OfficeRouteMessage> {
    private final MessageConverter messageConverter;
    private final BoardProvider boardProvider;

    @Override
    public void process(String jsonMessage) {
        OfficeRouteMessage message = messageConverter.extractMessage(jsonMessage, OfficeRouteMessage.class);
        Route route = message.getRoute();
        boardProvider.getBoards().stream()
                .filter(board -> board.noBusy() && route.getBoardName().equals(board.getName()))
                .findFirst()
                .ifPresent(board -> {
                    board.setRoute(route);
                    board.setBusy(true);
                    board.setLocation(null);
                });
    }
}
