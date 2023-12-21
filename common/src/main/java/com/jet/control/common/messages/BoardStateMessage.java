package com.jet.control.common.messages;

import com.jet.control.common.beans.Board;
import com.jet.control.common.beans.Source;
import com.jet.control.common.beans.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardStateMessage extends Message{
    private Board board;

    public BoardStateMessage() {
        this.source = Source.BOARD;
        this.type = Type.STATE;
    }

    public BoardStateMessage(Board board) {
        this();
        this.board = board;
    }
}
