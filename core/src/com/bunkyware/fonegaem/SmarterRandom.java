package com.bunkyware.fonegaem;

import java.util.ArrayList;

/**
 * Created by Alexander on 4/17/2016.
 */
public class SmarterRandom extends Pattern {

    float ballSpeed;
    float safeZoneWidth;
    float sectionWidth;
    float rotationSpeed;
    FrequencyTimer ballFrequency = new FrequencyTimer(0);
    FrequencyTimer repsLeft = new FrequencyTimer(0);
    ArrayList<Float> safepoints = new ArrayList<>(0);
    ArrayList<Flipper> directions = new ArrayList<>(0);

    //Pick random angle 0 - 360
    //if the angle falls in a safe zone, reposition outside in the section.
    //Random double 0 to (sectionWidth - safeZoneWidth)


    SmarterRandom(int _howManyReps, float _ballSpeed, int _ballFrequency, int _sections, float _safeZoneWidth, float _rotationSpeed){
        repsLeft.ChangeHowOften(_howManyReps);
        ballSpeed = _ballSpeed;
        ballFrequency.ChangeHowOften(_ballFrequency);
        safeZoneWidth = _safeZoneWidth;
        rotationSpeed = _rotationSpeed;
        sectionWidth = Angle.FULL_ROTATION / (float)_sections;
        for(int counter = 0; counter != _sections; counter++){
            safepoints.add((float)BunkyGDX.randomDouble(counter * sectionWidth, (counter + 1) * sectionWidth));
            directions.add(new Flipper(BunkyGDX.coinFlip()));
        }

    }

    public void patternLogic(Alex alex) {
        for(int counter = 0; counter != directions.size(); counter++){
            directions.get(counter).setValue(BunkyGDX.coinFlip());
            float lowEnd = counter * sectionWidth;
            float highEnd = (counter + 1) * sectionWidth;
/*            if (safepoints.get(counter) < lowEnd)
                directions.get(counter).setValue(true);
            if (safepoints.get(counter) > highEnd)
                directions.get(counter).setValue(false);*/
        }
        for(int counter = 0; counter != safepoints.size(); counter++){
            if (directions.get(counter).value())
                safepoints.set(counter, safepoints.get(counter) + (rotationSpeed));
            else
                safepoints.set(counter, safepoints.get(counter) - (rotationSpeed));
        }

        if(ballFrequency.IncrementAndTest()){
            for(int counter = 0; counter != safepoints.size(); counter++){

                Level.setCurrentAngle(safepoints.get(counter));
                Level.addNewTestDot();

                float lowEnd = (safepoints.get(counter) - safeZoneWidth / 2);
                float highEnd = (safepoints.get(counter) + safeZoneWidth / 2);
                float decision = (float) BunkyGDX.randomDouble(counter * sectionWidth, ((counter + 1) * (sectionWidth)) - safeZoneWidth);
                if (decision > lowEnd && decision < highEnd) {
                    decision += safeZoneWidth;
                }
                Level.setCurrentAngle(decision);
                Level.addNewBadBall();
                if(repsLeft.IncrementAndTest())
                    finishPattern();
            }
        }
    }

}
