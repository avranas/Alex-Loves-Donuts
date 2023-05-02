package com.bunkyware.fonegaem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.awt.Point;
import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by Alexander on 11/24/2015.
 */
public class MenuButton {
    private BitmapFont font;
    private String text;
    private float levelFrequency;
    private float levelSpeed;
    private float positionX;
    private float positionY;

    MenuButton(float x, float y, String newText, float _levelFrequency, float _levelSpeed){
        font = new BitmapFont();
        text = newText;
        levelFrequency = _levelFrequency;
        levelSpeed = _levelSpeed;
        positionX = x;
        positionY = y;

    }

    boolean pointIsInBox(float pointX, float pointY){
        Rectangle button = new Rectangle();
        TextureRegion region = font.getRegion();
        button.setX(positionX);
        button.setY(positionY);
        button.setWidth(region.getRegionWidth());
        button.setHeight(region.getRegionHeight());
        Rectangle input = new Rectangle();
        input.setX(pointX);
        input.setY(pointY);
        input.setWidth(1);
        input.setHeight(1);
        //System.out.println(pointX + "***" + pointY);
        return input.overlaps(button);
    }

    String getText(){
        return text;
    }

    float getLevelFrequency(){
        return levelFrequency;
    }

    float getLevelSpeed(){
        return levelSpeed;
    }

    void draw(Batch batch){

    }

}
