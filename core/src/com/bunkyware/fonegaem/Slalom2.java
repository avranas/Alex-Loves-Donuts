package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 4/13/2016.
 */
public class Slalom2 extends Pattern {

    FrequencyTimer fenceBallFrequency = new FrequencyTimer(1);
    FrequencyTimer fenceBalls = new FrequencyTimer(0);
    FrequencyTimer breakTimeLeft = new FrequencyTimer(0);
    boolean breakTimeOn = false;
    int ballSpeed;
    final float donutDistance = 20;
    Flipper side = new Flipper(false);


    Slalom2(int _fenceBalls, int _fenceBallFrequency, int _breakTime, int _ballSpeed){
        fenceBalls = new FrequencyTimer(_fenceBalls);
        fenceBallFrequency.ChangeHowOften(_fenceBallFrequency);
        ballSpeed = _ballSpeed;
        breakTimeLeft.ChangeHowOften(_breakTime);
    }


    public void patternLogic(Alex alex) {
        if (breakTimeOn) {
            if (breakTimeLeft.IncrementAndTest()) {
                breakTimeOn = false;
            }
        } else {
            if (fenceBallFrequency.IncrementAndTest()) {
                if (fenceBalls.IncrementAndTest()) {
                    fenceBalls.reset();
                    breakTimeLeft.reset();
                    breakTimeOn = true;
                }
                Level.addNewBadBall();

                System.out.println(fenceBalls.time_left() + "***" + fenceBalls.howOften() / 2);

                if (fenceBalls.time_left() == fenceBalls.howOften() / 2) {
                    float oldAngle = (float) Level.getCurrentAngle().value();
                    float offset = donutDistance;
                    if (side.value())
                        offset -= donutDistance * 2;
                    side.Flip();
                    Level.setCurrentAngle(oldAngle + offset);
                    Level.addNewDonut();
                    Level.setCurrentAngle(oldAngle);
                }
            }
        }
    }
    public void startUniquePattern(){
        Level.setBallSpeed(ballSpeed);
    }

}
