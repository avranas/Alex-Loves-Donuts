package com.bunkyware.fonegaem;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Alexander on 4/28/2016.
 */
public class RandomSelection {

    //numberTrue must be less than numberOfSlots
    static ArrayList<Boolean> getSelection(int numberOfSlots, int numberTrue){
        ArrayList<Boolean> selection = new ArrayList<>(numberOfSlots);
        for(int counter = 0; counter != numberTrue; counter++){
            selection.add(counter, true);
        }
        for(int counter = numberTrue; counter != numberOfSlots; counter++){
            selection.add(counter, false);
        }
        Collections.shuffle(selection);
        return selection;
    }
}
