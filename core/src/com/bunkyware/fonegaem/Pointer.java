package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 12/8/2015.
 */

class Pointer extends Pattern {
    public Flipper direction;
    public float rotationSpeed;
    public float ballSpeed;
    public FrequencyTimer flipTimer;
    protected final int maxFlipTime;
    protected final int minFlipTime;
    protected FrequencyTimer ballsLeft;
    protected FrequencyTimer shootTimer;
    private boolean halfOfBallsAreBad = false;
    private boolean badBallOn = false;

    protected void point(){
        if(direction.value())
            Level.getCurrentAngle().increase(rotationSpeed);
        else
            Level.getCurrentAngle().decrease(rotationSpeed);
        if(flipTimer.IncrementAndTest()) {
            direction.Flip();
            pickRandomFlipTime();
        }
    }

    public void makeHalfOfBallsBad(){
        halfOfBallsAreBad = true;
    }

    public void patternLogic(Alex alex){
        point();
        if(shootTimer.IncrementAndTest()) {
            if(badBallOn)
                Level.addNewBadBall();
            else if(halfOfBallsAreBad)
                Level.fire(BunkyGDX.coinFlip());
            else
                Level.addNewDonut();
            if(ballsLeft.IncrementAndTest())
                finishPattern();
        }
    }

    //Pointer (not anymore) automatically sets the angle on alex. Is this a good thing? (Nope)
    public void startUniquePattern(){
        //Level.setCurrentAngle(Alex.getPositionInDegrees());
        pickRandomFlipTime();
        Level.setBallSpeed(ballSpeed);
        //setRandomAngle();
        direction = new Flipper(BunkyGDX.coinFlip());
        direction.pickRandom();
    }

    protected void pickRandomFlipTime(){
        if(maxFlipTime != -1 && minFlipTime != -1)
            flipTimer = new FrequencyTimer(BunkyGDX.randomInt(minFlipTime, maxFlipTime));
        else
            flipTimer = new FrequencyTimer(-1);
    }

    Pointer(float _ballSpeed, int initFrequency, int howMany,
            float _rotationSpeed, int _minFlipTime, int _maxFlipTime, boolean _badBallOn) {
        ballSpeed = _ballSpeed;
        shootTimer = new FrequencyTimer(initFrequency);
        ballsLeft = new FrequencyTimer(howMany);
        rotationSpeed = _rotationSpeed;
        minFlipTime = _minFlipTime;
        maxFlipTime = _maxFlipTime;
        startUniquePattern();
        badBallOn = _badBallOn;
    }
}
