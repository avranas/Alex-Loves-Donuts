package com.bunkyware.fonegaem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;

/**
 * Created by Alexander on 1/21/2016.
 */
public class TextBox {
    public static BitmapFont mainGameFont;
    public static BitmapFont smallMainGameFont;
    public static BitmapFont resultsFont;
    public static BitmapFont goldFont;
    private float x;
    private float y;
    private String text;
    private BitmapFont currentFont;
    private GlyphLayout gl = new GlyphLayout();
    float scaleX = 1;
    float scaleY = 1;

    public String getText(){
        return text;
    }

    public void setScale(float newScaleX, float newScaleY){
        scaleX = newScaleX;
        scaleY = newScaleY;
    }

    //Load all your fonts here.
    static void loadFonts()
    {
        FreeTypeFontGenerator calibri = new FreeTypeFontGenerator(Gdx.files.internal("Calibri.ttf"));
        FreeTypeFontGenerator oblivious = new FreeTypeFontGenerator(Gdx.files.internal("Oblivious.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter mainGameParam = new FreeTypeFontGenerator.FreeTypeFontParameter();
        mainGameParam.size = 96;
        mainGameParam.color = Color.BLUE;
        mainGameParam.shadowColor = Color.BLACK;
        mainGameParam.shadowOffsetX = 4;
        mainGameParam.shadowOffsetY = 4;
        mainGameFont = calibri.generateFont(mainGameParam);
        mainGameParam.size = 48;
        smallMainGameFont = calibri.generateFont(mainGameParam);

        FreeTypeFontGenerator.FreeTypeFontParameter resultsParam = new FreeTypeFontGenerator.FreeTypeFontParameter();
        resultsParam.size = 192;
        resultsParam.color = Color.RED;
        resultsParam.shadowColor = Color.BLACK;
        resultsParam.shadowOffsetX = 4;
        resultsParam.shadowOffsetY = 4;
        resultsFont = calibri.generateFont(resultsParam);

        FreeTypeFontGenerator.FreeTypeFontParameter goldParam = new FreeTypeFontGenerator.FreeTypeFontParameter();
        goldParam.size = 72;
        goldParam.color = Color.GOLD;
        goldParam.shadowColor = Color.BROWN;
        goldParam.shadowOffsetX = 4;
        goldParam.shadowOffsetY = 4;
        goldFont = calibri.generateFont(goldParam);
    }

    TextBox(float _x, float _y, String _text, BitmapFont font){
        x = _x;
        y = _y;
        text = _text;
        currentFont = font;
        updateGlyph();
    }

    private void updateGlyph(){
        gl.setText(currentFont, text);

    }

    void draw(SpriteBatch batch){
        float oldScaleX = currentFont.getData().scaleX;
        float oldScaleY = currentFont.getData().scaleY;
        currentFont.getData().setScale(scaleX, scaleY);
        currentFont.draw(batch, text, x, y );
        currentFont.getData().setScale(oldScaleX, oldScaleY);

    }

    void setX(float newX){
        x = newX;
    }

    void setY(float newY){
        y = newY;
    }

    void setText(String newText){
        text = newText;
        updateGlyph();
    }

    void placeOnLeft(float bufferX){
        x = bufferX;
    }

    //THIS NEEDS TO BE CHANGED BACK
    void placeOnRight(float bufferX){
       // x = BunkyGDX.GAME_WIDTH - bufferX - gl.width;
        x = Gdx.graphics.getWidth() - bufferX - gl.width;
    }
    //THIS NEEDS TO BE CHAAANGED BAAACK
    void placeOnTop(float bufferY){
        //y = BunkyGDX.GAME_HEIGHT - bufferY;
        y = Gdx.graphics.getHeight() - bufferY;
    }
    void placeOnBottom(float bufferY){
        y = bufferY + gl.height;
    }

    //THIS TOO THIS TOOO
    void placeInMiddleX(){
        updateGlyph();
        //x = (BunkyGDX.GAME_WIDTH / 2 ) - gl.width / 2;
        x = (Gdx.graphics.getWidth() / 2 ) - gl.width / 2;
    }

    //TODO: Maybe the parameter should be % from top. Look into dpi? idk
    void placeInMiddleY(){
        updateGlyph();
        y = (BunkyGDX.GAME_HEIGHT / 2 ) + gl.height / 2;
    }

}
