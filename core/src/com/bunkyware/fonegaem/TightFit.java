package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 3/29/2016.
 */
public class TightFit extends Pattern {

    FrequencyTimer frequency = new FrequencyTimer(1);
    FrequencyTimer space = new FrequencyTimer(1);
    FrequencyTimer reps = new FrequencyTimer(1);
    float ballSpeed;
    int currentStep = 0;

    TightFit(int _frequency, int _space, int _reps, float speed){
        frequency.ChangeHowOften(_frequency);
        space.ChangeHowOften(_space);
        reps.ChangeHowOften(_reps);
        ballSpeed = speed;
    }

    //10 openings //20 reps
    //0-18, 19-36

    public void patternLogic(Alex alex){
        if(currentStep == 0){
            if(frequency.IncrementAndTest()){
                currentStep = 1;
                Level.addNewBadBall();
            }
        }
        if(currentStep == 1){
            if(space.IncrementAndTest()){
                currentStep = 2;
                Level.addNewDonut();
            }
        }
        if(currentStep == 2){
            if(space.IncrementAndTest()){
                currentStep = 0;
                Level.addNewBadBall();
                if(reps.IncrementAndTest())
                    finishPattern();
            }
        }
    }
    public void startUniquePattern(){
        Level.setBallSpeed(ballSpeed);
    }
}
