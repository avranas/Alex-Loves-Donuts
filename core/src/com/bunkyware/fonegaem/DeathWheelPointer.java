package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 3/10/2016.
 */
public class DeathWheelPointer extends DeathWheel {

    public DeathWheelPointer(
            float _solidSpeed, float _bulletSpeed, int _solidFrequency,
            float _solidRotationSpeed, int _solidPieces, int _bulletFrequency, int totalTime,
            int minFlipFrequency, int maxFlipFrequency
            ) {
        super(_solidSpeed, _bulletSpeed, _solidFrequency, _solidRotationSpeed,
                _solidPieces, _bulletFrequency, totalTime);
        minFlipTime = minFlipFrequency;
        maxFlipTime = maxFlipFrequency;
        pickRandomFlipTime();
    }

    private void pickRandomFlipTime(){
        direction.Flip();
        flipTimer.ChangeHowOften(BunkyGDX.randomInt(minFlipTime, maxFlipTime));
    }

    public Flipper direction = new Flipper(BunkyGDX.coinFlip());
    public FrequencyTimer flipTimer = new FrequencyTimer(1);
    public int maxFlipTime;
    public int minFlipTime;

    protected void point(){
        if(direction.value())
            angleOffset += solidRotationSpeed;
        else
            angleOffset -= solidRotationSpeed;
        if(flipTimer.IncrementAndTest()) {
            pickRandomFlipTime();
        }
    }

}
