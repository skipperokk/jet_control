package com.jet.control.common.beans;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Route {
    private String boardName;
    private List<RoutePath> path = new ArrayList<>();

    public boolean notAssigned(){
        return boardName == null;
    }
}
