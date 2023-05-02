package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 12/13/2015.
 */
public class BallRing extends Pattern {
    private float ballSpeed;
    private int numberOfBalls;
    private float lowAngleOpening;
    private float highAngleOpening;

    BallRing(float _ballSpeed, int _numberOfBalls, float _lowAngleOpening, float _highAngleOpening){
        super();
        lowAngleOpening = _lowAngleOpening;
        highAngleOpening = _highAngleOpening;
        ballSpeed = _ballSpeed;
        numberOfBalls = _numberOfBalls;
    }


    public void patternLogic(Alex alex){
        for(int counter = 0; counter != numberOfBalls; counter++){
            float newAngle = counter * 360 / numberOfBalls;
            Level.setCurrentAngle(newAngle);
            if(lowAngleOpening < highAngleOpening) {
                if (newAngle > lowAngleOpening && newAngle < highAngleOpening)
                    Level.addNewBadBall();
            }else {
                if (newAngle > lowAngleOpening || newAngle < highAngleOpening)
                    Level.addNewBadBall();
            }
        }
        finishPattern();
    }

    public void startUniquePattern(){
        Level.setBallSpeed(ballSpeed);
    }
}
