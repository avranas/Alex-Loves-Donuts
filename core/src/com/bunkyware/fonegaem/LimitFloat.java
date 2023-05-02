package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 12/29/2015.
 */
public class LimitFloat {

    private float max = 0;
    private float value = 0;
    boolean signed = false;

    LimitFloat(float init, float _max, boolean _signed){
        max = _max;
        value = init;
        signed = _signed;
    }

/*    void add(float thisMuch){
        if(value + thisMuch > max)

    }*/
}
