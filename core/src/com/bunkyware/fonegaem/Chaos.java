package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 4/9/2016.
 */
public class Chaos extends Pattern {

    FrequencyTimer howMany = new FrequencyTimer(1);
    FrequencyTimer frequency = new FrequencyTimer(1);
    int numberAtATime = 0;
    float lowBallSpeed;
    float highBallSpeed;

    //Like an advanced random. Shoots multiple bad balls at a time, at varying speeds.
    Chaos(int _numberAtAtime, int _frequency, int _howMany, float _lowBallSpeed, float _highBallSpeed){
        howMany.ChangeHowOften(_howMany);
        frequency.ChangeHowOften(_frequency);
        numberAtATime = _numberAtAtime;
        lowBallSpeed = _lowBallSpeed;
        highBallSpeed = _highBallSpeed;
    }

    public void patternLogic(Alex alex) {
        if(frequency.IncrementAndTest()){
            float oldSpeed = Level.getBallSpeed();
            for(int counter = 0; counter != numberAtATime; counter++){
                Level.setBallSpeed((float)BunkyGDX.randomDouble(lowBallSpeed, highBallSpeed));
                setRandomAngle();
                Level.addNewBadBall();
            }
            Level.setBallSpeed(oldSpeed);
        }
    }
}
