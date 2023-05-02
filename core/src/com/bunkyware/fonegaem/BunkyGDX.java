package com.bunkyware.fonegaem;

import com.badlogic.gdx.Gdx;

import java.awt.Point;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Alexander on 11/24/2015.
 */
public class BunkyGDX {

    static public Angle randomAngle(){
        return new Angle(randomDouble(0, 359.999999999999999));
    }
    static public final float GAME_WIDTH = 1920;
    static public final float GAME_HEIGHT = 1080;
    static float midPointX(){
       return GAME_WIDTH / 2;
    }
    static float midPointY(){
        return GAME_HEIGHT / 2;
    }
    static final float pi = 3.141592653589793238462643383279f;

    static float touchXFlip(){
        return Gdx.input.getX();
    }

    static ArrayList<String> getLines(String string){
        ArrayList<String> lines = new ArrayList<String>(1);
        StringBuilder newString = new StringBuilder();
        for(int counter = 0; counter != string.length(); counter++){
            char current = string.charAt(counter);
            // Original  ->  if(current == ' ' || current == '\r' || current == '\n') {
            if(current == '\r' || current == '\n') {
                if(newString.toString() != "") {
                    lines.add(newString.toString());
                    newString = new StringBuilder();
                }
            }
            else{
                newString.append(string.charAt(counter));
            }
        }
        if(newString.toString() != "")
            lines.add(newString.toString());
        return lines;
    }

    //removes spaces in a string and puts them in an ArrayList.
    static ArrayList<String> removeSpaces(String string){
        ArrayList<String> lines = new ArrayList<String>(0);
        StringBuilder newString = new StringBuilder();
        for(int counter = 0; counter != string.length(); counter++) {
            char current = string.charAt(counter);
            if (current == ' ') {
                if (newString.toString() != "") {
                    lines.add(newString.toString());
                    newString = null;
                    newString = new StringBuilder();
                }
            } else {
                newString.append(string.charAt(counter));
            }
        }
        if(newString.toString() != "")
            lines.add(newString.toString());
        return lines;
    }


    static float touchYFlip(){
        return BunkyGDX.GAME_HEIGHT - Gdx.input.getY();
    }

    //returns true unless char == 0
    static boolean charToBool(char character){
        return character != '0';
    }
    static char boolToChar(boolean bool){
        if(bool)
            return '1';
        else
            return '0';
    }

    static float cosDegrees(float value){
        return (float)Math.cos(value * pi / 180);
    }

    static float sinDegrees(float value){
        return (float)Math.sin(value * pi / 180);
    }

    static float tanDegrees(float value){
        return (float)Math.tan(value * pi / 180);
    }

    static float asinDegrees(float value){
        return (float)Math.asin(value * pi / 180);
    }

    static float acosDegrees(float value){
        return (float)Math.acos(value * pi / 180);
    }

    static float atanDegrees(float value){
        return (float)Math.atan(value * pi / 180);
    }

    static int randomInt(int low, int high){
        return low + (int)(Math.random() * (high - low + 1));
    }

    static double randomDouble(double low, double high){
        return low + (Math.random() * (high - low));
    }
    static boolean coinFlip(){
        return randomInt(0, 1) == 0;
    }

    //0 for top
    //1 for bottom
    //2 for left
    //3 for right
    //-1 (or anything else really, for random
    static FloatPoint randomLocationOnEdgeOfScreen(int code){
        FloatPoint returnThis = new FloatPoint(0, 0);
        if(code != 0 && code != 1 && code != 2 && code != 3)
            code = randomInt(0, 3);
        switch(code){
            //top
            case 0:
                returnThis.x = (float)randomDouble(0, BunkyGDX.GAME_WIDTH);
                returnThis.y = BunkyGDX.GAME_HEIGHT;
                break;
            //bottom
            case 1:
                returnThis.x = (float)randomDouble(0, BunkyGDX.GAME_WIDTH);
                returnThis.y = 0;
                break;
            //left
            case 2:
                returnThis.x = 0;
                returnThis.y = (float)randomDouble(0, BunkyGDX.GAME_HEIGHT);
                 break;
            //right
            case 3:
                returnThis.x = BunkyGDX.GAME_WIDTH;
                returnThis.y = (float)randomDouble(0, BunkyGDX.GAME_HEIGHT);
                break;
        }
        return returnThis;
    }

    //numberTrue must be less than numberOfSlots
    static ArrayList<Boolean> randomSelection(int numberOfSlots, int numberTrue){
        ArrayList<Boolean> selection = new ArrayList<>(numberOfSlots);
        for(int counter = 0; counter < numberTrue; counter++){
            selection.add(counter, true);
        }
        for(int counter = numberTrue; counter < numberOfSlots; counter++){
            selection.add(counter, false);
        }
        Collections.shuffle(selection);
        return selection;
    }

}
