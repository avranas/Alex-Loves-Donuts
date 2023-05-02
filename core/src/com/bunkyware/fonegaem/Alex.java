package com.bunkyware.fonegaem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.particles.influencers.RegionInfluencer;

import javafx.scene.shape.Circle;

/**
 * Created by Alexander on 1/7/2016.
 */

public class Alex extends AnimatedSprite{

    private static final float alexSpeed = 4.7f;
    private static final TextureAtlas eatingAtlas = new TextureAtlas("animation/alex eating.atlas");
    private static final Animation eatingAnimation = new Animation(0.1f, eatingAtlas.getRegions());
    private static final TextureAtlas idleAtlas = new TextureAtlas("animation/Alex Idle.atlas");
    private static final Animation alexAnimationIdle = new Animation(0.2f, idleAtlas.getRegions());
    public static final double circleDistance = 464;
    public static final float defaultScale = 0.4f;
    public static float maxWinLevelScale = 3f;
    public static float maxWinPartScale = 2f;
    

    //I made these for smart Aimer
    private boolean justMovedLeft = false;
    private boolean justMovedRight = false;

    //There is only one Alex and I need to get his position sometimes :(
    private static boolean movedLeftLast = true;
    private static Countdown eatingTimer = new Countdown(30);
    private static float positionInDegrees = 270;


    public static float getPositionInDegrees(){
        return positionInDegrees;
    }

    public static void putInDefaultPosition(){
        positionInDegrees = 270;
    }

/*    public TextureRegion getCurrentFrame(){
        eatingTimer.DecrementAndTest();
        if(!eatingTimer.time_is_up())
            return eatingAnimation.getKeyFrame(animationTimer, true);
        else
            return eatingAnimation.getKeyFrame(0);
    }*/

    public void startEating(){
        eatingTimer.reset();
        switchToAnimation(1);
    }

/*    public float getPositionInDegrees(){
        return positionInDegrees;
    }*/

    Alex(float x, float y){
        super(x, y, alexAnimationIdle);
        addNewAnimation(eatingAnimation);
        getSprite().setScale(defaultScale);
    }

    public void stopEating(){
        eatingTimer.reset();
        switchToAnimation(0);
    }

    public void alexLogic(){
        if(eatingTimer.DecrementAndTest()){
            stopEating();
        }
        getSprite().setCenterX(BunkyGDX.midPointX() + (float) (circleDistance * BunkyGDX.cosDegrees(positionInDegrees)));
        getSprite().setCenterY(BunkyGDX.midPointY() + (float) (circleDistance * BunkyGDX.sinDegrees(positionInDegrees)));

    }

    public void lookAtPoint(FloatPoint here){
        FloatPoint middle = new FloatPoint(getCenterX(), getCenterY());
        if(movedLeftLast)
            getSprite().setFlip(false, false);
        else
            getSprite().setFlip(false, true);
        getSprite().setRotation(Angle.angleBetweenPoints(middle, here) + 180);
    }

    public float getSpeed(){
        return alexSpeed;
    }

    public void moveLeft(){
        positionInDegrees += alexSpeed;
        movedLeftLast = true;
        justMovedLeft = true;
        justMovedRight = false;
    }

    public void moveRight(){
        positionInDegrees -= alexSpeed;
        movedLeftLast = false;
        justMovedRight = true;
        justMovedLeft = false;
    }

    public void stayPut(){
        justMovedLeft = false;
        justMovedRight = false;
    }

    public boolean getJustMovedLeft(){
        return justMovedLeft;
    }
    public boolean getJustMovedRight(){
        return justMovedRight;
    }

}
