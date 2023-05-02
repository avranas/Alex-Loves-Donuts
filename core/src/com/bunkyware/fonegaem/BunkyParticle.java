package com.bunkyware.fonegaem;

import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * Created by Alexander on 1/28/2016.
 */
public class BunkyParticle extends AnimatedSprite {

    private int timeOnScreen = 0;
    BunkyParticle(float x, float y, Animation animation, int _timeOnScreen){
        super(x, y, animation);
        timeOnScreen = _timeOnScreen;
    }

    BunkyParticle(float x, float y, Animation animation){
        super(x, y, animation);
        timeOnScreen = -1;
    }



    public boolean readyToDelete(){
        if(timeOnScreen == -1)
            return getCurrentAnimation().isAnimationFinished(animationTimer);
        timeOnScreen--;
        return timeOnScreen == 0;
    }


}
