package com.bunkyware.fonegaem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

/**
 * Created by Alexander on 1/23/2016.
 */
public class AnimatedSprite {
    private ArrayList<Animation> animations = new ArrayList<>(0);
    private Sprite currentSprite;
    public Rectangle getBoundingRectangle(){
        return new Rectangle(currentSprite.getX(), currentSprite.getY(), getWidth(), getHeight());
    }

    //Only works if both height and width are the same.
    //I should move this to its own class later. CircularAnimatedSprite or something.
    public Circle getBoundingCircle(){
        Circle newCircle = new Circle(
                currentSprite.getX() + getBaseWidth() / 2,
                currentSprite.getY() + getBaseHeight() / 2,
                getWidth() / 2
        );
        return newCircle;
    }
    private int currentAnimation = 0;
    protected float animationTimer = 0;

    //Next: broken donut's dimensions are being set to the original image. 256x128. Whyyyyyyy?

    AnimatedSprite(float x, float y, Animation firstAnimation){
        addNewAnimation(firstAnimation);
        currentSprite = new Sprite(getTextureRegionInCurrentAnimation().getTexture());

        currentSprite.setX(x);
        currentSprite.setY(y);
        //currentSprite = new Sprite(getTextureRegionInCurrentAnimation());
    }

    //handle animation timer and update texture. Call once per loop with draw()
    public void animate(){
        animationTimer += Gdx.graphics.getDeltaTime();
        //Maybe I only need this when I change animations?
        //No, because I need to uhh, you know, animate the sprite.
        setSpriteTexture();
    }

    public Sprite getSprite(){
        return currentSprite;
    }

    public float getWidth(){
        return getBaseWidth() * currentSprite.getScaleX();
    }
    public float getHeight(){
        return getBaseHeight() * currentSprite.getScaleY();
    }
    //TIL regionwidth is not the same as regular-ass width
    private float getBaseWidth(){
        return (float)currentSprite.getWidth();
    }
    private float getBaseHeight(){
        return (float)currentSprite.getHeight();
    }
    public float getCenterX(){
        return (currentSprite.getX() + getWidth() / 2);
    }
    public float getCenterY(){
        return (currentSprite.getY()  + getHeight() / 2);
    }

/*    private TextureRegion getCurrentFrame(){
        return getCurrentAnimation().getKeyFrame(animationTimer);
    }*/
    protected Animation getCurrentAnimation(){
        return animations.get(currentAnimation);
    }
    private void setSpriteTexture(){
        //using SetRegion on a sprite resets "flip" and likely does other bad things as well.
        //But setRegion is the only way to give currentSprite the correct TextureRegion.
        //If more problems occur, I'll need to either fix it here, or find another way to animate sprites
        boolean flipX = currentSprite.isFlipX();
        boolean flipY = currentSprite.isFlipY();
        currentSprite.setRegion(getTextureRegionInCurrentAnimation());
        currentSprite.setFlip(flipX, flipY);
        //currentSprite.setTexture(getCurrentAnimation().getKeyFrame(animationTimer, true).getTexture());
    }
    private TextureRegion getTextureRegionInCurrentAnimation(){
       return getCurrentAnimation().getKeyFrame(animationTimer, true); //change this to add looping
    }

    //return position of new animation
    protected int addNewAnimation(Animation newAnimation){
        animations.add(newAnimation);
        return animations.size() - 1;
    }
    protected void switchToAnimation(int code){
        currentAnimation = code;
        animationTimer = 0;
        setSpriteTexture();
    }
}
