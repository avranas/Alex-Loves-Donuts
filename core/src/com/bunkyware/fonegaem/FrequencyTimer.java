package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 11/21/2015.
 */
public class FrequencyTimer {

    //Counts up to howOften.
    //Adds 1 everytime incrementAndTest() is called, and returns true when howOften is  reached, else returns false.
    //If howOften is set to -1, then IncrementAndTest will always return false.
    FrequencyTimer(int howOften){

        timer_ = 0;
        how_often_ = howOften;
        recent_cycle_ = false;
    }
    //Call this once per loop. Returns true every "how_often" frames.
    boolean IncrementAndTest()
    {
        if(how_often_ == -1)
            return false;
        timer_++; // + 1 frame
        if(timer_ >= how_often_)
        {
            timer_ -= how_often_;
            recent_cycle_ = true;
            return true;
        }
        recent_cycle_ = false;
        return false;
    }

    int howOften(){
        return how_often_;
    }

    boolean unincremented(){
        return timer_ == 0;
    }

    void IncrementWithoutTesting()
    {
        timer_++;
        if (timer_ >= how_often_)
            timer_ -= how_often_;
    }

    //Next time IncrementAndTest is called, you'll reach the end.
    void SkipToEnd()
    {
        timer_ = how_often_ - 1;
    }

    void reset(){
        timer_ = 0;
    }

    boolean recent_cycle()
    {
        return recent_cycle_;
    }
    int how_often()
    {
        return how_often_;
    }

    int time_spent()
    {
        return timer_;
    }
    int time_left()
    {
        return how_often_ - timer_;
    }
    void ChangeHowOften(int value)
    {
        how_often_ = value;
    }

    int timer_;
    int how_often_;
    boolean recent_cycle_;
}
