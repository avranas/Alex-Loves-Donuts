package com.bunkyware.fonegaem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Alexander on 1/1/2016.
 */

// >1 pattern
public class Phase {

    enum PhaseType{
        ListOnce, //go through the patterns in order, only once.
        RandomOnce, //Do all the patterns in random order, only once.
        ListLoop, //go through the patterns until 'patternsLeft' patterns have been completed.
        Random,   //pick patterns at random until 'patternsLeft' patterns have been completed
    }

    public void startNewEvents(){
        globalEventTimer++;

        //Loop through list of timedEvents and find new events that are ready, then add it to Level.activeEvents somehow
        for(int counter = 0; counter != timedEvents.size(); counter++){
            if(globalEventTimer == timedEvents.get(counter).timeStamp){
                Level.activateNewEvent(timedEvents.get(counter));
            }
        }
        for(int counter = 0; counter != frequentEvents.size(); counter++){
            if(frequentEvents.get(counter).ready()){
                Level.activateNewEvent(frequentEvents.get(counter));
            }
        }
        //Loop through list of frequentEvents and find new events that are ready, then add it to Level.activeEvents somehow

    }

    public void printCurrentPatternName(){
        System.out.println(getCurrentPattern().getClass());
    }

    private int globalEventTimer = 0;
    private Countdown patternsLeft = new Countdown(0);

    private ArrayList<Pattern> patterns = new ArrayList<>(0);
    private ArrayList<TimedEvent> timedEvents = new ArrayList<>(0);
    private ArrayList<FrequentEvent> frequentEvents = new ArrayList<>(0);
    private int currentPattern = 0;
    private PhaseType phaseType = PhaseType.ListOnce;
    private FrequencyTimer timeBetweenPatterns = new FrequencyTimer(0);
    private boolean paused = false;

    public void addNewFrequentEvent(FrequentEvent newEvent){
        frequentEvents.add(newEvent);
    }
    public void addNewTimedEvent(TimedEvent newEvent){
        timedEvents.add(newEvent);
    }

    public int patternPosition(){
        return currentPattern;
    }

    public boolean isPaused(){
        return paused;
    }
    public boolean pauseIsOver(){
        //System.out.println(timeBetweenPatterns.time_left());
        if (timeBetweenPatterns.IncrementAndTest()){
            paused = false;
            return true;
        }
        else
            paused = true;
        return false;
    }
    //If ballsInPhase == -1, the phase never ends!
    Phase(int numberOfPatterns, String _phaseType, int _timeBetweenPatterns) {
        patternsLeft.ChangeTime(numberOfPatterns);
        phaseType = PhaseType.valueOf(_phaseType);
        timeBetweenPatterns.ChangeHowOften(_timeBetweenPatterns);
        if(_phaseType.matches("ListOnce") || _phaseType.matches("RandomOnce")){
            patternsLeft.ChangeTime(1);
        }
    }


    //If ballsInPhase == -1, the phase never ends!
    boolean phaseIsOver(){
        return patternsLeft.time_left() == 0 && patternsLeft.time_left() != -1;
    }
    public void addNewPattern(Pattern newPattern){
        patterns.add(newPattern);
    }

    public Pattern getCurrentPattern(){
        return patterns.get(currentPattern);
    }


    //pickNextEvent() if I want to do Random or something

    //Next: fix each individual level; make sure everything works.
    public void goToNextPattern(){
        if( phaseType == PhaseType.Random)
            pickRandomPattern();
        else
        {
            currentPattern++;
            if (currentPattern == patterns.size()) {
                currentPattern = 0;
            }
        }
        if(!patternsLeft.time_is_up())
            if(patternsLeft.DecrementAndTest())
                currentPattern = 0;
            else
                getCurrentPattern().startPattern();
    }
    public void pickRandomPattern(){
        currentPattern = BunkyGDX.randomInt(0, patterns.size() - 1);
    }
    public void startPhase() {
        currentPattern = 0;
        globalEventTimer = 0;
        switch (phaseType) {
            case ListOnce:
                patternsLeft.ChangeTime(patterns.size());
                break;
            case ListLoop:
                break;
            case Random:
                pickRandomPattern();
                break;
            case RandomOnce:
                Collections.shuffle(patterns);
                patternsLeft.ChangeTime(patterns.size());
                break;
        }
        patternsLeft.reset();
        getCurrentPattern().startPattern();
    }
}
