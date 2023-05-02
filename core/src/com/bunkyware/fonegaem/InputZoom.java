package com.bunkyware.fonegaem;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by Alexander on 5/24/2016.
 */
public class InputZoom {
    static float magnitude = 0;

    static void reset(){
        magnitude = 0;
    }
    static void followInput(OrthographicCamera camera, boolean leftPressed, boolean rightPressed){
        if(leftPressed)
            camera.zoom /= magnitude;
        if(rightPressed)
            camera.zoom *= magnitude;
    }


}
