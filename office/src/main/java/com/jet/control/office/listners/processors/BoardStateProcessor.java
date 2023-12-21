package com.jet.control.office.listners.processors;

import com.jet.control.common.beans.AirPort;
import com.jet.control.common.beans.Board;
import com.jet.control.common.beans.Route;
import com.jet.control.common.messages.AirPortStateMessage;
import com.jet.control.common.messages.BoardStateMessage;
import com.jet.control.common.processors.MessageConverter;
import com.jet.control.common.processors.MessageProcessor;
import com.jet.control.office.providers.AirportsProvider;
import com.jet.control.office.providers.BoardsProvider;
import com.jet.control.office.services.WaitingRoutesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component("BOARD_STATE")
@RequiredArgsConstructor
public class BoardStateProcessor implements MessageProcessor<BoardStateMessage> {

    private final MessageConverter messageConverter;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private final WaitingRoutesService waitingRoutesService;
    private final BoardsProvider boardsProvider;
    private final AirportsProvider airportsProvider;


    @Override
    public void process(String jsonMessage) {
        BoardStateMessage message = messageConverter.extractMessage(jsonMessage, BoardStateMessage.class);
        Board board = message.getBoard();
        Optional<Board> previousBoardOptional = boardsProvider.getBoard(board.getName());
        AirPort airport = airportsProvider.getAirport(board.getLocation());

        boardsProvider.addBoard(board);
        if (previousBoardOptional.isPresent() && board.hasRoute() && !previousBoardOptional.get().hasRoute()){
            Route route = board.getRoute();
            waitingRoutesService.remove(route);
        }

        if (previousBoardOptional.isEmpty() || !board.isBusy() && previousBoardOptional.get().isBusy()){
            airport.addBoard(board.getName());
            kafkaTemplate.sendDefault(messageConverter.toJson(new AirPortStateMessage(airport)));
        }
    }
}
