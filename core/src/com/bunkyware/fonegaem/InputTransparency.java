package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 5/25/2016.
 */
public class InputTransparency {
    static void reset(){
        currentTransparency = 1;
        magnitude = 0;
    }
    static float magnitude = 0;
    static private float currentTransparency = 1;
    static public float getCurrentTransparency(){
        if(currentTransparency > 2)
            return 0;
        if(currentTransparency < 0)
            return 0;
        if(currentTransparency > 1)
            return 1 - (currentTransparency - 1);
        else
            return currentTransparency;
    }
    static void logic(boolean leftPressed, boolean rightPressed) {
        if (leftPressed)
            currentTransparency -= magnitude;
        if (rightPressed)
            currentTransparency += magnitude;
    }
}
