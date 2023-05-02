package com.bunkyware.fonegaem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Alexander on 12/21/2015.
 */
public class Obstruction extends Sprite {

    private float speed;
    private Angle angle = new Angle(0);

    Obstruction(Texture texture, float x, float y, float _speed, float _angle){
        super(texture);
        speed = _speed;
        setCenterX(x);
        setCenterY(y);
        angle.setAngle(_angle);
    }

    public void move(){
        setX(getX() + speed * BunkyGDX.cosDegrees((float)angle.value()));
        setY(getY() + speed * BunkyGDX.sinDegrees((float) angle.value()));
    }

}
