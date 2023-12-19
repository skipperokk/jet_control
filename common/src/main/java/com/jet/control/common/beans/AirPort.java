package com.jet.control.common.beans;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AirPort {
    private String name;
    private List<String> boards = new ArrayList<>();
    // координаты аэропрта
    private int x;
    private int y;

    public void addBoard(String boardName) {
        int indx = boards.indexOf(boardName);
        if (indx >= 0) {
            boards.set(indx, boardName);
        } else {
            boards.add(boardName);
        }
    }

    public void removeBoard(String boardName){
        boards.remove(boardName);
    }
}
