package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 3/31/2016.
 */
public class OneIsGood extends Pattern {

    int numberOfSlots;
    FrequencyTimer reps = new FrequencyTimer(1);
    float ballSpeed;
    float openingWidth;
    FrequencyTimer frequency = new FrequencyTimer(1);

    float initialAngle = -999;


    OneIsGood(int _frequency, int _numberOfSlots, float _ballSpeed, float _openingWidth, int _reps){
        numberOfSlots = _numberOfSlots;
        ballSpeed = _ballSpeed;
        openingWidth = _openingWidth;
        reps.ChangeHowOften(_reps);
        frequency.ChangeHowOften(_frequency);
    }


    public void patternLogic(Alex alex){
        if(frequency.IncrementAndTest()) {
            if (initialAngle == -999) {
                initialAngle = alex.getPositionInDegrees();
            }
            int decision = BunkyGDX.randomInt(0, numberOfSlots - 1);
            for (int counter = 0; counter != numberOfSlots; counter++) {
                float newAngle = (initialAngle - openingWidth / 2) + openingWidth * ((float) counter / (float) numberOfSlots - 1);
                Level.setCurrentAngle(newAngle);
                if (counter == decision)
                    Level.addNewDonut();
                else
                    Level.addNewBadBall();
            }
            if (reps.IncrementAndTest())
                finishPattern();
        }
    }

    public void startUniquePattern(){
        Level.setBallSpeed(ballSpeed);
    }

}
