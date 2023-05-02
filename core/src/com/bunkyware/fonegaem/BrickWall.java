package com.bunkyware.fonegaem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Alexander on 6/13/2016.
 */
public class BrickWall extends Sprite {

    boolean startedOnTop;
    private float velocityY = 2;
    static Texture brickWallTexture = new Texture("brick wall.png");
    BrickWall(boolean startsOnTop, float _speed){
        super(brickWallTexture);
        startedOnTop = startsOnTop;
        if(startsOnTop)
            setY(BunkyGDX.GAME_HEIGHT);
        else
            setY(0);
        //setScale(4, 4);
        setX(BunkyGDX.midPointX());
    }
    void move(){
        Rectangle rect = getBoundingRectangle();

/*        if(startedOnTop) {
            setY(getY() - velocityY);
            //if(rect.getY() - rect.getHeight() / 2 < BunkyGDX.midPointY())
             //   setY(BunkyGDX.midPointY());
        }
        else
            setY(getY() + velocityY);
       //     if(rect.getY() + rect.getHeight() / 2  > BunkyGDX.midPointY())
        //        setY(BunkyGDX.midPointY());*/
    }

}
