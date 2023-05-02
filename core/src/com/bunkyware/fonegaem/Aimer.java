package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 2/22/2016.
 */


public class Aimer extends Pattern {
    private FrequencyTimer ballCounter;
    private FrequencyTimer ballFrequency;
    private float ballSpeed;
    private boolean smart;
    private static float smartOffset = 10;

    Aimer(float _ballSpeed, int initBallFrequency, int howMany, boolean smart_){
        super();
        ballSpeed = _ballSpeed;
        //setRandomAngle();
        ballFrequency = new FrequencyTimer(initBallFrequency);
        ballCounter = new FrequencyTimer(howMany);
        smart = smart_;
    }

    public void patternLogic(Alex alex){
        if(ballFrequency.IncrementAndTest()){
            if(smart) {
                //System.out.println(Alex.getPositionInDegrees() + " *****" +
                //        Alex.getPositionInDegrees() + alex.getSpeed() * alex.circleDistance / ballSpeed);
                if(alex.getJustMovedLeft())
                    // alex speed per frame * ( ball speed / distance to edge)
                    Level.getCurrentAngle().setAngle(Alex.getPositionInDegrees() + alex.getSpeed() * alex.circleDistance / ballSpeed);
                else if(alex.getJustMovedRight())
                    Level.getCurrentAngle().setAngle(Alex.getPositionInDegrees() - alex.getSpeed() * alex.circleDistance / ballSpeed );
                else
                    Level.getCurrentAngle().setAngle(Alex.getPositionInDegrees());
            }
            else
                Level.getCurrentAngle().setAngle(Alex.getPositionInDegrees());
            Level.addNewBadBall();
            if(ballCounter.IncrementAndTest())
                finishPattern();
        }
    }
    public void startUniquePattern(){
        Level.setBallSpeed(ballSpeed);
    }
}

