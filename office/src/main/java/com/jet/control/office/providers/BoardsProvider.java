package com.jet.control.office.providers;

import com.jet.control.common.beans.Board;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Component
public class BoardsProvider {
    private final List<Board> boardList = new ArrayList<>();
    // так как addBoard вызывается на основе событий в кафке, то это не гаратирует,
    // что события будут обработаны одновременно с одним самолетом, порядок важен
    private final Lock lock = new ReentrantLock(true);

    public Optional<Board> getBoard(String boardName) {
        return boardList.stream()
                .filter(board -> board.getName().equals(boardName))
                .findFirst();
    }

    public List<Board> getBoards() {
        return boardList;
    }

    public void addBoard(Board board) {
        try {
            lock.lock();
            Optional<Board> optionalBoard = getBoard(board.getName());
            if (optionalBoard.isPresent()) {
                int indx = boardList.indexOf(optionalBoard.get());
                boardList.set(indx, board);
            } else {
                boardList.add(board);
            }
        } finally {
            lock.unlock();
        }
    }
}
