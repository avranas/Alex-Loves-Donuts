package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 11/29/2015.
 */
public class Flipper
{
    Flipper(boolean init){
        value_ = init;
    }

    public void setValue(boolean newValue){
        value_ = newValue;
    }
    public void Flip()
    {
        value_ = !value_;
    }

    public void pickRandom(){
        value_ = BunkyGDX.coinFlip();
    }

    public boolean value()
    {
        return value_;
    }

    private boolean value_;
}
