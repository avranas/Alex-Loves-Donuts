package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 5/31/2016.
 */
public class LightsOff {

    static FrequencyTimer offTime = new FrequencyTimer(0);
    static boolean recentLower = false;
    static double largeTransitionAmount = 0;
    static double frameTransitionAmount = 0;
    static private double alpha = 0;

    public static void reset(){
        offTime.reset();
        alpha = 0;
        largeTransitionAmount = 0;
        recentLower = false;
        frameTransitionAmount = 0;
    }

    static void turnLightsOffLots(){
        alpha += largeTransitionAmount;
        if(alpha >= 1)
            alpha = 1;
    }

    static void turnLightsOnLots(){
        alpha -= largeTransitionAmount;
        if(alpha <= 0)
            alpha = 0;
    }

    static public double getAlpha(){
        return alpha;
    }
    static void turnLightsOnSome(){
        alpha -= frameTransitionAmount;
        if(alpha <= 0)
            alpha = 0;
    }

    static void turnLightsOffSome(){
        alpha += frameTransitionAmount;
        if(alpha >= 1)
            alpha = 1;
    }
    static void turnLightsOff(){
        alpha = 1;
    }

    static void turnLightsOn(){
        alpha = 0;
    }

    static boolean lightsAreAllTheWayOff(){
        return alpha == 1;
    }

    static boolean lightsAreAllTheWayOn(){
        return alpha == 0;
    }

    static boolean neitherOnNorOff(){
        return alpha < 1 && alpha != 0;
    }


}
