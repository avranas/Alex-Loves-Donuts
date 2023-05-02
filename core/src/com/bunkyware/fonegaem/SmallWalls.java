package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 3/12/2016.
 */
public class SmallWalls extends Pattern {
    private int numberOfWalls;
    private FrequencyTimer timeLeft = new FrequencyTimer(1);
    private float ballSpeed;
    private FrequencyTimer wallFrequency = new FrequencyTimer(0);
    private FrequencyTimer obstacleFrequency = new FrequencyTimer(0);
    Flipper side = new Flipper(true);
    private int ballsPerObstacle;

    SmallWalls(int _numberOfWalls, int time, float _ballSpeed,
               int _wallFrequency, int _obstacleFrequency, int _ballsPerObstacle){
        super();
        numberOfWalls = _numberOfWalls;
        timeLeft.ChangeHowOften(time);
        ballSpeed = _ballSpeed;
        wallFrequency.ChangeHowOften(_wallFrequency);
        obstacleFrequency.ChangeHowOften(_obstacleFrequency);
        ballsPerObstacle = _ballsPerObstacle;
    }

    //10 openings //20 reps
    //0-18, 19-36

    public void patternLogic(Alex alex){
        if(wallFrequency.IncrementAndTest()){
            for(int counter = 0; counter != numberOfWalls; counter++) {
                Level.setCurrentAngle((float)counter / (float)numberOfWalls * 360);
                Level.addNewBadBall();
            }
        }
        if(obstacleFrequency.IncrementAndTest()){
            for(int counter = 0; counter != numberOfWalls; counter++){
                float lowAngle = (float)counter / (float)numberOfWalls * 360;
                if(side.value())
                    lowAngle += 180 / numberOfWalls;
                float highAngle = lowAngle + 180 / numberOfWalls;
                for(int counterB = 1; counterB <= ballsPerObstacle; counterB++){
                    Level.setCurrentAngle(lowAngle + ((float)counterB / (float)ballsPerObstacle * (highAngle - lowAngle)));
                    Level.addNewBadBall();
                }
            }
            side.pickRandom();
        }
        if(timeLeft.IncrementAndTest())
            finishPattern();
    }
    public void startUniquePattern(){
        Level.setBallSpeed(ballSpeed);
    }
}
