package com.bunkyware.fonegaem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import java.util.ArrayList;

import javafx.scene.Node;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import javafx.scene.control.Skinnable;
import sun.font.TrueTypeFont;

/**
 * Created by Alexander on 11/23/2015.
 */
// TODO: I needa fix fonts here tooo

public class TitleScreen implements Screen {

    private SpriteBatch batch;
    //private Sprite alexFace;
    private MyGdxGame main;
    float worldWidth = BunkyGDX.GAME_WIDTH;
    float worldHeight = BunkyGDX.GAME_HEIGHT;
    OrthographicCamera camera = new OrthographicCamera(worldWidth, worldHeight);
    private Viewport viewport = new FitViewport(worldWidth, worldHeight, camera);
    private Stage stage = new Stage(new StretchViewport(worldWidth, worldHeight, camera));
    float cameraPositionX = BunkyGDX.midPointX();
    float cameraPositionY = BunkyGDX.midPointY();


    private Texture title = new Texture(Gdx.files.internal("title.png"));
    private Sprite titleSprite = new Sprite(title);
    private static float buttonRowOffset = 16;
    private static float buttonColumnOffset = 640;
    private static float buttonRowDistance = 168;
    private static float buttonColumnDistance = 224;
    private static float bigButtonHeight = 144;
    private static float buttonWidth = 192;
    private static float buttonHeight = 96;
    private static float finalButtonWidth = 864;
    private static Texture deathCounterTexture = new Texture(Gdx.files.internal("death counter.png"));
    private static Sprite deathCounter = new Sprite(deathCounterTexture);
    private TextBox deathText = new TextBox(200, 100, "x ", TextBox.mainGameFont);
    private TextBox myNameText = new TextBox(100, 1100, "" , TextBox.smallMainGameFont);

    //private static BitmapFont recordText = new BitmapFont();
    private static ArrayList<TextBox> recordTexts = new ArrayList<>();
    private static final int levelGoal1 = 40;
    private static final int levelGoal2 = 50;
    private static final int levelGoal3 = 60;
    private static final int levelGoal4 = 70;
    private static float scoreSizeTimer = 0;
    private static final float baseScoreSize = 1;
    private static final float scoreSizeSpeed = 3.3f;
    private static final float scoreSizeMagnitude = 0.1f;

    private static Array<Boolean> levelGoalsReached = new Array<>(0);

    private void goToMainGame(int level, int part){
        dispose();
        main.setScreen(new MainGame(main, level, part));

    }

    public TitleScreen(MyGdxGame game) {
        main = game;
    }

    void loadHighScores(){
        Integer total = 0;
        for(Integer counter = 1; counter != 5; counter++){
            StringBuilder balls = new StringBuilder("record");
            balls.append(counter.toString());
            Integer text = new Integer(main.getSaveData().getInteger(balls.toString()));
            recordTexts.add(new TextBox(buttonColumnOffset,
                    BunkyGDX.GAME_HEIGHT - (((float)counter - 0.5f) * buttonRowDistance),
                    text.toString(),
                    TextBox.goldFont
                    ));
            total += text;
        }
        recordTexts.add(new TextBox(buttonColumnOffset,
                BunkyGDX.GAME_HEIGHT - ((float) 4.5 * buttonRowDistance),
                "Total:",
                TextBox.goldFont
        ));
        recordTexts.add(new TextBox(buttonColumnOffset,
                BunkyGDX.GAME_HEIGHT - ((float) 5.5 * buttonRowDistance),
                total.toString(),
                TextBox.goldFont
        ));

        //Modify goals for each level here.
        if(main.getSaveData().getInteger("record1") >= levelGoal1) //level 1
            levelGoalsReached.add(new Boolean(true));
        else
            levelGoalsReached.add(new Boolean(false));
        if(main.getSaveData().getInteger("record2") >= levelGoal2) //level 2
            levelGoalsReached.add(new Boolean(true));
        else
            levelGoalsReached.add(new Boolean(false));
        if(main.getSaveData().getInteger("record3") >= levelGoal3) //level 3
            levelGoalsReached.add(new Boolean(true));
        else
            levelGoalsReached.add(new Boolean(false));
        if(main.getSaveData().getInteger("record4") >= levelGoal4) //level 4
            levelGoalsReached.add(new Boolean(true));
        else
            levelGoalsReached.add(new Boolean(false));



    }


    @Override
    public void show() {

        ArrayList<Boolean> randomList = BunkyGDX.randomSelection(20, 2);
        for(int counter = 0; counter != randomList.size(); counter++){
            System.out.println(randomList.get(counter));
        }


        loadHighScores();
        titleSprite.setScale(3.1f, 3.1f);
        titleSprite.setCenter(364, 536);
        batch = new SpriteBatch();
        Integer deaths = main.getSaveData().getInteger("deathCounter");
        deathText.setText("x" + deaths.toString());

        //reset button
        final TextButton resetButton = new TextButton(
                "Delete save data",
                main.getSkin(),
                "default");
        resetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.resetSaveToDefault();
                loadHighScores();
                main.setScreen(new TitleScreen(main));
            }
        });
        stage.addActor(resetButton);

        //Unlock everything button
        final TextButton unlockButton = new TextButton(
                "Unlock everything",
                main.getSkin(),
                "default");
        unlockButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.unlockEverything();
                main.setScreen(new TitleScreen(main));
            }
        });
        unlockButton.setX(300);
        stage.addActor(unlockButton);



        //level buttons
        int counter = 0;
        for(Integer counterX = 1; counterX != 5; counterX++) {
            for (Integer counterY = 1; counterY != 5; counterY++) {
                final TextButton button = new TextButton(
                        counterX.toString() + " - " + counterY.toString(),
                        main.getSkin(),
                        "default"
                );
                if(counterY == 1) {
                    button.setHeight(bigButtonHeight);
                    button.setY(BunkyGDX.GAME_HEIGHT - buttonRowOffset - (counterX * buttonRowDistance));
                }
                else {
                    button.setHeight(buttonHeight);
                    button.setY(BunkyGDX.GAME_HEIGHT - buttonRowOffset - (counterX * buttonRowDistance) + buttonHeight / 4);
                }
                button.setWidth(buttonWidth);
                button.getLabel().setFontScale(4);
                button.setX(buttonColumnOffset + (counterY * buttonColumnDistance));
                switch (counter) {
                    case 0:
                        button.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                goToMainGame(1, 1);
                            }
                        });
                        if(Integer.parseInt(recordTexts.get(0).getText()) > levelGoal1)
                            button.setColor(Color.GOLD);
                        else if(main.getSaveData().getBoolean("completed_1_1"))
                            button.setColor(Color.GREEN);
                        stage.addActor(button);
                        break;
                    case 1:
                        if(!main.getSaveData().getBoolean("completed_1_1"))
                            break;
                        button.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                goToMainGame(1, 2);
                            }
                        });
                        if(Integer.parseInt(recordTexts.get(0).getText()) > levelGoal1)
                            button.setColor(Color.GOLD);
                        else if(main.getSaveData().getBoolean("completed_1_2"))
                            button.setColor(Color.GREEN);
                        stage.addActor(button);
                        break;
                    case 2:
                        if(!main.getSaveData().getBoolean("completed_1_2"))
                            break;
                        button.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                goToMainGame(1, 3);
                            }
                        });
                        if(Integer.parseInt(recordTexts.get(0).getText()) > levelGoal1)
                            button.setColor(Color.GOLD);
                        else if(main.getSaveData().getBoolean("completed_1_3"))
                            button.setColor(Color.GREEN);
                        stage.addActor(button);
                        break;
                    case 3:
                        if(!main.getSaveData().getBoolean("completed_1_3"))
                            break;
                        button.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                goToMainGame(1, 4);
                            }
                        });
                        if(Integer.parseInt(recordTexts.get(0).getText()) > levelGoal1)
                            button.setColor(Color.GOLD);
                        else
                        if(main.getSaveData().getBoolean("completed_1_4"))
                            button.setColor(Color.GREEN);
                        stage.addActor(button);
                        break;
                    case 4:
                        if(!main.getSaveData().getBoolean("completed_1_1"))
                            break;
                        button.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                goToMainGame(2, 1);
                            }
                        });
                        if(Integer.parseInt(recordTexts.get(1).getText()) > levelGoal1)
                            button.setColor(Color.GOLD);
                        else if(main.getSaveData().getBoolean("completed_2_1"))
                            button.setColor(Color.GREEN);
                        stage.addActor(button);
                        break;
                    case 5:
                        if(!main.getSaveData().getBoolean("completed_2_1"))
                            break;
                        button.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                goToMainGame(2, 2);
                            }
                        });
                        if(Integer.parseInt(recordTexts.get(1).getText()) > levelGoal1)
                            button.setColor(Color.GOLD);
                        else if(main.getSaveData().getBoolean("completed_2_2"))
                            button.setColor(Color.GREEN);
                        stage.addActor(button);
                        break;
                    case 6:
                        if(!main.getSaveData().getBoolean("completed_2_2"))
                            break;
                        button.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                goToMainGame(2, 3);
                            }
                        });
                        if(Integer.parseInt(recordTexts.get(1).getText()) > levelGoal1)
                            button.setColor(Color.GOLD);
                        else if(main.getSaveData().getBoolean("completed_2_3"))
                            button.setColor(Color.GREEN);
                        stage.addActor(button);
                        break;
                    case 7:
                        if(!main.getSaveData().getBoolean("completed_2_3"))
                            break;
                        button.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                goToMainGame(2, 4);
                            }
                        });
                        if(Integer.parseInt(recordTexts.get(1).getText()) > levelGoal1)
                            button.setColor(Color.GOLD);
                        else if(main.getSaveData().getBoolean("completed_2_4"))
                            button.setColor(Color.GREEN);
                        stage.addActor(button);
                        break;
                    case 8:
                        if(!main.getSaveData().getBoolean("completed_2_1"))
                            break;
                        button.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                goToMainGame(3, 1);
                            }
                        });
                        if(Integer.parseInt(recordTexts.get(2).getText()) > levelGoal1)
                            button.setColor(Color.GOLD);
                        else if(main.getSaveData().getBoolean("completed_3_1"))
                            button.setColor(Color.GREEN);
                        stage.addActor(button);
                        break;
                    case 9:
                        if(!main.getSaveData().getBoolean("completed_3_1"))
                            break;
                        button.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                goToMainGame(3, 2);
                            }
                        });
                        if(Integer.parseInt(recordTexts.get(2).getText()) > levelGoal1)
                            button.setColor(Color.GOLD);
                        else if(main.getSaveData().getBoolean("completed_3_2"))
                            button.setColor(Color.GREEN);
                        stage.addActor(button);
                        break;
                    case 10:
                        if(!main.getSaveData().getBoolean("completed_3_2"))
                            break;
                        button.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                goToMainGame(3, 3);
                            }
                        });
                        if(Integer.parseInt(recordTexts.get(2).getText()) > levelGoal1)
                            button.setColor(Color.GOLD);
                        else if(main.getSaveData().getBoolean("completed_3_3"))
                            button.setColor(Color.GREEN);
                        stage.addActor(button);
                        break;
                    case 11:
                        if(!main.getSaveData().getBoolean("completed_3_3"))
                            break;
                        button.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                goToMainGame(3, 4);
                            }
                        });

                        if(Integer.parseInt(recordTexts.get(2).getText()) > levelGoal1)
                            button.setColor(Color.GOLD);
                        else if(main.getSaveData().getBoolean("completed_3_4"))
                            button.setColor(Color.GREEN);
                        stage.addActor(button);
                        break;
                    case 12:
                        if(!main.getSaveData().getBoolean("completed_3_1"))
                            break;
                        button.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                goToMainGame(4, 1);
                        }
                        });
                        if(Integer.parseInt(recordTexts.get(3).getText()) > levelGoal1)
                            button.setColor(Color.GOLD);
                        else if(main.getSaveData().getBoolean("completed_4_1"))
                            button.setColor(Color.GREEN);
                        stage.addActor(button);
                        break;
                    case 13:
                        if(!main.getSaveData().getBoolean("completed_4_1"))
                            break;
                        button.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                goToMainGame(4, 2);
                            }
                        });
                        if(Integer.parseInt(recordTexts.get(3).getText()) > levelGoal1)
                            button.setColor(Color.GOLD);
                        else if(main.getSaveData().getBoolean("completed_4_2"))
                            button.setColor(Color.GREEN);
                        stage.addActor(button);
                        break;
                    case 14:
                        if(!main.getSaveData().getBoolean("completed_4_2"))
                            break;
                        button.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                goToMainGame(4, 3);
                            }
                        });
                        if(Integer.parseInt(recordTexts.get(3).getText()) > levelGoal1)
                            button.setColor(Color.GOLD);
                        else if(main.getSaveData().getBoolean("completed_4_3"))
                            button.setColor(Color.GREEN);
                        stage.addActor(button);
                        break;
                    case 15:
                        if(!main.getSaveData().getBoolean("completed_4_3"))
                            break;
                        button.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                goToMainGame(4, 4);
                            }
                        });
                        if(Integer.parseInt(recordTexts.get(3).getText()) > levelGoal1)
                            button.setColor(Color.GOLD);
                        else if(main.getSaveData().getBoolean("completed_4_4"))
                            button.setColor(Color.GREEN);
                        stage.addActor(button);
                        break;
                }
                counter++;
            }
        }
        //last level if unlocked
        if( main.getSaveData().getBoolean("completed_1_3") &&
                main.getSaveData().getBoolean("completed_2_3") &&
                main.getSaveData().getBoolean("completed_3_3") &&
                main.getSaveData().getBoolean("completed_4_3")
                ) {
            final TextButton finalButton = new TextButton(
                    "Final",
                    main.getSkin(),
                    "default"
            );
            if(levelGoalsReached.get(0) &&
                    levelGoalsReached.get(1) &&
                    levelGoalsReached.get(2) &&
                    levelGoalsReached.get(3))
                finalButton.setColor(Color.GOLD);
            finalButton.setX(buttonColumnOffset + buttonColumnDistance);
            finalButton.setY(BunkyGDX.GAME_HEIGHT - (buttonRowOffset + buttonRowDistance * 5));
            finalButton.setHeight(bigButtonHeight);
            finalButton.setWidth(finalButtonWidth);
            finalButton.getLabel().setFontScale(4);
            finalButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    goToMainGame(5, 1);
                }
            });
            stage.addActor(finalButton);
        }

        //Inverted controls button
        StringBuilder invertedText = new StringBuilder();
        Color invertedColor = new Color();
        if(main.getSaveData().getBoolean("invertedControls")) {
            invertedText.append("Inverted Controls: On");
            invertedColor.set(Color.GREEN);
        }
        else {
            invertedText.append("Inverted Controls: Off");
            invertedColor.set(Color.RED);
        }

        final TextButton invertedButton = new TextButton(
                invertedText.toString(),
                main.getSkin(),
                "default"
        );
        invertedButton.setX(buttonColumnOffset + buttonColumnDistance);
        invertedButton.setY(BunkyGDX.GAME_HEIGHT - (buttonRowOffset + buttonRowDistance * 6));
        invertedButton.setHeight(bigButtonHeight);
        invertedButton.setWidth(buttonWidth);
        invertedButton.setColor(invertedColor);
        invertedButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boolean current = main.getSaveData().getBoolean("invertedControls");
                main.getSaveData().putBoolean("invertedControls", !current);
                main.getSaveData().flush();
                if (current) {
                    invertedButton.setColor(Color.RED);
                    invertedButton.setText("Inverted Controls: Off");
                }
                else {
                    invertedButton.setColor(Color.GREEN);
                    invertedButton.setText("Inverted Controls: On");
                }
            }
        });
        stage.addActor(invertedButton);


        //camera controls button
        final StringBuilder cameraText = new StringBuilder();
        Color cameraColor = new Color();
        if(main.getSaveData().getBoolean("staticCamera")) {
            cameraText.append("Static Camera: On");
            cameraColor.set(Color.GREEN);
        }
        else {
            cameraText.append("Static Camera: Off");
            cameraColor.set(Color.RED);
        }
        final TextButton cameraButton = new TextButton(
                cameraText.toString(),
                main.getSkin(),
                "default"
        );

        cameraButton.setX(buttonColumnOffset + buttonColumnDistance * 2);
        cameraButton.setY(BunkyGDX.GAME_HEIGHT - (buttonRowOffset + buttonRowDistance * 6));
        cameraButton.setHeight(bigButtonHeight);
        cameraButton.setWidth(buttonWidth);
        cameraButton.setColor(cameraColor);
        cameraButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boolean current = main.getSaveData().getBoolean("staticCamera");
                main.getSaveData().putBoolean("staticCamera", !current);
                main.getSaveData().flush();
                if (current) {
                    cameraButton.setColor(Color.RED);
                    cameraButton.setText("Static Camera: Off");
                } else {
                    cameraButton.setColor(Color.GREEN);
                    cameraButton.setText("Static Camera: On");
                }
            }
        });
        stage.addActor(cameraButton);

        deathCounter.setX(buttonColumnOffset + buttonRowDistance * 6);
        deathCounter.setY(BunkyGDX.GAME_HEIGHT - (buttonRowOffset + buttonRowDistance * 6));
        deathText.setX(buttonColumnOffset + buttonRowDistance * 7 + buttonWidth / 8);
        deathText.setY(BunkyGDX.GAME_HEIGHT - (buttonRowOffset + buttonRowDistance * 5 + buttonHeight / 2));


        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        //logic()


        //end logic()

        Gdx.gl.glClearColor(0.5f, 0.5f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //stage.getViewport().apply();
        stage.draw();
        batch.begin();
/*

        main.getSaveData().putInteger("record1", 100);
        main.getSaveData().putInteger("record2", 20);
        main.getSaveData().putInteger("record3", 30);
        main.getSaveData().putInteger("record4", 40);
        main.getSaveData().putInteger("record5", 50);
        main.getSaveData().flush();
*/

        titleSprite.draw(batch);
        scoreSizeTimer += scoreSizeSpeed;


        for(int counter = 0; counter != 4; counter++){
            if(Integer.parseInt(recordTexts.get(counter).getText()) != 0) {
                if (levelGoalsReached.get(counter)) {
                    float scale = baseScoreSize + BunkyGDX.sinDegrees(scoreSizeTimer) * scoreSizeMagnitude;
                    recordTexts.get(counter).setScale(scale, scale);
                } else if (Integer.parseInt(recordTexts.get(0).getText()) != 0) {
                    recordTexts.get(counter).setScale(baseScoreSize, baseScoreSize);
                }
                recordTexts.get(counter).draw(batch);
            }
        }
        if(Integer.parseInt(recordTexts.get(5).getText()) != 0) {
            if (levelGoalsReached.get(0) && levelGoalsReached.get(1) && levelGoalsReached.get(2) && levelGoalsReached.get(3)) {
                float scale = baseScoreSize + BunkyGDX.sinDegrees(scoreSizeTimer) * scoreSizeMagnitude;
                recordTexts.get(5).setScale(scale, scale);
            } else {
                recordTexts.get(5).setScale(baseScoreSize, baseScoreSize);
            }
            recordTexts.get(4).draw(batch);
            recordTexts.get(5).draw(batch);
        }
        deathCounter.draw(batch);
        deathText.draw(batch);
        myNameText.draw(batch);
        batch.end();

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
        stage.dispose();
        batch.dispose();
        recordTexts.clear();
    }


}
