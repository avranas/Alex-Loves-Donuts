package com.bunkyware.fonegaem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.scene.effect.Light;

//TODO: level with camera rotation that works like a pendulum. Maybe changes patterns over time.
//TODO: find creative uses for camera roation.
//TODO: make obstructions cool and not suck.
//TODO: clear warnings
//TODO: There's space for one more thing under the records. Maybe an end game reward?
//TODO: I need a short cut for X-5 for testing purposes.

//TODO: While you're at it, think of a better way to lay out the title screen.
    //This TextBox class will have variables: x, y, static Glyph
    //And methods: draw(), placeOnLeft(float withBuffer), placeOnRight(float withBuffer), bottom(), top(), middleX(), middleY(), move(),
        //changeSize(), whatever I can think of.

//TODO: At the end of 4-3, the 1 donut needed to win automatically pops out where the OpeningPointer gap is. This is cool! Make sure this stays!
    //Do this by, removing MainGameState.WinLevel. When level x-4 is beaten, do a "good job!" then
    //repeat level X-4 at a faster speed. Continue counting donuts. The counter will look something
    //like 201/200, and continue counting. At 400/200, don't do a "good job!", just repeat the level
    //again and continue increasing speed.
//Beating all 4 levels without dying will send the player to level X-5 which loops through a set
    //of stupid hard  (next to impossible) instructions. I don't want to make the game speed up :(
    //Well, I don't want to have to speed up alex.
    //If the player reaches X donuts in level X-5, level X on the title screen will be colored gold.
    //When every level is colored gold, you get a new gold title screen (or something)
        //Something cool unlocks; I'm not sure what. Not content though. This not meant to be achieved by every player.

    //When a part is beaten, this counter appears under the "level part counter". After X-4 is
    //beaten, the "level part counter" disappeares and this is the only counter visible.
    //This will give players the hint to start at X-1 and see how far they can go.

//IDEA: Would it work to have "endless mode" only available if you start at the first part of the level
//then last all the way to the end of the last part? "endless mode" would repeat the instructions of
//part 3, speeding them up verrry slowly.


//level 1: only donuts
    //<X>1 easy intro
    //<X>2 faster
    //<X> trigger
    //<X>4 fastest, and long!
    //Endless fastest pointer

//TODO: a quick reaction level.  fast random launch.
    // Random pause time,
    // Launch distance is a random; launch speed is a product of distance

//level 2: bad balls
    //1 alternate bad balls with donuts then simple collect donuts through bad balls
    //1 alt : I like the idea of a simple pointer where you have to dodge a single bad ball
        //every so often, but many balls either pose no threat, or make the level impossible to finish. How can I improve on this?
    //2<X>slow donuts that you must collect through a simple pattern of many bad balls
    //3<X>the line of balls
    //4 Long list of complex bad ball patterns. All bad balls; 1 donut
    //Super hexagon style with occasional donut


//Needs some work
//level 3: visual challenges with donut
    // 1-3 style that hides after 60%
    // slow, hides after 30%, released in groups.
    // slow, 1-3 style, appears after 60%
    //Ghost donuts
    //Disappear at 20% 1-5 style

//level 4: visual challenges with world
    //Wacky camera
    //doge!
    //InputLightsOff
    //ghost donuts + donut background
    //1-5 style with consistent rotator.

    //TODO:
    //Can the camera pan around the playing field?
    //How about zoom in and zoom out?
    //Maybe try to mimic ITG2's Driven course?

//level 5: everything
    //Everything. Lasts 1.5 minutes, or something. Start with only donuts. Hard AF. Throw in bad balls,
    //throw in disappearing balls, throw in all of level 4's mechanics. Then something stupid at the end,
    //Like a boss or something, but not really. I don't know what. Something stupid:
        //
//I don't know if I want a bonus stage in level 5
//If not, level 5 turns gold after a normal clear.
//How can I create the sensation of "I have to find a way to beat this level. How do I do it?"

//TODO: Ideas that I should try:
    //How about a "memory game" with the LightsOff mechanic? The game shows the donuts, then the screen goes dark for some seconds?


/**
 * Created by Alexander on 11/23/2015.
 */
public class MainGame implements Screen {

    //Game stuff.
    private MyGdxGame main;
    float worldWidth = BunkyGDX.GAME_WIDTH;
    float worldHeight = BunkyGDX.GAME_HEIGHT;
    float cameraPositionX = BunkyGDX.midPointX();
    float cameraPositionY = BunkyGDX.midPointY();
    OrthographicCamera camera;
    OrthographicCamera hudCamera = new OrthographicCamera(worldWidth, worldHeight);
    private Stage stage;
    private Stage hudStage;

    //pics n' stuff
    private BrickWall brickWall;
    private TextBox ballCounterText = new TextBox(0, 0, "0", TextBox.mainGameFont);
    private TextBox goldCounterText = new TextBox(0, 0, "0", TextBox.goldFont);
    private TextBox levelText = new TextBox(0, 0, "0", TextBox.mainGameFont);
    private TextBox resultsText = new TextBox(0, 0, "0", TextBox.resultsFont);
    private SpriteBatch batch;
    private SpriteBatch hud;
    static private Texture portalTexture = new Texture(Gdx.files.internal("portal.png"));
    static private Texture fieldTexture = new Texture(Gdx.files.internal("new field.png"));
    static private Texture transparentField = new Texture(Gdx.files.internal("field.png"));
    //static private TextureAtlas brokenDonutAtlas = new TextureAtlas("animation/broken donut.atlas");
    //static private Animation brokenDonut = new Animation(0.2f, brokenDonutAtlas.getRegions());
    static Background background = new Background("cool bg.atlas", 1.5f, 45, 4);
    private Alex alex;
    private Sprite portalSprite;
    private CircleSprite fieldSprite;
    ShapeRenderer shapeRenderer = new ShapeRenderer();

    //main game stuuff
    Integer totalBallCounter = 0;
    boolean showTotalBallCounter = false;
    boolean startedOnPartOne = false;
    private int ballsSpit = 0;
    ArrayList<Donut> donuts = new ArrayList<Donut>();
    ArrayList<BadBall> badBalls = new ArrayList<>();
    ArrayList<TestDot> testDots = new ArrayList<>();
    ArrayList<BunkyParticle> particles = new ArrayList<>();
    private int currentLevel;
    private int currentPart;
    //Both height and width need to be set to the screen's width because the camera rotates.
    private Rectangle screenBox = new Rectangle(-BunkyGDX.GAME_WIDTH, -BunkyGDX.GAME_WIDTH, BunkyGDX.GAME_WIDTH * 2, BunkyGDX.GAME_WIDTH * 2);

    private float cameraVolatility = 0;
    private final float CAMERA_SETTLE_AMOUNT = 0.2f;
    ArrayList<Obstruction> obstructions = new ArrayList<Obstruction>(0);
    private ArrayList<FakeDonut> fakeDonuts = new ArrayList<>(0);
    //Results variables
    private static float winRoationTimer = 0;
    private final float buttonHeight = 192;
    private final float buttonWidth = 592;
    private final float buttonDistance = 16;
    boolean leftPressed = false;
    boolean rightPressed = false;

    //win part variables
    private static final int maxWinRotation = 360;
    private static final float winRotationPerFrame = 20;
    private static final float winSizePerFrame = 0.1f;
    private static float currentWinSize = 1;
    private Flipper growing = new Flipper(true);



    //level info.
    private Music gameMusic; //music is different depending on the level (I think)

    //sound effects
    private Sound chomp;
    private Sound fart;
    private Sound yay;


    private void goBackToTitleScreen(){
        gameMusic.stop();
        dispose();
        main.setScreen(new TitleScreen(main));
    }

    private void makeBackToTitleScreenButton() {
        final TextButton backToTitle = new TextButton("Back to stage select", main.getSkin(), "default");

        backToTitle.setHeight(buttonHeight);
        backToTitle.setWidth(buttonWidth);
        backToTitle.getLabel().setFontScale(4);
        backToTitle.setY(BunkyGDX.midPointY());
        backToTitle.setX(BunkyGDX.midPointX() - buttonWidth / 2);
        backToTitle.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goBackToTitleScreen();
            }
        });
        hudStage.addActor(backToTitle);
    }

    public MainGame(MyGdxGame game, int level, int part) {
        GameEvent.resetEventData();
        if(part == 1)
            startedOnPartOne = true;
        currentLevel = level;
        currentPart = part;
        main = game;
        //the background depends on the level
        switch(currentLevel) {
            case 1:
                background.setBackground("cooler bg.atlas");
                break;
            case 2:
                background.setBackground("cool bg.atlas");
                break;
            case 3:
                background.setBackground("coolester bg.atlas");
                break;
            case 4:
                background.setBackground("coolestest bg.atlas");
                break;
            case 5:
                background.setBackground("coolest bg.atlas");
                break;
        }
        background.changeScale(4);
    }

    //This needs to be called every frame.
    private void cameraMovementLogic(){
        if(cameraVolatility > 0) {
            cameraVolatility -= CAMERA_SETTLE_AMOUNT;
        }
        else
            cameraVolatility = 0;
        setRandomCameraPosition(cameraVolatility, cameraVolatility);
    }

    private void bumpCamera(float magnitude){
        cameraVolatility = magnitude;
    }

    private void setRandomCameraPosition(float maxX, float maxY){
        //System.out.println(camera.position.x + "****" + camera.position.y);
            camera.position.set(cameraPositionX + ((float) BunkyGDX.randomDouble(0, maxX)),
                cameraPositionY + (float) BunkyGDX.randomDouble(0, maxY),
                0
                );
    }

    //I wanna handle events differently
    //Notepad doc:
    //Each phase has an ArrayList<Event>
    //event has a timestamp, when the phase timer reaches this time, the event starts.
    //when the event starts, it gets added to MainGame::ArrayList<Event> activeEvents
    //activeEvents gets iterated through RIGHT BELOW.
    //When the event is over, it gets removed from the list.

    //There should be TimedEvent and FrequentEvent in Level.java
        //TimedEvent happens on a timestamp
        //FrequentEvent happens every x frames.








    private void handleEvents(){
        //TODO Here I need to get timed events and frequent events seperately somehow

        //Get events if they're ready
        Level.startNewEvents();


        //Here events are handled
        for(int eventCounter = 0; eventCounter != Level.numberOfActiveEvents(); eventCounter++){
            GameEvent event = Level.getEvent(eventCounter);
            event.countTime();

            if(event.isActive()){
                switch(event.getEventType().toString()){
                    case "Bump":
                        bumpCamera(Bumper.magnitude);
                        event.finishEvent();
                        break;
                    case "PulsingZoom":
                        Zoomer.pulseTarget();
                        Zoomer.goToTargetZoom();
                        camera.zoom = Zoomer.currentZoom;
                        break;
                    case "Zoom":{
                        if(Zoomer.goToTargetZoom())
                            event.finishEvent();
                        camera.zoom = Zoomer.currentZoom;
                        break;
                    }
                    case "WackyRotator": {
                        double rotateSpeed = Rotator.getRotationSpeed();
                        if (BunkyGDX.coinFlip())
                            rotateSpeed *= -1;
                        camera.rotate((float)rotateSpeed);
                        if(Rotator.countDown())
                            event.finishEvent();
                        break;
                    }
                    case "Rotator": {
                        float rotateSpeed = (float)Rotator.getRotationSpeed();
                        if (event.eventJustStarted())
                            Rotator.setRandomRotatorDirection();
                        if (Rotator.getRotatorDirection())
                            rotateSpeed *= -1;
                        camera.rotate(rotateSpeed);
                        if(Rotator.countDown())
                            event.finishEvent();
                        break;
                    }
                    case "AlternatingRotator": {
                        if(Rotator.flipTimer.IncrementAndTest()){
                            Rotator.direction.Flip();
                            Rotator.setRandomFlipTime(Integer.parseInt(event.getInstruction(0)),
                                    Integer.parseInt(event.getInstruction(1)));
                        }
                        float rotateSpeed = Float.parseFloat(event.getInstruction(2));
                        if (Rotator.getRotatorDirection())
                            rotateSpeed *= -1;
                        camera.rotate(rotateSpeed);
                        if(Rotator.countDown())
                            event.finishEvent();
                        break;
                    }
                    case "MakeFakeSide": {
                        FloatPoint newPointA = BunkyGDX.randomLocationOnEdgeOfScreen(-1);
                        //This stinks
                        FloatPoint newPointB = new FloatPoint(portalSprite.getX() + portalSprite.getWidth() / 2, portalSprite.getY() + portalSprite.getHeight() / 2);
                        Angle newAngle = new Angle(Angle.angleBetweenPoints(newPointA, newPointB));

                        fakeDonuts.add(new FakeDonut(
                                newPointA.x,
                                newPointA.y,
                                5f,
                                (float) newAngle.value()
                        ));
                        event.finishEvent();
                        break;
                    }
                    case "MakeFakeCenter": {
                        FloatPoint newPointA = new FloatPoint(BunkyGDX.midPointX(), BunkyGDX.midPointY());
                        Angle newAngle = BunkyGDX.randomAngle();

                        fakeDonuts.add(new FakeDonut(
                                newPointA.x,
                                newPointA.y,
                                5f,
                                (float) newAngle.value()
                        ));
                        event.finishEvent();
                        break;
                    }
                    case "RandomDoge":
                        makeRandomDoge();
                        event.finishEvent();
                        break;
                    case "DonutBackground":
                        //copied from the constructor :(
                        fieldSprite = new CircleSprite(transparentField);
                        fieldSprite.setCenterY(BunkyGDX.midPointY());
                        fieldSprite.setCenterX(BunkyGDX.midPointX());
                        fieldSprite.setScale(2.75f);
                        background.setBackground("donut.atlas");
                        background.changeScale(1);

                        break;
                    case "InputZoom":
                        InputZoom.followInput(camera, leftPressed, rightPressed);
                        break;
                    case "InputTransparency":
                        InputTransparency.logic(leftPressed, rightPressed);

                        for(Donut donut : donuts){
                            donut.setTransparency(InputTransparency.getCurrentTransparency());
                        }
                        break;
                    case "InputMove":
                        InputMove.followInput(camera, cameraPositionX, cameraPositionY, leftPressed, rightPressed);
                        break;
                    //Lights go off for x seconds then comes back on at y speed.
                    //TODO: Fix this. Fix other light problems
                    case "LightsOff":
                        if(!LightsOff.lightsAreAllTheWayOff()){
                            LightsOff.turnLightsOffSome();
                            }else if(LightsOff.offTime.IncrementAndTest()){
                            LightsOff.turnLightsOn();
                            event.finishEvent();
                        }
                        break;
                    case "ReverseInputLightsOff":
                        LightsOff.turnLightsOnSome();
                        if(alex.getJustMovedLeft() || alex.getJustMovedRight()){
                            if(LightsOff.recentLower)
                                break;
                            else {
                                LightsOff.recentLower = true;
                                LightsOff.turnLightsOffLots();
                            }
                        } else {
                            LightsOff.recentLower = false;
                        }
                        break;
                    case "InputLightsOff":
                        if(alex.getJustMovedLeft() || alex.getJustMovedRight())
                            LightsOff.turnLightsOffLots();
                        else
                            LightsOff.turnLightsOnSome();
                        break;
                    case "BrickWall":
                        brickWall = new BrickWall(Boolean.parseBoolean(event.getInstruction(0)), 4);
                        event.finishEvent();
                        break;
                    case "BumpOnMove":
                        if(alex.getJustMovedLeft() || alex.getJustMovedRight())
                            bumpCamera(BumpOnMove.magnitude);
                        break;
                    case "DogeOnMove":
                        if(alex.getJustMovedLeft() || alex.getJustMovedRight()){
                            if(DogeOnMove.recentDoge)
                                break;
                            else{
                                DogeOnMove.recentDoge = true;
                                makeRandomDoge();
                            }
                        }else{
                            DogeOnMove.recentDoge = false;
                        }
                        break;
                    case "LowerLightsOnMove":
                        LightsOff.turnLightsOnSome();
                        if(alex.getJustMovedLeft() || alex.getJustMovedRight()) {
                            if(LightsOff.recentLower)
                                break;
                            else{
                                LightsOff.recentLower = true;
                                LightsOff.turnLightsOffLots();
                            }
                        }else{
                            LightsOff.recentLower = false;
                        }
                        break;
                    default:
                        //Uh oh a bad happened
                        break;
                }
            }
        }
        Level.removeExpiredEvents();

        //Move this to its own function and handle events better.
                /*TimedEvent.incrementTimer();
                for(TimedEvent event : events){
                    if(event.isActive()) {
                        switch(event.getEventType().toString()){
                            case "WackyRotator":
                                float rotateSpeed = Integer.parseInt(event.getInstruction(0));
                                if(BunkyGDX.coinFlip())
                                    rotateSpeed *= -1;
                                camera.rotate(rotateSpeed);
                                break;
                            case "Rotator":
                                float rotateSpeed2 = Integer.parseInt(event.getInstruction(0));
                                if (event.eventJustStarted())
                                    event.setRandomRotatorDirection();
                                if(event.getRotatorDirection())
                                    rotateSpeed2 *= -1;
                                camera.rotate(rotateSpeed2);
                                break;
                            case "MakeFake":
                                FloatPoint newPointA = BunkyGDX.randomLocationOnEdgeOfScreen(-1);
                                //This stinks
                                FloatPoint newPointB = new FloatPoint(portalSprite.getX() + portalSprite.getWidth() / 2, portalSprite.getY() + portalSprite.getHeight() / 2);
                                Angle newAngle = new Angle(Angle.angleBetweenPoints(newPointA, newPointB));

                                fakeDonuts.add(new FakeDonut(
                                        newPointA.x,
                                        newPointA.y,
                                        5f,
                                        (float)newAngle.value()
                                        ));
                                break;
                            case "RandomDoge":
                                int decision = BunkyGDX.randomInt(2, 3);
                                FloatPoint newLocation = BunkyGDX.randomLocationOnEdgeOfScreen(decision);
                                float newDogeAngle = 180;
                                if(decision == 2)
                                    newDogeAngle = 0;

                                obstructions.add(new Doge(
                                        newLocation.x,
                                        newLocation.y,
                                        5,
                                        newDogeAngle
                                ));
                                break;
                            case "DonutBackground":
                                //copied from the constructor :(
                                fieldSprite = new CircleSprite(transparentField);
                                fieldSprite.setCenterY(midPointY);
                                fieldSprite.setCenterX(midPointX);
                                fieldSprite.setScale(11);
                                background.setBackground("donut.atlas");
                                background.changeScale(1);

                                break;
                            default:
                                //Uh oh a bad happened
                                break;
                        }
                    }
                }*/

    }


    //No longer need this, Phase will take care of loading events.
/*
    private void loadEvents(String fileName){

        FileHandle handle = Gdx.files.internal(fileName);
        if(!handle.exists())
            return;
        String string = handle.readString();
        ArrayList<String> instructions = BunkyGDX.getLines(string);
        //patternPicker = PatternPicker.valueOf(instructions.get(0));

        //go through each line and read patterns.
        for(int lineCounter = 0; lineCounter != instructions.size() - 1; lineCounter++) {
            events.add(new TimedEvent(instructions.get(lineCounter)));
        }
        TimedEvent.setResetTime(Integer.parseInt(instructions.get(instructions.size() - 1)));
    }*/
    @Override
    public void show() {
        //camera.lookAt(10, 10, 0);
        alex = new Alex(0, 0);
        batch = new SpriteBatch();
        hud = new SpriteBatch();
        portalSprite = new Sprite(portalTexture);
        fieldSprite = new CircleSprite(fieldTexture);
        portalSprite.setCenterX(BunkyGDX.midPointX());
        portalSprite.setCenterY(BunkyGDX.midPointY());
        fieldSprite.setCenterY(BunkyGDX.midPointY());
        fieldSprite.setCenterX(BunkyGDX.midPointX());
        fieldSprite.setScale(2.75f);
        //Gdx.input.setInputProcessor(this);

        startLevel();

        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/kazoo.wav"));
        chomp = Gdx.audio.newSound(Gdx.files.internal("sounds/chomp.wav"));
        fart = Gdx.audio.newSound(Gdx.files.internal("sounds/fart.wav"));
        yay = Gdx.audio.newSound(Gdx.files.internal("sounds/yay.wav"));
        //I don't know how to get the length of a music file,
        // but I do know that the kazoo music is ~45 seconds long.
        gameMusic.setPosition((float) Math.random() * 40);// gameMusic.);

        gameMusic.setLooping(true);
        gameMusic.play();
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setInputProcessor(hudStage);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        portalSprite.rotate(10);
        cameraMovementLogic();

        FloatPoint ptB = new FloatPoint(portalSprite.getX(), portalSprite.getY());
        alex.lookAtPoint(ptB);


        //ball logic in all MainGame states
        for(Donut donut : donuts){
            //Handle DecreaseTransparency instruction from Level
            donut.increaseTransparencyAfterSomeDistance(
                    Level.getIncreaseTransparencyByThisMuch(),
                    Level.getIncreaseTransparencyAfter()
            );
            donut.move();
            //Don't worry. The donut won't shrink if its shrink rate == 1
            donut.shrink();
        }
        for(BadBall badBall : badBalls){
            //Handle DecreaseTransparency instruction from Level
            badBall.increaseTransparencyAfterSomeDistance(
                    Level.getIncreaseTransparencyByThisMuch(),
                    Level.getIncreaseTransparencyAfter()
            );
            badBall.move();
        }
        for(TestDot testDot : testDots){
            testDot.move();
        }
        for (Iterator<FakeDonut> iterator = fakeDonuts.iterator(); iterator.hasNext();) {
            FakeDonut fake = iterator.next();
            if (!Intersector.overlaps(
                    fake.getBoundingRectangle(),
                    screenBox))
                //Will this work? Or do I have to use regular iterators?
                iterator.remove();

        }
        for(FakeDonut fakeDonut : fakeDonuts){
            fakeDonut.move();
            fakeDonut.rotate(MakeRotateFake.rotationSpeed);
            if(Intersector.overlaps(
                    fakeDonut.getBoundingRectangle(),
                    alex.getBoundingRectangle()))
                fakeDonut.lowerAlpha();
            else
                fakeDonut.recoverAlpha();
        }

        for(Obstruction obstruction : obstructions){
            obstruction.move();
        }
        if(background != null)
            background.move();

        //Also I like the idea for fake donuts flying across the screen as obstructions.
        //Maybe you can make them fade away like ghosts when you hover over them?

        //take input
        leftPressed = false;
        rightPressed = false;
        for (int touchPoint = 0; touchPoint != 20; touchPoint++) {
            if (Gdx.input.isTouched(touchPoint)) {
                float x = Gdx.input.getX(touchPoint);
                float midPointX = BunkyGDX.GAME_WIDTH / 2;
                if (x > midPointX && x != 0)
                    leftPressed = true;
                if (x < midPointX && x != 0)
                    rightPressed = true;
            }
        }

        switch(mainGameState){
            case Main:
                //process input
                if (leftPressed) {
                    if(main.getSaveData().getBoolean("invertedControls")){
                        alex.moveRight();
                        if(!main.getSaveData().getBoolean("staticCamera"))
                            camera.rotate(alex.getSpeed());
                    }else{
                        alex.moveLeft();
                        if(!main.getSaveData().getBoolean("staticCamera"))
                            camera.rotate(-alex.getSpeed());
                    }
                }else if (rightPressed) {
                    if(main.getSaveData().getBoolean("invertedControls")){
                        alex.moveLeft();
                        if(!main.getSaveData().getBoolean("staticCamera"))
                            camera.rotate(-alex.getSpeed());
                    }else{
                        alex.moveRight();
                        if(!main.getSaveData().getBoolean("staticCamera"))
                            camera.rotate(alex.getSpeed());
                    }
                }
                else alex.stayPut();

                //if(ballsSpit < Level.getTotalBallsToWin()) {
                //Level.printStatus();
                if(!Level.levelIsOver()){
                    if(!Level.getCurrentPhase().isPaused()) {
                        Pattern pattern = Level.getCurrentPhase().getCurrentPattern();
                        pattern.patternLogic(alex);
                        //Level.getCurrentPhase().printCurrentPatternName();
                        while (Level.badBallsAvailable()) {
                            badBalls.add(Level.getNextBadBall());
                        }
                        while (Level.testDotsAvailable()) {
                            testDots.add(Level.getNextTestDot());
                        }
                        while (ballsSpit < Level.getTotalBallsToWin() &&
                                Level.donutsAvailable()) {
                            donuts.add(Level.getNextDonut());

                            //remove this
                            //bumpCamera(8);
                            //end remove this

                            ballsSpit++;
                        }
                    }
                    if(!Level.levelIsOver())
                        handleEvents();
                    Level.goToNextPatternIfReady();
                }
                alex.alexLogic();
                //This is the only way that I know of that is safe to delete items from a container
                //while you're iterating through it.
                for (Iterator<Donut> iterator = donuts.iterator(); iterator.hasNext();) {
                    Ball ball = iterator.next();
                    if (Intersector.overlaps(
                            ball.getCollisionCircle(),
                            alex.getBoundingCircle())) {

                        //BunkyParticle newParticle = new BunkyParticle(ball.getX(), ball.getY(), brokenDonut);
                        //newParticle.getSprite().setScale(ball.getScaleX(), ball.getScaleY());
                        //particles.add(newParticle);
                        Level.eatABall();
                        alex.startEating();
                        updateBallCounterText();
                        updateGoldCounterText();
                        chomp.play();
                        if (Level.getBallsCollected() >= Level.getTotalBallsToWin() && currentPart != 5) {
                            //This is the part where the next level gets unlocked.
                            StringBuilder test = new StringBuilder();
                            test.append("completed_");
                            test.append(currentLevel);
                            test.append('_');
                            test.append(currentPart);
                            main.getSaveData().putBoolean(test.toString(), true);
                            main.getSaveData().flush();

                            if(currentPart == 4 && !startedOnPartOne)
                                goToState(MainGameState.WinLevel);
                            else
                                goToState(MainGameState.WinPart);
                        }
                        // Remove the current element from the iterator and the list.
                        iterator.remove();
                    }

                    //Prob: If I lose, I'm still testing collision with donuts.

                    if(!Intersector.overlaps(
                            ball.getCollisionCircle(),
                            fieldSprite.getCollisionCircle())){
                        if(currentPart != 5) {
                            goToState(MainGameState.Lose);
                            break;
                        }
                        else {
                            goToState(MainGameState.EndPart5);
                            break;
                        }
                    }
                }

                for (Iterator<BadBall> iterator = badBalls.iterator(); iterator.hasNext();) {
                    BadBall badBall = iterator.next();
                    if (Intersector.overlaps(
                            badBall.getCollisionCircle(),
                            alex.getBoundingCircle())) {
                        if(currentPart != 5)
                            goToState(MainGameState.Lose);
                        else
                            goToState(MainGameState.EndPart5);
                    }
                        if (!Intersector.overlaps(
                            badBall.getCollisionCircle(),
                            screenBox))
                        // Remove the current element from the iterator and the list.
                        iterator.remove();
                }//////
                for (Iterator<TestDot> iterator = testDots.iterator(); iterator.hasNext();) {
                    TestDot testDot = iterator.next();
                    if (!Intersector.overlaps(
                            testDot.getCollisionCircle(),
                            screenBox))
                        // Remove the current element from the iterator and the list.
                        iterator.remove();
                }
                break;
            case EndPart5:
            case Lose:
                alex.getSprite().setY(alex.getSprite().getY() - 5);
                alex.getSprite().rotate(40);
                for (Iterator<Donut> iterator = donuts.iterator(); iterator.hasNext();) {
                    Donut ball = iterator.next();
                    //remove ball from list if it's offscreen
                    //Problem: iterator is being removed when I don't want it, because of the camera.
                    //When you find a solution, fix the badBall part below.
                    if (!Intersector.overlaps(
                            ball.getCollisionCircle(),
                            screenBox)) {
                        // Remove the current element from the iterator and the list.
                        iterator.remove();
                    }
                    else if(!Intersector.overlaps(
                            ball.getCollisionCircle(),
                            fieldSprite.getCollisionCircle())){
                        ball.removeTransparency();
                    }
                }
                for (Iterator<BadBall> iterator = badBalls.iterator(); iterator.hasNext();) {
                    BadBall ball = iterator.next();
                    //remove ball from list if it's offscreen
                    if (!Intersector.overlaps(
                            ball.getCollisionCircle(),
                            screenBox)) {
                        // Remove the current element from the iterator and the list.
                        iterator.remove();
                    }
                    else if(!Intersector.overlaps(
                            ball.getCollisionCircle(),
                            fieldSprite.getCollisionCircle())){
                        ball.removeTransparency();
                    }
                }
                break;
            case WinLevel:
                if(alex.getSprite().getScaleX() < Alex.maxWinLevelScale) {
                    alex.getSprite().setScale(
                        alex.getSprite().getScaleX() + 0.1f,
                        alex.getSprite().getScaleY() + 0.1f
                    );
                }
                winRoationTimer += 0.025;
                alex.getSprite().rotate(300 * (float) Math.sin(winRoationTimer));
                break;
            case WinPart:
                if(growing.value()){
                    winRoationTimer += winRotationPerFrame;
                    currentWinSize += winSizePerFrame;
                    if(winRoationTimer >= maxWinRotation && currentWinSize >= Alex.maxWinPartScale)
                        growing.Flip();

                }else{
                    winRoationTimer -= winRotationPerFrame;
                    currentWinSize -= winSizePerFrame;
                    boolean good1 = false;
                    boolean good2 = false;
                    if(winRoationTimer < 0){
                        winRoationTimer = 0;
                        good1 = true;
                    }
                    if(currentWinSize < Alex.defaultScale){
                        currentWinSize = Alex.defaultScale;
                        good2 = true;
                    }
                    if(good1 && good2){
                        goToNextPart();
                    }
                }
                alex.getSprite().setScale(currentWinSize);
                alex.getSprite().rotate(winRoationTimer);

                break;
        }

        //end logic()
        //begin render()


        camera.update();
        //This is where the camera is told to follow this SpriteBatch
        batch.setProjectionMatrix(camera.combined);
        //camera.rotate(5);
        background.draw();
        batch.begin();
        fieldSprite.draw(batch);
        portalSprite.draw(batch);
        for(FakeDonut fake : fakeDonuts){
            fake.draw(batch, fake.getAlpha());
        }

        shapeRenderer.setProjectionMatrix(camera.combined);
        for(BadBall badBall : badBalls){
            badBall.draw(batch, badBall.getAlpha());

        }
        for(Donut donut : donuts){
            donut.draw(batch, donut.getAlpha());

        }
        for(TestDot testDot : testDots){
            testDot.draw(batch, testDot.getAlpha
                    ());
        }
        //batch.draw(alex.getCurrentFrame(), alex.getX(), alex.getY());
        for(Iterator<BunkyParticle> iterator = particles.iterator(); iterator.hasNext();){
            BunkyParticle particle = iterator.next();
            particle.animate();
            particle.getSprite().draw(batch);
            if(particle.readyToDelete())
                iterator.remove();
        }
        batch.end();

        if(!LightsOff.lightsAreAllTheWayOn()) {
            System.out.println(LightsOff.getAlpha());
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0, 0, 0, (float)LightsOff.getAlpha());
            shapeRenderer.rect(-99999999, -99999999, 999999999, 999999999);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
        batch.begin();
        alex.animate();
        alex.getSprite().draw(batch);
        for(Obstruction obstruction : obstructions){
           obstruction.draw(batch);
       }
       batch.end();


/*        for(BadBall badBall : badBalls){
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            Circle renderThis = badBall.getCollisionCircle();
            shapeRenderer.circle(renderThis.x, renderThis.y, renderThis.radius);
            shapeRenderer.end();

        }
        for(Donut donut : donuts){
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            Circle renderThis = donut.getCollisionCircle();
            shapeRenderer.circle(renderThis.x, renderThis.y, renderThis.radius);
            shapeRenderer.end();
        }
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        Circle renderThis = alex.getBoundingCircle();
        shapeRenderer.circle(renderThis.x, renderThis.y, renderThis.radius);
        shapeRenderer.end();*/

        batch.setProjectionMatrix(hudCamera.combined);
        hudCamera.update();
        //This is where the camera is told to follow this SpriteBatch
        hud.begin();
        if(brickWall != null){
            brickWall.move();
            brickWall.draw(hud);
        }
        if(mainGameState != MainGameState.Main)
            resultsText.draw(hud);
        //How do I get a BitmapFont's string? Can I? Next:
        if(currentPart != 5) {
            ballCounterText.draw(hud);
        }
 //       if(showTotalBallCounter)
 //           goldCounterText.draw(hud);
        levelText.draw(hud);
        hud.end();
        //I had to move stage.draw() outside of batch.begin() and batch.end() so the renderer would stop giving me problems.
        //stage.getViewport().apply();
        hudStage.draw();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        gameMusic.dispose();

        batch.dispose();
        hudStage.dispose();
        stage.dispose();
    }

    //work on this
    enum MainGameState{
        Main,
        WinPart,
        WinLevel,
        Lose,
        EndPart5
    }
    private MainGameState mainGameState = MainGameState.Main;

    private void startLevel(){
        goToState(mainGameState.Main);

        //Maingame()
        StringBuilder levelPath = new StringBuilder("level");
        levelPath.append(currentLevel);
        levelPath.append("_");
        levelPath.append(currentPart);
        levelPath.append(".txt");
        Level.loadInstructions(levelPath.toString());
        Level.startCurrentPattern();
        alex.putInDefaultPosition();

        //TODO: I need to somehow place the camera in the default position so when I win and start the next part,
            //alex stays in the same position.

        camera = new OrthographicCamera(worldWidth, worldHeight);
        camera.position.set(cameraPositionX, cameraPositionY, 0);

        stage = new Stage(new StretchViewport(worldWidth, worldHeight, camera));
        hudStage = new Stage(new StretchViewport(worldWidth, worldHeight, hudCamera));

        //reset results
        currentWinSize = Alex.defaultScale;
        winRoationTimer = 0;

        //reset level variables
        Level.resetBallCounter();
        ballsSpit = 0;
        growing.setValue(true);
        cameraPositionY = BunkyGDX.midPointY();
        cameraPositionX = BunkyGDX.midPointX();

        updateBallCounterText();
        updateGoldCounterText();
        updateLevelText();
        Level.getCurrentPhase().startPhase();

    }

    private void updateBallCounterText(){
        ballCounterText.setText(Level.getBallsCollected() + " / " + Level.getTotalBallsToWin());
        ballCounterText.placeOnLeft(16);
        ballCounterText.placeOnTop(16);
    }

    private void updateGoldCounterText(){
        StringBuilder sb = new StringBuilder();
        if(startedOnPartOne)
            sb.append("*");
        Integer displayThis = totalBallCounter;
        if(currentPart == 5)
            displayThis += Level.getBallsCollected();
        else
            sb.append("+ ");
        sb.append(displayThis.toString());
        if(startedOnPartOne)
            sb.append("*");
        goldCounterText.setText(sb.toString());
        if(currentPart != 5) {
            goldCounterText.placeOnLeft(16);
            goldCounterText.placeOnTop(128);
        }else{
            goldCounterText.placeOnLeft(16);
            goldCounterText.placeOnTop(16);
        }
    }

    private void updateLevelText(){
        levelText.setText(currentLevel + " - " + currentPart);
        levelText.placeOnRight(16);
        levelText.placeOnTop(16);
    }

    private void updateResults(String newText){
        resultsText.setText(newText);
        resultsText.placeInMiddleX();
        resultsText.placeOnTop(128);
    }

    private void goToNextPart(){
        showTotalBallCounter = true;
        currentPart++;
        startLevel();
    }

    private void goToState(MainGameState newMainGameState){
        if(newMainGameState == mainGameState)
            return;
        mainGameState = newMainGameState;
        if(newMainGameState != MainGameState.Main)
            alex.stopEating();
        switch(mainGameState){
            case Main:
                break;
            case WinPart:
                totalBallCounter += Level.getTotalBallsToWin();
                if(startedOnPartOne && currentPart == 4)
                    updateResults("Excellent!!");
                else
                    updateResults("You win!");
                yay.play();
                break;
            case WinLevel:
                updateResults("You win!");
                yay.play();
                makeBackToTitleScreenButton();
                break;
            case EndPart5:
                updateResults("Final score: " + (totalBallCounter + Level.getBallsCollected()));
                StringBuilder record = new StringBuilder("record");
                record.append(currentLevel);
                Integer oldRecord = main.getSaveData().getInteger(record.toString());
                //Put in new high score if it's better.
                if(totalBallCounter + Level.getBallsCollected() > oldRecord) {
                    main.getSaveData().putInteger(record.toString(), totalBallCounter + Level.getBallsCollected());
                    main.getSaveData().flush();
                }
                makeBackToTitleScreenButton();
                break;
            case Lose:
                Integer deaths = main.getSaveData().getInteger("deathCounter");
                deaths++;
                main.getSaveData().putInteger("deathCounter", deaths);
                main.getSaveData().flush();
                updateResults("You lose!");
                fart.play();
                final TextButton tryAgain = new TextButton("Try again", main.getSkin(), "default");
                final TextButton backToTitle2 = new TextButton("Back to stage select", main.getSkin(), "default");
                tryAgain.setHeight(buttonHeight);
                tryAgain.setWidth(buttonWidth);
                tryAgain.getLabel().setFontScale(4);
                tryAgain.setX(BunkyGDX.midPointX() - buttonWidth - buttonDistance);
                tryAgain.setY(BunkyGDX.midPointY());
                tryAgain.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        dispose();
                        main.setScreen(new MainGame(main, currentLevel, currentPart));
                    }
                });
                backToTitle2.setHeight(buttonHeight);
                backToTitle2.setWidth(buttonWidth);
                backToTitle2.getLabel().setFontScale(4);
                backToTitle2.setX(BunkyGDX.midPointX() + buttonDistance);
                backToTitle2.setY(BunkyGDX.midPointY());
                backToTitle2.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        goBackToTitleScreen();
                    }
                });
                hudStage.addActor(backToTitle2);
                hudStage.addActor(tryAgain);
                break;
        }
    }
    //Next: change ball movement midway? Eh

    private void makeRandomDoge(){
        int decision = BunkyGDX.randomInt(2, 3);
        FloatPoint newLocation = BunkyGDX.randomLocationOnEdgeOfScreen(decision);
        float newDogeAngle = 180;
        if(decision == 2)
            newDogeAngle = 0;

        obstructions.add(new Doge(
                newLocation.x,
                newLocation.y,
                5,
                newDogeAngle
        ));
    }

}

