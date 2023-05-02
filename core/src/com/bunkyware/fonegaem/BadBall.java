package com.bunkyware.fonegaem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;

/**
 * Created by Alexander on 12/6/2015.
 */
public class BadBall extends Ball {

    static Texture badBallTexture = new Texture("bad ball.png");
    BadBall(float launchAngle, float speed){
        super(launchAngle, speed, badBallTexture);

        //I'm hardcoding the ball's collision circle here. This is important.
        Circle badBallCircle = new Circle(24, 24, 18);
        setCollisionCircle(badBallCircle);
    }
}
