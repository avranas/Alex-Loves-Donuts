package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 3/10/2016.
 */
public class DeathWheel extends Pattern {

    private float solidSpeed;
    private float bulletSpeed;
    protected float solidRotationSpeed;
    private FrequencyTimer bulletFrequency;
    private FrequencyTimer solidFrequency;
    protected float angleOffset = 0;
    private Countdown timeLeft;
    private int solidPieces;

    DeathWheel(float _solidSpeed, float _bulletSpeed, int _solidFrequency, float _solidRotationSpeed,
               int _solidPieces, int _bulletFrequency, int totalTime){
        solidSpeed = _solidSpeed;
        bulletSpeed = _bulletSpeed;
        solidRotationSpeed = _solidRotationSpeed;
        bulletFrequency = new FrequencyTimer(_bulletFrequency);
        solidFrequency = new FrequencyTimer(_solidFrequency);
        timeLeft = new Countdown(totalTime);
        solidPieces = _solidPieces;
    }
    public void patternLogic(Alex alex){
        point();
        if(bulletFrequency.IncrementAndTest()){
            for(int counter = 0; counter != solidPieces; counter++) {
                Level.setCurrentAngle(((float)counter / (float)solidPieces) * 360);
                Level.setBallSpeed(bulletSpeed);
                Level.addNewBadBall();
            }
        }
        if(solidFrequency.IncrementAndTest()){
            for(int counter = 0; counter != solidPieces; counter++) {
                Level.setCurrentAngle(((float) counter / (float) solidPieces) * 360 + angleOffset);
                Level.setBallSpeed(solidSpeed);
                Level.addNewBadBall();
            }
        }
        if(timeLeft.DecrementAndTest()){
            timeLeft.reset();
            finishPattern();
        }
    }

    protected void point(){
        angleOffset += solidRotationSpeed;
    }



    public void startUniquePattern(){
        ;
    }

}
