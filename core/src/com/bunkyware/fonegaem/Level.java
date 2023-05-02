package com.bunkyware.fonegaem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Intersector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by Alexander on 12/8/2015.
 */

//Next: I need to implement "pattern sets" For example: until you reach x number of balls, loop through pattern set A,
    //after reaching x balls, loop through pattern set B, then when you reach y balls, start looping through set C until you win.

public class Level{

    //Remember, if you add any new variables, you have to reset it in void loadInstructions(String file)
    static private ArrayList<Phase> phases = new ArrayList<>(0);
    static private ArrayList<GameEvent> activeEvents = new ArrayList<>(0);
    static private int currentPhase = 0;
    static private int ballsCollected = 0;
    static private int totalBallsToWin = 0;
    static private float ballSpeed = 0;
    static private Angle currentAngle = new Angle(0);
    static private float randomBadBallPercentage = 0;
    static private float increasingSpeedValue = 0.0f;
    static private float increaseTransparencyAfter = 0; //in ball distance
    static private float increaseTransparencyByThisMuch = 0; //per frame
    static private ArrayList<Donut> newDonuts = new ArrayList<>(0);
    static private ArrayList<TestDot> newTestDots = new ArrayList<>(0);
    static private ArrayList<BadBall> newBadBalls = new ArrayList<>(0);

    static void startNewEvents(){
        getCurrentPhase().startNewEvents();
    }

    public static void activateNewEvent(GameEvent newEvent){
        activeEvents.add(newEvent);
    }

    //When making a new variable, make sure you revert it to its default value in loadInstructions()

   static public void fire(boolean badBallOn){
       if(badBallOn || readyForRandomBadBall())
           addNewBadBall();
       else
           addNewDonut();
   }

    static public void printStatus(){
        String printThis = "Phase: " + Integer.toString(currentPhase) + " ; " + "Pattern: " + Integer.toString(getCurrentPhase().patternPosition());
        if(levelIsOver())
            System.out.println("Done!");
        else if(!getCurrentPhase().isPaused())
            System.out.println(printThis);
        else
            System.out.println("paused");
    }

    static public Pattern getCurrentPattern(){
        return getCurrentPhase().getCurrentPattern();
    }

    static public void startCurrentPattern(){
        getCurrentPattern().startPattern();
    }

    static public Phase getCurrentPhase(){
        return phases.get(currentPhase);
    }

    //TODO: Decide on if I want to make a addNewAcceleratingDonut() method
    static public void addNewDonut(){
        //This is (only?) used in one level (2-2?) which increases the speed of the donuts whenever one is fired.
        setBallSpeed(ballSpeed + increasingSpeedValue);
        //getCurrentPhase().decrementPhaseCounter();
        newDonuts.add(new Donut((float) currentAngle.value(), ballSpeed, 1));
    }

    static public void addNewShrinkingDonut(float shrinkRate){
        newDonuts.add(new Donut((float) currentAngle.value(), ballSpeed, shrinkRate));
    }

    static public void addNewTestDot(){
        newTestDots.add(new TestDot((float) currentAngle.value(), ballSpeed));
    }

    static public void addNewBadBall(){
        newBadBalls.add(new BadBall((float) currentAngle.value(), ballSpeed));
    }

    static void removeExpiredEvents(){
        for (Iterator<GameEvent> iterator = activeEvents.iterator(); iterator.hasNext();) {
            GameEvent ge = iterator.next();
            if (ge.isOver())
                iterator.remove();

        }
    }

    static BadBall getNextBadBall(){
        int here = newBadBalls.size() - 1;
        BadBall returnThis = newBadBalls.get(here);
        newBadBalls.remove(here);
        return returnThis;
    }

    static TestDot getNextTestDot(){
        int here = newTestDots.size() - 1;
        TestDot returnThis = newTestDots.get(here);
        newTestDots.remove(here);
        return returnThis;
    }

    static Donut getNextDonut(){
        int here = newDonuts.size() - 1;
        Donut returnThis = newDonuts.get(here);
        newDonuts.remove(here);
        return returnThis;
    }

    static boolean badBallsAvailable(){
        return newBadBalls.size() != 0;
    }

    static boolean testDotsAvailable(){
        return newTestDots.size() != 0;
    }

    static boolean donutsAvailable(){
        return newDonuts.size() != 0;
    }

    static public float getIncreaseTransparencyAfter(){
        return increaseTransparencyAfter;
    } //in ball distance
    static public float getIncreaseTransparencyByThisMuch(){
        return increaseTransparencyByThisMuch;
    } //per frame

    static public float getBallSpeed(){
        return ballSpeed;
    }

    static public Angle getCurrentAngle(){
        return currentAngle;
    }

    static public void setBallSpeed(float newSpeed){
        ballSpeed = newSpeed;
    }

    static public void setCurrentAngle(double newAngle){currentAngle.setAngle(newAngle);}

    static public int numberOfActiveEvents(){
        return activeEvents.size();
    }

    static public GameEvent getEvent(int value){
        return activeEvents.get(value);
    }

    static public void goToNextPatternIfReady(){
        if(getCurrentPhase().getCurrentPattern().patternIsOver()) {
            if (getCurrentPhase().pauseIsOver()) {
                getCurrentPhase().goToNextPattern();
                if(getCurrentPhase().phaseIsOver()) {
                    currentPhase++;
                    if (currentPhase == phases.size())
                        currentPhase = 0;
                    getCurrentPhase().startPhase();
                }
            }
        }

    }

    static public void resetBallCounter(){
        ballsCollected = 0;
    }

    static public int getBallsCollected(){
        return ballsCollected;
    }

    static public void eatABall(){
        ballsCollected++;
    }

    static public boolean levelIsOver(){
        return ballsCollected >= getTotalBallsToWin() && getTotalBallsToWin() != -1;
    }

    static void loadInstructions(String file) {
        //reset everything
        activeEvents.clear();
        phases.clear();
        newDonuts.clear();
        newBadBalls.clear();
        currentPhase = -1;
        totalBallsToWin = 0;
        ballSpeed = 0;
        currentAngle.setAngle(0);
        randomBadBallPercentage = 0;
        increasingSpeedValue = 0.0f;
        increaseTransparencyAfter = 0;
        increaseTransparencyByThisMuch = 0;
        ballsCollected = 0;


        FileHandle handle = Gdx.files.internal(file);
        String string = handle.readString();
        ArrayList<String> instructions = BunkyGDX.getLines(string);
        //patternPicker = PatternPicker.valueOf(instructions.get(0));


        //events
        //Event 90 1 MakeFake
        //(every 90 frames, perform action "MakeFake" for 1 frame.


        //go through each line and read patterns.
        totalBallsToWin = Integer.parseInt(instructions.get(0));
        for(int lineCounter = 1; lineCounter != instructions.size(); lineCounter++){
            ArrayList<String> newPattern = BunkyGDX.removeSpaces(instructions.get(lineCounter));
            //The first instruction in each line will be the name of the pattern type
            switch(newPattern.get(0)) {
                case "NewPhase":
                    int ballsInPhase = Integer.parseInt(newPattern.get(1));
                    String phaseType = newPattern.get(2);
                    int timeBetweenPatterns = Integer.parseInt(newPattern.get(3));
                    phases.add(new Phase(ballsInPhase, phaseType, timeBetweenPatterns));
                    currentPhase++;
                    break;
                case "TimedEvent": {
                    ArrayList<String> entry = new ArrayList<>(0);
                    String eventType;
                    eventType = newPattern.get(2);
                    for(int counter = 3; counter != newPattern.size(); counter++)
                        entry.add(newPattern.get(counter));
                    getCurrentPhase().addNewTimedEvent(new TimedEvent(eventType, entry, Integer.parseInt(newPattern.get(1))));
                    break;
                }
                case "FrequentEvent": {
                    ArrayList<String> entry = new ArrayList<>(0);
                    String eventType;
                    eventType = newPattern.get(2);
                    for(int counter = 3; counter != newPattern.size(); counter++)
                        entry.add(newPattern.get(counter));
                    getCurrentPhase().addNewFrequentEvent(new FrequentEvent(eventType, entry, Integer.parseInt(newPattern.get(1))));
                    break;
                }
                //Spits donut/bad at random position
                case "Random":
                    getCurrentPhase().addNewPattern(new Random(
                            Float.parseFloat(newPattern.get(1)), //speed
                            Integer.parseInt(newPattern.get(2)), //frequency
                            Integer.parseInt(newPattern.get(3)), //howMany
                            Boolean.parseBoolean(newPattern.get(4)) //bad ball on
                    ));
                    break;
                //spits donut/bad at inital angle, angle rotates every frame
                //TODO: inital angle, min flip time
                case "Pointer":
                    getCurrentPhase().addNewPattern(new Pointer(
                            Float.parseFloat(newPattern.get(1)),    //ballSpeed
                            Integer.parseInt(newPattern.get(2)),    //ballFrequency
                            Integer.parseInt(newPattern.get(3)),    //howMany
                            Float.parseFloat(newPattern.get(4)),    //rotationSpeed
                            Integer.parseInt(newPattern.get(5)),     //minFlipTime
                            Integer.parseInt(newPattern.get(6)),     //maxFlipTime
                            Boolean.parseBoolean(newPattern.get(7)) //badball on
                    ));
                    break;
                //used in 2-2
                case "PointerWithHalfBadBalls":
                    Pointer newPointer = new Pointer(
                            Float.parseFloat(newPattern.get(1)),    //ballSpeed
                            Integer.parseInt(newPattern.get(2)),    //ballFrequency
                            Integer.parseInt(newPattern.get(3)),    //howMany
                            Float.parseFloat(newPattern.get(4)),    //rotationSpeed
                            Integer.parseInt(newPattern.get(5)),   //minFlipTime
                            Integer.parseInt(newPattern.get(6)),   //maxFlipTime
                            false);
                    newPointer.makeHalfOfBallsBad();
                    getCurrentPhase().addNewPattern(newPointer);
                     break;
                //pause for x - y frames
                case "RandomPause":
                    getCurrentPhase().addNewPattern(new Pause(
                            BunkyGDX.randomInt(Integer.parseInt(newPattern.get(1)), Integer.parseInt(newPattern.get(2))
                    )));
                    break;
                //pause for x frames
                case "Pause":
                    getCurrentPhase().addNewPattern(new Pause(
                            Integer.parseInt(newPattern.get(1))     //time in frames
                    ));
                    break;
                //TODO: change this to an event
                case "IncreaseSpeedEveryDonut":
                    increasingSpeedValue = Float.parseFloat(newPattern.get(1));
                    break;
                //TODO: change this to an event
                case "IncreaseTransparency":
                    increaseTransparencyAfter = Float.parseFloat(newPattern.get(1)); //in ball distance
                    increaseTransparencyByThisMuch = Float.parseFloat(newPattern.get(2)); //per frame
                    break;
                //TODO: Remove this. I'll never use it.
                case "RandomBadBallPercentage":
                    randomBadBallPercentage = Float.parseFloat(newPattern.get(1)); //bad ball percentage 0.00 - 1.00
                    break;
                //TODO: Remove this. I see no practical use.
                case "BallRing":
                    getCurrentPhase().addNewPattern(new BallRing(
                            Float.parseFloat(newPattern.get(1)), //speed
                            Integer.parseInt(newPattern.get(2)), //number of balls
                            Float.parseFloat(newPattern.get(3)), //low angle
                            Float.parseFloat(newPattern.get(4))  //high angle
                    ));
                    break;
                //A pointer with a ring of bads with an opening instead of a donut.
                //TODO: Add a min/max flip time.
                case "OpeningPointer":
                    getCurrentPhase().addNewPattern(new OpeningPointer(
                            Float.parseFloat(newPattern.get(1)), //ball speed
                            Integer.parseInt(newPattern.get(2)), //frequency
                            Integer.parseInt(newPattern.get(3)), //how many reps
                            Float.parseFloat(newPattern.get(4)),  //rotation speed
                            Integer.parseInt(newPattern.get(5)), //min flip time
                            Integer.parseInt(newPattern.get(6)), //max flip time
                            Integer.parseInt(newPattern.get(7)), //number of balls in one rep
                            Float.parseFloat(newPattern.get(8)), //opening length
                            Boolean.parseBoolean(newPattern.get(9)) //only 2 balls
                    ));
                    break;
                //Pulse of x bads equal length apart (y), that increase its position by 1/2 of y, every rep
                case "AlternatingPulse":
                    getCurrentPhase().addNewPattern(new AlternatingPulse(
                            Integer.parseInt(newPattern.get(1)), //numberOfBalls
                            Integer.parseInt(newPattern.get(2)), //frequency
                            Integer.parseInt(newPattern.get(3)), //number of pulses
                            Float.parseFloat(newPattern.get(4)), //ball speed
                            Boolean.parseBoolean(newPattern.get(5)) //alternating donut
                    ));
                    break;
                case "ShrinkingDonutPointer":
                    getCurrentPhase().addNewPattern(new ShrinkingDonutPointer(
                            Float.parseFloat(newPattern.get(1)),    //ballSpeed
                            Integer.parseInt(newPattern.get(2)),    //ballFrequency
                            Integer.parseInt(newPattern.get(3)),    //howMany
                            Float.parseFloat(newPattern.get(4)),    //rotationSpeed
                            Integer.parseInt(newPattern.get(5)),    //minFlipTime
                            Integer.parseInt(newPattern.get(6)),    //maxFlipTime
                            Float.parseFloat(newPattern.get(7))     //Shrink rate
                    ));
                    break;
                //creates bads with a gap, starting at 360, closing down until it reaches endGap
                case "GapCloser":
                    getCurrentPhase().addNewPattern(new GapCloser(
                            Float.parseFloat(newPattern.get(1)),//ball speed
                            Float.parseFloat(newPattern.get(2)),//_closingSpeed)
                            Float.parseFloat(newPattern.get(3)),// _endGap,
                            Integer.parseInt(newPattern.get(4)),// _ballFrequency,
                            Integer.parseInt(newPattern.get(5))// _numberOfBalls
                    ));
                    break;
                //Shoots Alex. If smart, calculates Alex's projected location based on movement and ball speed.
                //TODO: Try this with donuts
                case "Aimer":
                    getCurrentPhase().addNewPattern(new Aimer(
                            Float.parseFloat(newPattern.get(1)), //ballspeed
                            Integer.parseInt(newPattern.get(2)), //frequency
                            Integer.parseInt(newPattern.get(3)), //howmany
                            Boolean.parseBoolean(newPattern.get(4)) //smart
                    ));
                    break;
                //spits bads with x balls at equal distances
                case "Star":
                    getCurrentPhase().addNewPattern(new Star(
                            Float.parseFloat(newPattern.get(1)), //ballspeed
                            Integer.parseInt(newPattern.get(2)), //frequency
                            Integer.parseInt(newPattern.get(3)), //howmany
                            Float.parseFloat(newPattern.get(4)), //rotation speed
                            Integer.parseInt(newPattern.get(5)) //balls per rep
                    ));
                    break;
                //A Star with a rotation that speeds up and slows down like a sine wave.
                case "SineStar":
                    getCurrentPhase().addNewPattern(new SineStar(
                            Float.parseFloat(newPattern.get(1)), //ballspeed
                            Integer.parseInt(newPattern.get(2)), //frequency
                            Integer.parseInt(newPattern.get(3)), //howmany
                            Float.parseFloat(newPattern.get(4)), //rotation speed
                            Integer.parseInt(newPattern.get(5)), //balls per rep
                            Integer.parseInt(newPattern.get(6)) //wave width
                    ));
                    break;
                //TODO: Instead of rotating, Walls should have a random offset
                //idk
                case "Walls":
                    getCurrentPhase().addNewPattern(new Walls(
                            Float.parseFloat(newPattern.get(1)), //ballspeed
                            Integer.parseInt(newPattern.get(2)), //numberOfBalls
                            Float.parseFloat(newPattern.get(3)), //rotation
                            Integer.parseInt(newPattern.get(4)), //numberOfOpenings
                            Integer.parseInt(newPattern.get(5)), //ballFrequency
                            Integer.parseInt(newPattern.get(6)) //numberOfReps
                    ));
                    break;
                //TODO: Remove this, it sucks
                case "Stopper":
                    getCurrentPhase().addNewPattern(new Stopper(
                            Float.parseFloat(newPattern.get(1)), //ballspeed
                            Integer.parseInt(newPattern.get(2)), //frequency
                            Integer.parseInt(newPattern.get(3)) //how many
                    ));
                    break;
//idk
                case "DeathWheel":
                    getCurrentPhase().addNewPattern(new DeathWheel(
                            Float.parseFloat(newPattern.get(1)), //solid speed
                            Float.parseFloat(newPattern.get(2)), //bullet Speed
                            Integer.parseInt(newPattern.get(3)), //solid frequency
                            Float.parseFloat(newPattern.get(4)), //solid rotation speed
                            Integer.parseInt(newPattern.get(5)), //solid pieces
                            Integer.parseInt(newPattern.get(6)), //bullet frequency
                            Integer.parseInt(newPattern.get(7)) //total time
                    ));
                    break;
                //idk
                case "DeathWheelPointer":
                    getCurrentPhase().addNewPattern(new DeathWheelPointer(
                            Float.parseFloat(newPattern.get(1)), //solid speed
                            Float.parseFloat(newPattern.get(2)), //bullet Speed
                            Integer.parseInt(newPattern.get(3)), //solid frequency
                            Float.parseFloat(newPattern.get(4)), //solid rotation speed
                            Integer.parseInt(newPattern.get(5)), //solid pieces
                            Integer.parseInt(newPattern.get(6)), //bulletx   frequency
                            Integer.parseInt(newPattern.get(7)), //total time
                            Integer.parseInt(newPattern.get(8)), //min flip time
                            Integer.parseInt(newPattern.get(9)) //max flip time
                    ));
                    break;
                //idk
                case "SmallWalls":
                    getCurrentPhase().addNewPattern(new SmallWalls(
                            Integer.parseInt(newPattern.get(1)), //number of walls
                            Integer.parseInt(newPattern.get(2)), //time
                            Float.parseFloat(newPattern.get(3)), //ball speed
                            Integer.parseInt(newPattern.get(4)), //wall frequency
                            Integer.parseInt(newPattern.get(5)), //obstacle frequency
                            Integer.parseInt(newPattern.get(6)) //balls per obstacle
                    ));
                    break;
                //idk
                case "PulseWithRangeOfSpeed":
                    getCurrentPhase().addNewPattern(new PulseWithRangeOfSpeed(
                            Integer.parseInt(newPattern.get(1)),// _numberOfBalls
                            Integer.parseInt(newPattern.get(2)),// frequency
                            Float.parseFloat(newPattern.get(3)),// lowSpeed
                            Float.parseFloat(newPattern.get(4)),// highSpeed
                            Integer.parseInt(newPattern.get(5)),// numberOfCorners
                            Integer.parseInt(newPattern.get(6)),// numberOfPulses
                            Boolean.parseBoolean(newPattern.get(7))// alternates
                    ));
                    break;
                //idk
                case "Slalom":
                    getCurrentPhase().addNewPattern(new Slalom(
                            Integer.parseInt(newPattern.get(1)), //frequency
                            Integer.parseInt(newPattern.get(2)), //reps
                            Float.parseFloat(newPattern.get(3)), //ballSpeed
                            Integer.parseInt(newPattern.get(4)),  //angleOffset
                            Float.parseFloat(newPattern.get(5))   //expandSpeed
                    ));
                    break;
                // x o x -- donut sandwiched between two bad balls
                case "TightFit":
                    getCurrentPhase().addNewPattern(new TightFit(
                            Integer.parseInt(newPattern.get(1)), //frequency
                            Integer.parseInt(newPattern.get(2)), //space
                            Integer.parseInt(newPattern.get(3)), //reps
                            Float.parseFloat(newPattern.get(4)) //speed
                    ));
                    break;
                //idk
                case "Waves":
                    getCurrentPhase().addNewPattern(new Waves(
                            Float.parseFloat(newPattern.get(1)), //bad ball speed
                            Integer.parseInt(newPattern.get(2)), //wave frequency
                            Integer.parseInt(newPattern.get(3)), //donutFrequency
                            Integer.parseInt(newPattern.get(4)), //wave count
                            Integer.parseInt(newPattern.get(5)), //bad balls per rep
                            Float.parseFloat(newPattern.get(6)) //cb speed
                    ));
                    break;
                //idk
                case "OneIsGood":
                    getCurrentPhase().addNewPattern(new OneIsGood(
                            Integer.parseInt(newPattern.get(1)), //frequency
                            Integer.parseInt(newPattern.get(2)), //numberOfSlots
                            Float.parseFloat(newPattern.get(3)), //ballSpeed
                            Float.parseFloat(newPattern.get(4)), //openingWidth
                            Integer.parseInt(newPattern.get(5)) //reps
                    ));
                    break;
                case "SimpleWall":
                    //points at Alex and shoots a wall at x length
                    getCurrentPhase().addNewPattern(new SimpleWall(
                            Float.parseFloat(newPattern.get(1)), //speed
                            Float.parseFloat(newPattern.get(2)), //length
                            Integer.parseInt(newPattern.get(3)) //howmany
                    ));
                    break;
                //Shoots multiple bad balls at a time at varying speeds.
                case "Chaos":
                    getCurrentPhase().addNewPattern(new Chaos(
                            Integer.parseInt(newPattern.get(1)), //_numberAtAtime
                            Integer.parseInt(newPattern.get(2)), //frequency
                            Integer.parseInt(newPattern.get(3)), //howmany
                            Float.parseFloat(newPattern.get(4)), //low ball speed
                            Float.parseFloat(newPattern.get(5))  //high ball Speed
                    ));
                    break;
                //TODO: Remove this, I have a better one. Change the other's name to SmartRandom
                case "SmartRandom":
                    getCurrentPhase().addNewPattern(new SmartRandom(
                            Float.parseFloat(newPattern.get(1)), //_ballSpeed
                            Integer.parseInt(newPattern.get(2)), //frequency
                            Integer.parseInt(newPattern.get(3)), //howmany
                            Float.parseFloat(newPattern.get(4)), //rotation speed
                            Integer.parseInt(newPattern.get(5)), //min flip time
                            Integer.parseInt(newPattern.get(6)) //max flip time
                    ));
                    break;
                //
                //   x
                //   x
                //   x   o
                //   x
                //   x
                //
                //
                //       x
                //       x
                //   o   x
                //       x
                //       x
                //
                //Kinda like that. Alternates back and forth
                case "Slalom2":
                    getCurrentPhase().addNewPattern(new Slalom2(
                            Integer.parseInt(newPattern.get(1)), //_fenceBallFrequency
                            Integer.parseInt(newPattern.get(2)), //_fenceBalls
                            Integer.parseInt(newPattern.get(3)), //_breakTime
                            Integer.parseInt(newPattern.get(4)) //_ballSpeed
                    ));
                    break;
                //Intended to be a mess of random bad balls with x amount of safe gaps
                case "SmarterRandom":
                    getCurrentPhase().addNewPattern(new SmarterRandom(
                            Integer.parseInt(newPattern.get(1)), //how many reps
                            Float.parseFloat(newPattern.get(2)), //ballSpeed
                            Integer.parseInt(newPattern.get(3)), //ball frequency
                            Integer.parseInt(newPattern.get(4)), //sections
                            Float.parseFloat(newPattern.get(5)), //safe zone width
                            Float.parseFloat(newPattern.get(6))  //rotation Speed
                    ));
                    break;
                //Shoots a ball at x speed, from y - z distance away from alex
                //Good for quick reaction levels
                case "Trigger":
                    getCurrentPhase().addNewPattern(new Trigger(
                            Float.parseFloat(newPattern.get(1)), //ball speed
                            Float.parseFloat(newPattern.get(2)), //min distance
                            Float.parseFloat(newPattern.get(3)), //max distance
                            Boolean.parseBoolean(newPattern.get(4)) //random bad ball
                    ));
                    break;
                default:
                    System.out.println("problem in Level constructor");
            }
        }
        currentPhase = 0;
    }

    static boolean readyForRandomBadBall(){
        return Math.random() * 100 < randomBadBallPercentage;
    }


    static int getTotalBallsToWin(){
        if(totalBallsToWin == -1)
            return Integer.MAX_VALUE;
        return totalBallsToWin;
    }

}
//Can't something be done about Aimer? I like the idea but it sucks :(
//TODO: I should make a random badBall pattern so it looks random and crazy like Random does now, but isn't impossible sometimes.
    //Maybe one ball per rep in a random division of the circle.
//TODO: ooh! I need a pattern that spits balls to your left and right, forcing me to stay put.