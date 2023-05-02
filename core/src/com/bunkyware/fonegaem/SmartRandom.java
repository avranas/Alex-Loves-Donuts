package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 4/11/2016.
 */
public class SmartRandom extends Pointer {

    final float gapWidth = 70;
    Flipper firstCycle = new Flipper(true);
    FrequencyTimer occasionalAimer = new FrequencyTimer(40);

    SmartRandom(float _ballSpeed, int initBallFrequency, int howMany, float rotationSpeed, int _minFlipTime, int maxFlipTime){
        super(_ballSpeed, initBallFrequency, howMany, rotationSpeed, _minFlipTime, maxFlipTime, true);
    }

    public void patternLogic(Alex alex){
        if(firstCycle.value()){
            firstCycle.setValue(false);
            Level.setCurrentAngle(alex.getPositionInDegrees());
        }
        point();
        if(shootTimer.IncrementAndTest()) {
            float oldAngle = (float) Level.getCurrentAngle().value();
            float decision = (float) BunkyGDX.randomDouble(0, Angle.FULL_ROTATION - gapWidth);
            Level.setCurrentAngle(oldAngle + (gapWidth / 2) + decision);
            System.out.println(decision + "****" + Level.getCurrentAngle().value());
            Level.addNewBadBall();
            Level.setCurrentAngle(oldAngle);
        }
        if(occasionalAimer.IncrementAndTest()){
            double oldAngle = Level.getCurrentAngle().value();
            Level.setCurrentAngle(alex.getPositionInDegrees());
            Level.addNewBadBall();
            Level.setCurrentAngle(oldAngle);
        }
    }
}
