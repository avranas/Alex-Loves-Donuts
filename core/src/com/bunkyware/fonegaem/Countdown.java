package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 12/16/2015.
 */
class Countdown {
    Countdown(int time) {
    time_left_ = time;
    initial_time_ = time;
}

    //Call this once per loop. Returns true if time is up.
    boolean DecrementAndTest()
    {
        if (time_left_ > 0)
            time_left_--;
        if (time_left_ == 0)
            return true;
        else
            return false;
    }

    void decrement(){
        time_left_--;
    }

    boolean time_is_up()
    {
        return time_left_ == 0;
    }

    int time_left()
{
    return time_left_;
}
    void ChangeTime(int value)
{
    time_left_ = value;
    initial_time_ = value;
}
    void SetTimeLeft(int value)
{
    time_left_ = value;
}

    int initial_time()
{
    return initial_time_;
}
    void reset()
{
    time_left_ = initial_time_;
}

    private int time_left_;
    private int initial_time_;
};
