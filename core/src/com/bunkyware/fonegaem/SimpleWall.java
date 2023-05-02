package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 4/5/2016.
 */
public class SimpleWall extends Pattern {

    float length;
    int howMany;
    float speed;

    public SimpleWall(float _speed, float _length, int _howMany) {
        length = _length;
        howMany = _howMany;
        speed = _speed;
    }

    public void patternLogic(Alex alex){
        float angle = alex.getPositionInDegrees();
        Level.setBallSpeed(speed);
        for(int counter = 0; counter != howMany; counter++){
            Level.setCurrentAngle(angle + (float)counter / (float)howMany * length  - length / 2);
            Level.addNewBadBall();
            finishPattern();
        }
    }
}
