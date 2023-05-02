package com.bunkyware.fonegaem;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;

import java.awt.Event;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javafx.scene.effect.Light;

/**
 * Created by Alexander on 12/15/2015.
 */

//What the hell am I trying to do with this Manipulator thing?
    //I want things to happen in the MainGame as described by the level file
    //

public class GameEvent {

    public static void resetEventData(){
        Rotator.reset();
        Zoomer.reset();
        Bumper.reset();
        InputTransparency.reset();
        InputZoom.reset();
        InputMove.reset();
        LightsOff.reset();
    }
    public boolean eventJustStarted(){
        return startTimer.recent_cycle();
    }
    private String eventType;
    private ArrayList<String> instructions = new ArrayList<>();
    private FrequencyTimer startTimer = new FrequencyTimer(0);
    private FrequencyTimer finishTimer = new FrequencyTimer(0);
    private boolean isActive = false;
    private boolean eventDone = false;
    void finishEvent(){
        eventDone = true;
    }

    public String getEventType(){
        return eventType;
    }

    public void countTime(){
        if(isActive){
            if(finishTimer.IncrementAndTest())
                isActive = false;
        }
        else {
          if(startTimer.IncrementAndTest()) {
              isActive = true;
              eventDone = false;
          }
        }
    }

    public boolean isOver(){
        return eventDone;
    }
    public boolean isActive(){
        return isActive;
    }

    String getInstruction(int value){
        return instructions.get(value);
  }

    GameEvent(String _eventType, ArrayList<String> _instructions) {
        instructions = _instructions;
        eventType = _eventType;
        switch(eventType){
            case "AlternatingRotator":
                //0: min flip time
                //1: max flip time
                //2: rotation speed
                //3: for how long
                Rotator.setRandomFlipTime(Integer.parseInt(instructions.get(0)),
                        Integer.parseInt(instructions.get(1)));
                Rotator.setRotationSpeed(Float.parseFloat(instructions.get(2)));
                Rotator.setRotationTimer(Integer.parseInt(instructions.get(3)));
                break;
            case "Rotator":
            case "WackyRotator":
                //0: event time
                //1: speed
                Rotator.setRotationTimer(Integer.parseInt(instructions.get(0)));
                Rotator.setRotationSpeed(Float.parseFloat(instructions.get(1)));
                break;
            case "Zoom":
                Zoomer.targetZoom = Float.parseFloat(instructions.get(0));
                Zoomer.zoomSpeed = Float.parseFloat(instructions.get(1));
                break;
            case "PulsingZoom":
                Zoomer.pulseSpeed = Float.parseFloat(instructions.get(0));
                Zoomer.pulseMagnitude = Float.parseFloat(instructions.get(1));
                break;
            case "InputZoom":
                InputZoom.magnitude = Float.parseFloat(instructions.get(0));
                break;
            case "InputTransparency":
                InputTransparency.magnitude = Float.parseFloat(instructions.get(0));
                break;
            case "InputMove":
                InputMove.magnitude = Float.parseFloat(instructions.get(0));
                break;
            case "Bump":
                Bumper.magnitude = Float.parseFloat(instructions.get(0));
                break;
            case "LightsOff":
                LightsOff.offTime.ChangeHowOften(Integer.parseInt(instructions.get(0)));
                LightsOff.frameTransitionAmount = Float.parseFloat(instructions.get(1));
                break;
            case "MakeFakeCenter":
            case "MakeFakeSide":
                MakeRotateFake.rotationSpeed = Float.parseFloat(instructions.get(0));
                break;
            case "BumpOnMove":
                BumpOnMove.magnitude = Float.parseFloat(instructions.get(0));
                break;
            case "LowerLightsOnMove":
                LightsOff.largeTransitionAmount = Float.parseFloat(instructions.get(0));
                LightsOff.frameTransitionAmount = Float.parseFloat(instructions.get(1));
                break;
            case "InputLightsOff":
                LightsOff.largeTransitionAmount = Float.parseFloat(instructions.get(0));
                LightsOff.frameTransitionAmount = Float.parseFloat(instructions.get(1));
                break;
            case "ReverseInputLightsOff":
                LightsOff.largeTransitionAmount = Float.parseFloat(instructions.get(0));
                LightsOff.frameTransitionAmount = Float.parseFloat(instructions.get(1));
                break;
            //Remember: Whenever I add a new Event type, I need to add a method to reset its variables,
                //then call it above in resetEventData()
            default:
                break;
        }
    }
}
