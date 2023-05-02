package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 12/31/2015.
 */

public class OpeningPointer extends Pointer {

    private float numberOfBalls;
    private float openingLength;
    private boolean onlySpitEdges;

    OpeningPointer(float _ballSpeed, int initFrequency, int howMany,
            float _rotationSpeed, int _minFlipTime, int _maxFlipTime, int _numberOfBalls,
                   float _openingLength, boolean _onlySpitEdges) {
        super(_ballSpeed, initFrequency, howMany, _rotationSpeed, _minFlipTime, _maxFlipTime, false);
        openingLength = _openingLength;
        numberOfBalls = _numberOfBalls;
        onlySpitEdges = _onlySpitEdges;
    }

    public void patternLogic(Alex alex) {
        point();
        if (shootTimer.IncrementAndTest()) {
            float previousAngle = (float)Level.getCurrentAngle().value();
            Angle lowAngleOpening = new Angle(Level.getCurrentAngle().value() - openingLength / 2);
            Angle highAngleOpening = new Angle(Level.getCurrentAngle().value() + openingLength / 2);
            if(onlySpitEdges){
                Level.setCurrentAngle((float) lowAngleOpening.value());
                Level.addNewBadBall();
                Level.setCurrentAngle((float) highAngleOpening.value());
                Level.addNewBadBall();
            }else {
                for (int counter = 0; counter != numberOfBalls; counter++) {
                    Angle newAngle = new Angle((float) counter * 360 / numberOfBalls);
                    Level.setCurrentAngle((float) newAngle.value());
                    if (lowAngleOpening.value() < highAngleOpening.value()) {
                        if (newAngle.value() < lowAngleOpening.value() || newAngle.value() > highAngleOpening.value())
                            Level.addNewBadBall();
                    } else {
                        if (newAngle.value() < lowAngleOpening.value() && newAngle.value() > highAngleOpening.value())
                            Level.addNewBadBall();
                    }
                }
            }
            Level.setCurrentAngle(previousAngle);
            if(ballsLeft.IncrementAndTest())
                finishPattern();
        }
    }


}
