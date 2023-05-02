package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 3/8/2016.
 */
public class SineStar extends Star {
    private float timer = 0;
    private float waveWidth = 0;

    SineStar(float _ballSpeed, int _ballFrequency, int howMany, float _rotationSpeed, int _ballsPerRep, float _waveWidth) {
        super(_ballSpeed, _ballFrequency, howMany, _rotationSpeed, _ballsPerRep);
        waveWidth = _waveWidth;
    }

    public void rotate(){
        timer += rotationSpeed;
        offset += BunkyGDX.sinDegrees(timer) * waveWidth;
    }
}
