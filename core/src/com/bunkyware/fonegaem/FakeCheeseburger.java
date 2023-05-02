package com.bunkyware.fonegaem;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Alexander on 12/24/2015.
 */
public class FakeDonut extends Obstruction {
    //lowers alpha like a ghost whenever alex hovers over it

    static final float lowestAlpha = 0.25f;
    static final float highestAlpha = 1;
    static final float deltaAlpha = 0.1f;
    float currentAlpha = 1;
    static Texture texture = new Texture("donut.png");

    FakeDonut(float x, float y, float _speed, float _angle){
        super(texture, x, y, _speed, _angle);
    }

    void lowerAlpha(){
        if(currentAlpha > lowestAlpha)
            currentAlpha -= deltaAlpha;
        if(currentAlpha < lowestAlpha)
            currentAlpha = lowestAlpha;
    }
    void recoverAlpha(){
        if(currentAlpha < highestAlpha)
            currentAlpha += deltaAlpha;
        if(currentAlpha > highestAlpha)
            currentAlpha = highestAlpha;
    }

    float getAlpha(){
        return currentAlpha;
    }
}
