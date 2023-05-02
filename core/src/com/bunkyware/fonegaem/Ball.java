package com.bunkyware.fonegaem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;

/**
 * Created by Alexander on 11/21/2015.
 */

public class Ball extends CircleSprite{

    private Angle angle;
    private final static float spawnPointX = BunkyGDX.midPointX();
    private final static float spawnPointY = BunkyGDX.midPointY();
    private float distanceTraveled = 0;
    private float speed;
    private float alpha = 1;

    void setTransparency(float thisMuch){
        alpha = thisMuch;
    }

    float getAlpha(){
        return alpha;
    }

    void removeTransparency(){
        alpha = 1;
    }

    void increaseTransparencyAfterSomeDistance(float byThisMuch, float afterThisMuch){
        if(alpha > 0 && distanceTraveled >= afterThisMuch) {
            alpha -= byThisMuch;
        }
    }

    Ball(float launchAngle, float _speed, Texture ballTexture){
        super(ballTexture);
        setCenterX(spawnPointX);
        setCenterY(spawnPointY);
        angle = new Angle(launchAngle);
        speed = _speed;
    }

    void move() {
        setX(getX() + speed * BunkyGDX.cosDegrees((float) angle.value()));
        setY(getY() + speed * BunkyGDX.sinDegrees((float) angle.value()));
        distanceTraveled += speed;
    }

}
