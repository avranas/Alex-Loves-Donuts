package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 2/26/2016.
 */
public class Walls extends Pattern {
    private int numberOfBalls;
    private float rotation;
    private int numberOfOpenings;
    private Countdown repsLeft;
    private float ballSpeed;
    private FrequencyTimer ballFrequency;
    private float ballOffset;

    Walls(float _ballSpeed, int _numberOfBalls, float _rotation, int _numberOfOpenings, int _ballFrequency, int numberOfReps){
        super();
        ballSpeed = _ballSpeed;
        numberOfBalls = _numberOfBalls;
        rotation = _rotation;
        numberOfOpenings = _numberOfOpenings;
        repsLeft = new Countdown(numberOfReps);
        ballFrequency = new FrequencyTimer(_ballFrequency);
        ballOffset = 0;
    }

    //10 openings //20 reps
    //0-18, 19-36

    public void patternLogic(Alex alex){
        //I don't think rotation is working the way I wanted?
        ballOffset += rotation;
        System.out.println(ballOffset);
        if(ballFrequency.IncrementAndTest()){
            float reps = numberOfOpenings * 2;
            float lengthOfReps = 360 / reps;
            for(int counter = 0; counter != numberOfBalls; counter++) {
                float newAngle = ((float)counter / (float)numberOfBalls * 360);
                Level.getCurrentAngle().setAngle(newAngle + ballOffset);
                int currentRep = (int)(newAngle / lengthOfReps);
                if(currentRep % 2 == 0)
                    Level.addNewBadBall();
            }
            if(repsLeft.DecrementAndTest())
                finishPattern();
        }
    }
    public void startUniquePattern(){
        Level.setBallSpeed(ballSpeed);
    }
}
