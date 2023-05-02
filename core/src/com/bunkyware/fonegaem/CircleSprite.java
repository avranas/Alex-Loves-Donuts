package com.bunkyware.fonegaem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;


/**
 * Created by Alexander on 11/27/2015.
 */

public class CircleSprite extends Sprite {

    CircleSprite(Texture texture, Circle circleInSprite) {
        super(texture);
        collisionCircle = circleInSprite;
    }

    CircleSprite(Texture texture) {
        super(texture);
        collisionCircle = new Circle(
                getTexture().getWidth() / 2,
                getTexture().getHeight() / 2,
                getTexture().getHeight() / 2
        );
    }

    protected void setCollisionCircle(Circle newCircle){
        collisionCircle = newCircle;
    }

    Circle getCollisionCircle(){
/*        Circle returnThis = collisionCircle;
        returnThis.x += getX();
        returnThis.y += getY();
        return returnThis;*/
        Circle returnThis = new Circle();
        returnThis.x = getX() + collisionCircle.x;
        returnThis.y = getY() + collisionCircle.y;
        returnThis.radius = collisionCircle.radius * scale;
        return returnThis;
    }

    public void setScale(float newScale){
        super.setScale(newScale);
        scale = newScale;
    }

    private Circle collisionCircle;
    private float scale = 1;
}
