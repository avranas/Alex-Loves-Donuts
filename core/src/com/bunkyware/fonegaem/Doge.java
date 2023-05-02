package com.bunkyware.fonegaem;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Alexander on 12/24/2015.
 */
public class Doge extends Obstruction {
    public static final Texture dogePic = new Texture("doge.png");

    Doge(float x, float y, float _speed, float _angle){
        super(dogePic, x, y, _speed, _angle);
    }
}
