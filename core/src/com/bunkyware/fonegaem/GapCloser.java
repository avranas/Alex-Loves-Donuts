package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 1/19/2016.
 */
public class GapCloser extends Pattern {
    private float closingSpeed;
    private float endGap;
    private float currentGap = 360;
    private FrequencyTimer ballFrequency;
    private int numberOfBalls;

    //Call GapCloser to close the gap at current Angle
    GapCloser(float ballSpeed, float _closingSpeed, float _endGap, int _ballFrequency, int _numberOfBalls){
        closingSpeed = _closingSpeed;
        endGap = _endGap;
        ballFrequency = new FrequencyTimer(_ballFrequency);
        numberOfBalls = _numberOfBalls;
        Level.setBallSpeed(ballSpeed);
    }

    public void patternLogic(Alex alex){
        if(ballFrequency.IncrementAndTest()){
            Angle lowAngleOpening = new Angle(Level.getCurrentAngle().value() - currentGap / 2);
            Angle highAngleOpening = new Angle(Level.getCurrentAngle().value() + currentGap / 2);
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
            currentGap -= closingSpeed;
            if(currentGap <= endGap)
                finishPattern();
        }


    }
}
