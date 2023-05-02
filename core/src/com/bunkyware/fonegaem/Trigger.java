package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 5/4/2016.
 */
public class Trigger extends Pattern {
    private float _ballSpeed;
    private float _minDistance;
    private float _maxDistance;
    private boolean _randomBadBall;

    Trigger(float ballSpeed, float minDistance, float maxDistance, boolean randomBadBall){
        super();
        _ballSpeed = ballSpeed;
        _minDistance = minDistance;
        _maxDistance = maxDistance;
        _randomBadBall = randomBadBall;
    }

    //Note: BadBall shouldn't be allowed if you're planning on using this for 2-1
    public void patternLogic(Alex alex){
        float decision = (float)BunkyGDX.randomDouble(_minDistance, _maxDistance);
        if(BunkyGDX.coinFlip())
            decision *= -1;
        Level.setCurrentAngle(alex.getPositionInDegrees() + decision);
        if(_randomBadBall) {
            boolean result = BunkyGDX.coinFlip();
            if(result == true)
                Level.setCurrentAngle(alex.getPositionInDegrees() + _minDistance);
            Level.fire(result);
        }
        else
            Level.addNewDonut();
        finishPattern();
    }
    public void startUniquePattern(){
        Level.setBallSpeed(_ballSpeed);
    }
}
