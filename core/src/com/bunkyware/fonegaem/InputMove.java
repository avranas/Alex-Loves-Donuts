package com.bunkyware.fonegaem;

import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by Alexander on 5/27/2016.
 */
public class InputMove {
    static float magnitude = 0;
    static float xTranslation = 0;

    static void reset(){
        magnitude = 0;
        xTranslation = 0;
    }
    static void followInput(OrthographicCamera camera, float cameraPositionX, float cameraPositionY, boolean leftPressed, boolean rightPressed){
        if(leftPressed)
            xTranslation += magnitude;
        if(rightPressed)
            xTranslation -= magnitude;

        //Will this work in combination with bump? If not, that's fine, as long as I don't make
            //a level that uses both.
        camera.position.set(cameraPositionX + xTranslation,
                cameraPositionY,
                0);
    }



}
