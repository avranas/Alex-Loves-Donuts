package com.bunkyware.fonegaem;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import javafx.stage.Stage;

public class MyGdxGame extends Game {

	private float lastTouchX;
	private float lastTouchY;
	private Preferences saveData;
	//Make buttons
	private Skin skin;

	public Skin getSkin(){return skin;}

	void resetSaveToDefault(){
		saveData.putInteger("deathCounter", 0);
		saveData.putInteger("record1", 0);
		saveData.putInteger("record2", 0);
		saveData.putInteger("record3", 0);
		saveData.putInteger("record4", 0);
		saveData.putBoolean("completed_1_1", false);
		saveData.putBoolean("completed_1_2", false);
		saveData.putBoolean("completed_1_3", false);
		saveData.putBoolean("completed_1_4", false);
		saveData.putBoolean("completed_2_1", false);
		saveData.putBoolean("completed_2_2", false);
		saveData.putBoolean("completed_2_3", false);
		saveData.putBoolean("completed_2_4", false);
		saveData.putBoolean("completed_3_1", false);
		saveData.putBoolean("completed_3_2", false);
		saveData.putBoolean("completed_3_3", false);
		saveData.putBoolean("completed_3_4", false);
		saveData.putBoolean("completed_4_1", false);
		saveData.putBoolean("completed_4_2", false);
		saveData.putBoolean("completed_4_3", false);
		saveData.putBoolean("completed_4_4", false);
		saveData.putBoolean("invertedControls", false);
		saveData.putBoolean("staticCamera", false);
		saveData.flush();
	}

	void unlockEverything(){
		saveData.putBoolean("completed_1_1", true);
		saveData.putBoolean("completed_1_2", true);
		saveData.putBoolean("completed_1_3", true);
		saveData.putBoolean("completed_1_4", true);
		saveData.putBoolean("completed_2_1", true);
		saveData.putBoolean("completed_2_2", true);
		saveData.putBoolean("completed_2_3", true);
		saveData.putBoolean("completed_2_4", true);
		saveData.putBoolean("completed_3_1", true);
		saveData.putBoolean("completed_3_2", true);
		saveData.putBoolean("completed_3_3", true);
		saveData.putBoolean("completed_3_4", true);
		saveData.putBoolean("completed_4_1", true);
		saveData.putBoolean("completed_4_2", true);
		saveData.putBoolean("completed_4_3", true);
		saveData.putBoolean("completed_4_4", true);
		saveData.flush();
	}


	@Override
	//gets automatically called when the app is first created.
	public void create () {
		TextBox.loadFonts();
		saveData = Gdx.app.getPreferences("saveData");
		skin = new com.badlogic.gdx.scenes.scene2d.ui.Skin(Gdx.files.internal("data/uiskin.json"));
		setScreen(new TitleScreen(this));
	}

/*	void unlockNext(int level, int part){
		if(level == 1 && part == 1) {
			saveData.putBoolean("unlocked_1_2", true);
			saveData.putBoolean("unlocked_2_1", true);
		}
		else if(level == 2 && part == 1) {
			saveData.putBoolean("unlocked_2_2", true);
			saveData.putBoolean("unlocked_3_1", true);
		}
		else if(level == 3 && part == 1) {
			saveData.putBoolean("unlocked_3_2", true);
			saveData.putBoolean("unlocked_4_1", true);
		}
		else if(level == 1 && part == 2) {
			saveData.putBoolean("unlocked_1_3", true);
		}
		else if(level == 2 && part == 2) {
			saveData.putBoolean("unlocked_2_3", true);
		}
		else if(level == 3 && part == 2) {
			saveData.putBoolean("unlocked_3_3", true);
		}
		else if(level == 4 && part == 2) {
			saveData.putBoolean("unlocked_4_3", true);
		}
		saveData.flush();
	}*/
	public Preferences getSaveData(){
		return saveData;
	}

	@Override
	public void resize(int width, int height) {
		screen.resize(width, height);
	}

	@Override
	//destructor, kind of.
	public void dispose(){
		screen.dispose();
	}

	@Override
	//gets called every loop to draw objects on the screen. You've been there.
	public void render() {
		screen.render(0);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}


	//@Override
	public boolean keyDown(int keycode) {

		return false;
	}

	//@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	//@Override
	public boolean keyTyped(char character) {
		return false;
	}

	//@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		lastTouchX = screenX;
		lastTouchY = screenY;
		return false;
	}

	//@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	//@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	//@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	//@Override
	public boolean scrolled(int amount) {
		return false;
	}

	protected float getlastTouchX()
	{
		return lastTouchX;
	}

	protected float getlastTouchY()
	{
		return lastTouchY;
	}
}
//chestnut password is ballsDEEP