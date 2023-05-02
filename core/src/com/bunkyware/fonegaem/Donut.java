package com.bunkyware.fonegaem;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Alexander on 12/6/2015.
 */
public class Donut extends Ball {
    private float shrinkRate;
    static Texture donutTexture = new Texture("donut.png");

    //Shrink rate is 1 if you don't want it to shrink
    Donut(float launchAngle, float speed, float _shrinkRate){
        super(launchAngle, speed, donutTexture);
        shrinkRate = _shrinkRate;
    }

    public void shrink(){
        setScale(getScaleX() * shrinkRate, getScaleY() * shrinkRate);
    }
}
