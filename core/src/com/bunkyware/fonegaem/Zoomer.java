package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 5/3/2016.
 */
public class Zoomer {
    static float targetZoom = 1;
    static float zoomSpeed = 0;
    static float currentZoom = 1;
    static float pulseMagnitude = 0;
    static float pulseSpeed = 0;
    static float pulseTimer = 1;

    static void reset(){
        targetZoom = 1;
        zoomSpeed = 0;
        currentZoom = 1;
        pulseMagnitude = 0;
        pulseSpeed = 0;
        pulseTimer = 1;

    }

    static void pulseTarget(){
        pulseTimer += pulseSpeed;
        currentZoom = 1 + pulseMagnitude * BunkyGDX.sinDegrees(pulseTimer);
    }

    static boolean goToTargetZoom(){
        if(currentZoom < targetZoom){
            currentZoom += zoomSpeed;
            if(currentZoom > targetZoom) {
                currentZoom = targetZoom;
                return true;
            }
            else
                return false;
        }
        else if(currentZoom > targetZoom){
            currentZoom -= zoomSpeed;
            if(currentZoom < targetZoom) {
                currentZoom = targetZoom;
                return true;
            }
        }
        return false;
    }
}
