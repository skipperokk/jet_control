package com.jet.control.ship.jobs;

import com.jet.control.common.beans.Board;
import com.jet.control.common.beans.RoutePath;
import com.jet.control.common.messages.BoardStateMessage;
import com.jet.control.common.processors.MessageConverter;
import com.jet.control.ship.providers.BoardProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@EnableScheduling
@EnableAsync
@RequiredArgsConstructor
public class BoardsJob {
    private final BoardProvider boardProvider;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final MessageConverter messageConverter;

    @Scheduled(initialDelay = 0, fixedDelay = 250)
    public void fly() {
        boardProvider.getBoards().stream()
                .filter(Board::hasRoute)
                .forEach(board -> board.getRoute().getPath().stream()
                        .filter(RoutePath::inProgress)
                        .findFirst()
                        .ifPresent(routePath -> {
                            board.calculatePosition(routePath);
                            routePath.addProgress(board.getSpeed());
                            if (routePath.done()) {
                                board.setLocation(routePath.getTo().getName());
                            }
                        }));
    }

    @Async
    @Scheduled(initialDelay = 0, fixedDelay = 1000)
    public void notifyState() {
        boardProvider.getBoards().stream()
                .filter(Board::isBusy)
                .forEach(board -> {
                    Optional<RoutePath> routePath = board.getRoute().getPath().stream()
                            .filter(RoutePath::inProgress)
                            .findAny();
                    if (routePath.isEmpty()) {
                        List<RoutePath> paths = board.getRoute().getPath();
                        board.setLocation(paths.get(paths.size() - 1).getTo().getName());
                        board.setBusy(false);
                    }

                    BoardStateMessage message = new BoardStateMessage(board);
                    kafkaTemplate.sendDefault(messageConverter.toJson(message));
                });
    }
}
