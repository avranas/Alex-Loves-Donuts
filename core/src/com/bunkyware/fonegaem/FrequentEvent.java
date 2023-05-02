package com.bunkyware.fonegaem;

import java.util.ArrayList;

/**
 * Created by Alexander on 5/14/2016.
 */
public class FrequentEvent extends GameEvent {
    FrequencyTimer timer;
    FrequentEvent(String eventType, ArrayList<String> instructions, int time) {
        super(eventType, instructions);
        timer = new FrequencyTimer(time);
    }

    boolean ready(){
        System.out.println(timer.time_left());
        return timer.IncrementAndTest();
    }
}
