package com.bunkyware.fonegaem;

/**
 * Created by Alexander on 1/7/2016.
 */
public class AlternatingPulse extends Pattern {

    private int numberOfBallsInAPulse;
    private FrequencyTimer ballFrequency;
    private FrequencyTimer numberOfPulses;
    static private Flipper position = new Flipper(true);
    private float ballSpeed = 0;
    private int donutPosition = -1;
    private boolean firstPulse = false;

    AlternatingPulse(int _numberOfBallsInAPulse, int _ballFrequency, int _numberOfPulses, float _ballSpeed, boolean _alternateDonuts){
        numberOfBallsInAPulse = _numberOfBallsInAPulse;
        ballFrequency = new FrequencyTimer(_ballFrequency);
        numberOfPulses = new FrequencyTimer(_numberOfPulses);
        ballSpeed = _ballSpeed;
        if(_alternateDonuts)
            donutPosition = 0;
    }

    public void startUniquePattern() {
        Level.setBallSpeed(ballSpeed);
    }

    public void patternLogic(Alex alex) {
        if (ballFrequency.IncrementAndTest()) {
            if(firstPulse){
                Level.setCurrentAngle(alex.getPositionInDegrees());
            }
            float previousAngle = (float)Level.getCurrentAngle().value();
            boolean usedDonut = false;
            position.Flip();
            for(int counter = 0; counter != numberOfBallsInAPulse; counter++) {
                Angle newAngle = new Angle(0);
                float calculatedAngle = counter * 360.f / (float)(numberOfBallsInAPulse);
                if(position.value())
                    calculatedAngle += (Angle.FULL_ROTATION / numberOfBallsInAPulse / 2);
                newAngle.setAngle(calculatedAngle);
                Level.setCurrentAngle((float) newAngle.value());
                if(counter == donutPosition && !usedDonut) {
                    Level.addNewDonut();
                    usedDonut = true;
                    if(BunkyGDX.coinFlip()) {
                        donutPosition++;
                        if (donutPosition == numberOfBallsInAPulse)
                            donutPosition = 0;
                    }
                    else {
                        donutPosition--;
                        if(donutPosition < 0)
                            donutPosition = numberOfBallsInAPulse - 1;
                    }
                }
                else
                    Level.addNewBadBall();
            }
            Level.setCurrentAngle(previousAngle);
            if(numberOfPulses.IncrementAndTest())
                finishPattern();
        }
    }

}
