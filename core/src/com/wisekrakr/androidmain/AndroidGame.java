package com.wisekrakr.androidmain;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.wisekrakr.androidmain.screens.EndScreen;
import com.wisekrakr.androidmain.screens.LevelCompleteScreen;
import com.wisekrakr.androidmain.screens.LoadingScreen;
import com.wisekrakr.androidmain.screens.MenuScreen;
import com.wisekrakr.androidmain.screens.PlayScreen;
import com.wisekrakr.androidmain.screens.PreferencesScreen;

public class AndroidGame extends Game {

	private PooledEngine engine;

	private MyAssetManager myAssetManager;
	private GamePreferences gamePreferences;

	private PreferencesScreen preferencesScreen;
	private MenuScreen menuScreen;
	private LevelCompleteScreen levelCompleteScreen;
	private PlayScreen playScreen;
	private EndScreen endScreen;

	public final static int MENU = 0;
	public final static int PREFERENCES = 1;
	public final static int LEVELSELECTION = 2;
	public final static int APPLICATION = 3;
	public final static int ENDGAME = 4;

	private GameThread gameThread;


	@Override
	public void create() {
		start();
	}

	private void start(){
		gamePreferences = new GamePreferences();

		myAssetManager = new MyAssetManager();

		engine = new PooledEngine();

		setScreen(new LoadingScreen(this));

		gameThread = new GameThread(this);
	}

	public void changeScreen(int screen){
		switch(screen){
			case MENU:
				if(menuScreen == null) menuScreen = new MenuScreen(this);
				this.setScreen(menuScreen);
				break;
			case PREFERENCES:
				if(preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
				this.setScreen(preferencesScreen);
				break;
			case LEVELSELECTION:
				if (levelCompleteScreen == null) levelCompleteScreen = new LevelCompleteScreen(this);
				this.setScreen(levelCompleteScreen);
				break;
			case APPLICATION:
				if(playScreen == null) playScreen = new PlayScreen(this);
				this.setScreen(new PlayScreen(this));
				break;
			case ENDGAME:
				if(endScreen == null) endScreen = new EndScreen(this);
				this.setScreen(endScreen);
				break;
		}
	}



	public GamePreferences getGamePreferences() {
		return gamePreferences;
	}

	public MyAssetManager assetManager() {
		return myAssetManager;
	}

	public PooledEngine getEngine() {
		return engine;
	}

	public GameThread getGameThread() {
		return gameThread;
	}
}
