package com.july.hitball;

import com.badlogic.gdx.Game;

public class MainGame extends Game {
	MenuScreen menuScreen;
	MainActivity main;
	
	public MainGame(MainActivity main){
		this.main = main;
	}
	
	
	@Override
	public void create() {
		Asset.getInstance().loadTexture();
		Asset.getInstance().loadMusic();

		menuScreen = new MenuScreen(this);
		setScreen(menuScreen);
	}

	
	@Override
	public void dispose() {
		try {
			System.out.println("dispose all");
			Asset.getInstance().dispose();
			menuScreen.dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
