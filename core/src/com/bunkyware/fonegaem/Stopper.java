package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 3/7/2016.
 */
public class Stopper extends Pattern {

    private float ballSpeed;
    private FrequencyTimer frequency;
    private Countdown numberOfReps;
    private float lowEnd = -9999;
    private float  highEnd = 0;
    private final float WINDOW = 10;
    private final float numberOfBalls = 36;

    Stopper(float _ballSpeed, int _frequency, int _howMany) {
        ballSpeed = _ballSpeed;
        frequency = new FrequencyTimer(_frequency);
        numberOfReps = new Countdown(_howMany);
    }

    public void patternLogic(Alex alex) {
        if(lowEnd == -9999) {
            lowEnd = alex.getPositionInDegrees() - WINDOW;
            highEnd = alex.getPositionInDegrees() + WINDOW;
        }
        if(frequency.IncrementAndTest()) {

            float previousAngle = (float)Level.getCurrentAngle().value();
            Level.setCurrentAngle(lowEnd);
            Level.addNewBadBall();
            Level.setCurrentAngle(highEnd);
            Level.addNewBadBall();
            Level.setCurrentAngle(previousAngle);
            if(numberOfReps.DecrementAndTest())
                finishPattern();
        }
    }

    public void startUniquePattern(){
        Level.setBallSpeed(ballSpeed);
    }


}
