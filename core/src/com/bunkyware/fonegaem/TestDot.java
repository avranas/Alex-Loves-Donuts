package com.bunkyware.fonegaem;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Alexander on 4/22/2016.
 */
public class TestDot extends Ball {
    static Texture dotTexture = new Texture("dot.png");

    //Shrink rate is 1 if you don't want it to shrink
    TestDot(float launchAngle, float speed){
        super(launchAngle, speed, dotTexture);
    }
}
