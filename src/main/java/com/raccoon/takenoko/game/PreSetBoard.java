package com.raccoon.takenoko.game;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service("preSetBoard")
@Scope("prototype")
public class PreSetBoard extends HashBoard {

    public PreSetBoard() {
        super();
    }

}
