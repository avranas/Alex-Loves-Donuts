package com.bunkyware.fonegaem;

import com.bunkyware.fonegaem.Alex;
import com.bunkyware.fonegaem.Level;

/*
 * Created by Alexander on 12/1/2015.
 */
abstract class  Pattern {
        private boolean patternOver = false;

        void setRandomAngle(){
            Level.getCurrentAngle().setAngle(Math.random() * 360);
        }

        boolean patternIsOver(){
            return patternOver;
        }

        void finishPattern(){
            patternOver = true;
            finishUniquePattern();
        }

        public void startPattern(){
            patternOver = false;
            startUniquePattern();
        }
        public void startUniquePattern(){

        }
        public void patternLogic(Alex alex){

        }

     //Whenever I want to finish a pattern, I need to reset its values so I can do it a second time should I choose.
        public void finishUniquePattern(){

        }
    }
