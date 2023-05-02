package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 3/27/2016.
 */
public class Slalom extends Pattern {

    private FrequencyTimer frequency = new FrequencyTimer(1);
    private FrequencyTimer repsLeft = new FrequencyTimer(1);
    private float ballSpeed;
    private float angleOffset;
    private Flipper ballType = new Flipper(false);
    private Flipper donutSide = new Flipper(false);
    private float expandSpeed;


    Slalom(int _frequency, int reps, float _ballSpeed, float _angleOffset, float _expandSpeed){
        frequency.ChangeHowOften(_frequency);
        repsLeft.ChangeHowOften(reps);
        ballSpeed = _ballSpeed;
        angleOffset = _angleOffset;
        expandSpeed = _expandSpeed;
    }

    //10 openings //20 reps
    //0-18, 19-36

    public void patternLogic(Alex alex){
        if(frequency.IncrementAndTest()){
            if(ballType.value()){
                Level.addNewBadBall();
            }else{
                float previousAngle = (float)Level.getCurrentAngle().value();
                angleOffset += expandSpeed;
                if(donutSide.value()) {
                    Level.setCurrentAngle(previousAngle + angleOffset);
                    Level.addNewDonut();
                }else {
                    Level.setCurrentAngle(previousAngle - angleOffset);
                    Level.addNewDonut();
                }
                Level.setCurrentAngle(previousAngle);
                donutSide.Flip();
            }
            ballType.Flip();
            if(repsLeft.IncrementAndTest())
                finishPattern();
        }
    }
    public void startUniquePattern(){
        Level.setBallSpeed(ballSpeed);
    }


}
