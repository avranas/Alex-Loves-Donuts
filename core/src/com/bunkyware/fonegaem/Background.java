package com.bunkyware.fonegaem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.ArrayList;

/**
 * Created by Alexander on 12/23/2015.
 */
public class Background {

    //ArrayList<Sprite> pics = new ArrayList<Sprite>(0);
    //ArrayList<Animation> pics = new ArrayList<Animation>(0);
    private float speed;
    private Angle angle = new Angle(0);
   // static Texture texture = new Texture("donut.png");
    private TextureAtlas coolBg;
    private float scale = 1;
    Animation pic;
    //private static Animation coolBgAnimation = new Animation(0.5f, coolBg.getRegions());
    private float animationTimer = 0;
    SpriteBatch bgBatch = new SpriteBatch();
    private float offsetX = 0;
    private float offsetY = 0;

    Background(String atlasPath, float _speed, float _angle, float picScale){
        setBackground(atlasPath);
        speed = _speed;
        angle.setAngle(_angle);
        scale = picScale;
/*        int maxX = (Gdx.graphics.getWidth() / texture.getWidth()) + 2;
        int maxY = (Gdx.graphics.getHeight() / texture.getHeight()) + 1;
        for(int counterX = 0; counterX != maxX + 1; counterX++) {
            for(int counterY = 0; counterY != maxY + 1; counterY++){
                Animation newAnimation = new Animation(0.5f, coolBg.getRegions());
                 newAnimation.setCenter(
                         Gdx.graphics.getWidth() * ((float) counterX / (float) maxX),
                         Gdx.graphics.getHeight() * ((float) counterY / (float) maxY));
                 pics.add(newSprite);*/
        //    }
        //}
    }

    //I might need a dispose() or destructor.

    public void move(){
        offsetX += speed * BunkyGDX.cosDegrees((float) angle.value());
        offsetY += speed * BunkyGDX.sinDegrees((float) angle.value());
        float width = pic.getKeyFrame(animationTimer).getRegionWidth() * scale;
        float height = pic.getKeyFrame(animationTimer).getRegionHeight() * scale;
        if(offsetX > width)
            offsetX -= width;
        if(offsetY > height)
            offsetY -= height;
        if(offsetX < -width)
            offsetX += width;
        if(offsetY < -height)
            offsetY += height;
/*        for(Sprite pic : pics) {
            pic.setX(pic.getX() + speed * BunkyGDX.cosDegrees((float) angle.value()));
            pic.setY(pic.getY() + speed * BunkyGDX.sinDegrees((float) angle.value()));
            if(pic.getX() > Gdx.graphics.getWidth())
                pic.setX(-texture.getWidth());
            if(pic.getY() > Gdx.graphics.getHeight())
                pic.setY(-texture.getHeight());
        }*/
    }

    public void changeScale(int newScale){
        scale = newScale;
    }

    public void setBackground(String newPath){
        StringBuilder realPath = new StringBuilder("animation/");
        realPath.append(newPath);
        coolBg = new TextureAtlas(realPath.toString());
        pic = new Animation(0.5f, coolBg.getRegions());
    }

    public void draw(){
        animationTimer += Gdx.graphics.getDeltaTime();
        bgBatch.begin();
        float width = pic.getKeyFrame(animationTimer).getRegionWidth() * scale;
        float height = pic.getKeyFrame(animationTimer).getRegionHeight() * scale;
        for(int counterX = -1; counterX != (int)(Gdx.graphics.getWidth() / width) + 1; counterX++){
            for(int counterY = -1; counterY != (int)(Gdx.graphics.getHeight() / height) + 1; counterY++) {
                bgBatch.draw(
                        pic.getKeyFrame(animationTimer, true),
                        offsetX + (width * counterX),
                        offsetY + (height * counterY),
                        width,
                        height
                );
            }
        }
        bgBatch.end();
/*        bgBatch.begin();
        for(Sprite pic : pics) {
            pic.draw(bgBatch);
        }
        bgBatch.end();*/
    }

}
