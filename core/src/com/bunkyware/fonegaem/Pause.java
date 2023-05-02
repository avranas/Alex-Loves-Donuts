package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 12/8/2015.
 */

class Pause extends Pattern {

    FrequencyTimer timeLeft;
    public Pause(int howLong) {
        timeLeft = new FrequencyTimer(howLong);
    }

    public void patternLogic(Alex alex){
        if(timeLeft.IncrementAndTest())
            finishPattern();
    }
}