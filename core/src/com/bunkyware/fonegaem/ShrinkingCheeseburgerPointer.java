package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 1/10/2016.
 */

//Makes a Pointer that shoots shrinking donuts
public class ShrinkingDonutPointer extends Pointer {

    private float shrinkRate;

    public void patternLogic(Alex alex){
        point();
        if(shootTimer.IncrementAndTest()) {
            Level.addNewShrinkingDonut(shrinkRate);
            if(ballsLeft.IncrementAndTest())
                finishPattern();
        }
    }

    ShrinkingDonutPointer(float _ballSpeed, int initFrequency, int howMany,
            float _rotationSpeed, int minFlipTime, int maxFlipTime, float _shrinkRate) {
        super(_ballSpeed, initFrequency, howMany, _rotationSpeed, minFlipTime, maxFlipTime, false);
    shrinkRate = _shrinkRate;

    }
}
