package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 3/25/2016.
 */
public class PulseWithRangeOfSpeed extends Pattern {

    private int numberOfBalls;
    private FrequencyTimer ballFrequency;
    private float lowSpeed;
    private float highSpeed;
    private int numberOfCorners;
    private FrequencyTimer numberOfPulses;
    boolean doesAlternate;
    Flipper alternater = new Flipper(false);

    PulseWithRangeOfSpeed(int _numberOfBalls, int frequency, float _lowSpeed,
               float _highSpeed, int _numberOfCorners, int _numberOfPulses, boolean _alternates){
        numberOfBalls = _numberOfBalls;
        ballFrequency = new FrequencyTimer(frequency);
        lowSpeed = _lowSpeed;
        highSpeed = _highSpeed;
        numberOfCorners = _numberOfCorners;
        numberOfPulses = new FrequencyTimer(_numberOfPulses);
        doesAlternate = _alternates;
    }

    //10 openings //20 reps
    //0-18, 19-36

    public void patternLogic(Alex alex){
        if(ballFrequency.IncrementAndTest()){
            float previousAngle = (float)Level.getCurrentAngle().value();
            float previousSpeed = Level.getBallSpeed();
            float reps = numberOfCorners;
            float lengthOfReps = 360 / reps;
            float low = lowSpeed;
            float high = highSpeed;
            if(doesAlternate)
                alternater.Flip();
            if(alternater.value()) {
                low = highSpeed;
                high = lowSpeed;
            }
                for(int counter = 0; counter != numberOfBalls; counter++) {
                    float newAngle = ((float)counter / (float)numberOfBalls * 360);
                    System.out.println(newAngle);
                    int currentRep = (int)(newAngle / lengthOfReps);
                    Level.getCurrentAngle().setAngle(newAngle);
                    float percentThruRep = (newAngle - currentRep * lengthOfReps) / lengthOfReps;
                    Level.setBallSpeed((  percentThruRep      * ( high - low)) + low);
                    Level.fire(true);

            }
            Level.setBallSpeed(previousSpeed);
            Level.setCurrentAngle(previousAngle);
            if(numberOfPulses.IncrementAndTest())
                finishPattern();
        }
    }


}
