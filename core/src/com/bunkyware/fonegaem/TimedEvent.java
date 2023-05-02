package com.bunkyware.fonegaem;

import com.badlogic.gdx.Game;

import java.util.ArrayList;

/**
 * Created by Alexander on 5/14/2016.
 */
public class TimedEvent extends GameEvent{
    int timeStamp;
    public TimedEvent(String eventType, ArrayList<String> instructions, int _timeStamp) {
        super(eventType, instructions);
        timeStamp = _timeStamp;
    }

    boolean ready(int gameTimer){
        return gameTimer >= timeStamp;
    }

}
