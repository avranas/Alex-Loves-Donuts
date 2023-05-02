package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 12/8/2015.
 */

class Random extends Pattern {
    private FrequencyTimer ballCounter;
    private FrequencyTimer ballFrequency;
    private float ballSpeed;
    private boolean badBallOn;

    Random(float _ballSpeed, int initBallFrequency, int howMany, boolean _badBallOn){
        super();
        ballSpeed = _ballSpeed;
        //setRandomAngle();
        ballFrequency = new FrequencyTimer(initBallFrequency);
        ballCounter = new FrequencyTimer(howMany);
        badBallOn = _badBallOn;
    }

    public void patternLogic(Alex alex){
        if(ballFrequency.IncrementAndTest()){
            Level.fire(badBallOn);
            setRandomAngle();
            if(ballCounter.IncrementAndTest())
                finishPattern();
        }
    }
    public void startUniquePattern(){
        Level.setBallSpeed(ballSpeed);
    }
}