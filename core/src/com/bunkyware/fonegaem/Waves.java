package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 3/31/2016.
 */
public class Waves extends Pattern {
    FrequencyTimer waveFrequency = new FrequencyTimer(1);
    FrequencyTimer donutFrequency = new FrequencyTimer(1);
    int waveCount;
    int badBallsPerRep;
    float badBallSpeed;
    float cbSpeed;


    Waves(float _badBallSpeed, int _waveFrequency, int _donutFrequency, int _waveCount, int _badBallsPerRep, float _cbSpeed){
        cbSpeed = _cbSpeed;
        badBallSpeed = _badBallSpeed;
        waveFrequency.ChangeHowOften(_waveFrequency);
        donutFrequency.ChangeHowOften(_donutFrequency);
        waveCount = _waveCount;
        badBallsPerRep = _badBallsPerRep;
    }


    public void patternLogic(Alex alex){
        if(waveFrequency.IncrementAndTest()) {
            float oldSpeed = Level.getBallSpeed();
            double oldAngle = Level.getCurrentAngle().value();
            for (int counter = 0; counter != badBallsPerRep; counter++) {
                float newAngle = (float)counter / (float)badBallsPerRep * Angle.FULL_ROTATION;

                float reps = waveCount * 2;
                float lengthOfReps = Angle.FULL_ROTATION / reps;

                int currentRep = (int) (newAngle / lengthOfReps);
                if (currentRep % 2 == 0) {
                    Level.setBallSpeed(badBallSpeed);
                    Level.setCurrentAngle(newAngle);
                    Level.addNewBadBall();
                }
            }
            Level.setBallSpeed(oldSpeed);
            Level.setCurrentAngle(oldAngle);
        }
        if(donutFrequency.IncrementAndTest()){
            setRandomAngle();
            Level.addNewDonut();
        }

    }
    public void startUniquePattern(){
        Level.setBallSpeed(cbSpeed);
    }
}
