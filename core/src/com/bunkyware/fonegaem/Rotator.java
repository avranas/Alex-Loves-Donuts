package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 1/3/2016.
 */

//CameraRotator?
public class Rotator {
/*    static Angle rotatorDirection = new Angle(0);
    static void setRandomRotatorDirection(){
        rotatorDirection.setAngle(BunkyGDX.randomDouble(0, 360));
    }
    static float getRotatorDirection(){
        return (float)rotatorDirection.value();
    }*/

    static void reset(){
        flipTimer.reset();
    }

    static Flipper direction = new Flipper(false);
    static double rotationSpeed;
    static Countdown timeLeft = new Countdown(0);
    static void setRandomFlipTime(int min, int max){
        flipTimer.ChangeHowOften(BunkyGDX.randomInt(min, max));
        flipTimer.reset();
    }
    static double getRotationSpeed(){
        return rotationSpeed;
    }
    static void setRandomRotatorDirection(){
        direction.pickRandom();
    }
    static boolean getRotatorDirection(){
        return direction.value();
    }
    static void flipDirection(){
        direction.Flip();
    }
    static FrequencyTimer flipTimer = new FrequencyTimer(1);

    static void setRotationSpeed(float value){
        rotationSpeed = value;
    }
    static boolean countDown(){
        return timeLeft.DecrementAndTest();
    }
    static void setRotationTimer(int time){
        timeLeft.ChangeTime(time);
    }
}
