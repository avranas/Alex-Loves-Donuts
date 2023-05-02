package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 2/24/2016.
 */
public class Star extends Pattern {

    private float ballSpeed;
    private FrequencyTimer ballFrequency;
    private FrequencyTimer ballCounter;
    protected float rotationSpeed;
    protected float offset;
    private int ballsPerRep;
    private float timer = 0;

    Star(float _ballSpeed, int _ballFrequency, int howMany, float _rotationSpeed, int _ballsPerRep){
        super();
        ballSpeed = _ballSpeed;
        //setRandomAngle();
        ballFrequency = new FrequencyTimer(_ballFrequency);
        ballCounter = new FrequencyTimer(howMany);
        rotationSpeed = _rotationSpeed;
        ballsPerRep = _ballsPerRep;
    }
    public void rotate(){
        offset += rotationSpeed;
    }

    public void patternLogic(Alex alex){
        rotate();
        if(ballFrequency.IncrementAndTest()){
            for(int counter = 0; counter != ballsPerRep; counter++) {
                Level.setCurrentAngle(((float)counter / (float)ballsPerRep) * 360 + offset);
                Level.addNewBadBall();
            }
            if(ballCounter.IncrementAndTest())
                finishPattern();
        }
    }
    public void startUniquePattern(){
        Level.setBallSpeed(ballSpeed);
    }

}
