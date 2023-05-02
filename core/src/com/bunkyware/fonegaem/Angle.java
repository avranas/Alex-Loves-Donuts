package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 11/22/2015.
 */
public class Angle {

    public static float FULL_ROTATION = 360;

    Angle(double value)
    {
        setAngle(value);
    }
    //operator double()
    //{
    //   return value_;
    //}

        double increase(double this_much)
        {
            value_ += this_much;
            int cycles = ((int)value_) / 360;
            value_ -= cycles * 360;
            if(value_ < 0)
                value_ = 360 + value_;
            return value_;
        }
        double decrease(double this_much)
        {
            value_ -= this_much;
            int cycles = ((int) value_) / 360;
            value_ += cycles * 360;
            if(value_ < 0)
                value_ = 360 + value_;
            return value_;
        }

       // double operator = (double this_much)
       // {
       //     set_angle(this_much);
       //     return this_much;
       // }

        void setAngle(double new_value)
        {
            value_ = new_value;
            int cycles = (int)(value_) / 360;
            if(cycles > 0)
                value_ -= cycles * 360;
            else if(cycles < 0)
                value_ += cycles * 360;
            else if(value_ < 0)
                value_ = 360 + value_;
        }
        public double value(){
            return value_;
        }
       private double value_;

    static float angleBetweenPoints(FloatPoint lhs, FloatPoint rhs)
    {
        double deltaX = rhs.x - lhs.x;
        double deltaY = rhs.y - lhs.y;
        //This is to eliminate problems with dividing by zero.
        if(deltaX == 0)
            deltaX = 0.00001f;
        float angle = (float)(((Math.atan(deltaY / deltaX))* 180 / BunkyGDX.pi));
        //If the cursor is behind the character add 180
        if(lhs.x > rhs.x)
            angle += 180;
        return angle;
    }
}
