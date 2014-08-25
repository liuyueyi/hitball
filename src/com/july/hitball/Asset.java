package com.july.hitball;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class Asset {
	private static Asset instance;

	public TextureAtlas atlas;
	public TextureRegion bg[];
	public TextureRegion birds[][];
	public TextureRegion deadBird;
	public TextureRegion balls[];

	public TextureRegion title;
	public TextureRegion gameOver;
	public TextureRegion resultDialog;
	public TextureRegion share;
	public TextureRegion playButton[];
	public TextureRegion returnButton[];
	public TextureRegion menuButton[];

	public TextureRegion arror; // ¼ýÍ·
	public TextureRegion hitButton[]; // ¶¥µÄ°´Å¥
	public TextureRegion cloud[];

	public TextureRegion tapContinue;
	public TextureRegion tapfly;

	public BitmapFont font;
	public LabelStyle fontStyle;
	public BitmapFont numfont;
	public LabelStyle numfontStyle;

	public TextureRegion buttons[];

	public Music dieMusic;
	public Music buttonMusic;
	public Music pointMusic;
	public Music flyMusic;

	public FileHandle file;

	private Asset() {

	}

	public static Asset getInstance() {
		if (instance == null) {
			instance = new Asset();
		}
		return instance;
	}

	public void loadTexture() {
		Texture ftr = new Texture(Gdx.files.internal("font/font.png"));
		ftr.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		font = new BitmapFont(Gdx.files.internal("font/font.fnt"),
				new TextureRegion(ftr), false);
		font.setScale(Constants.wrate, Constants.hrate);
		fontStyle = new LabelStyle(font, Color.WHITE);
		
		Texture ftr2 = new Texture(Gdx.files.internal("font/num.png"));
		ftr2.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		numfont = new BitmapFont(Gdx.files.internal("font/num.fnt"),
				new TextureRegion(ftr2), false);
		numfont.setScale(Constants.wrate, Constants.hrate);
		numfontStyle = new LabelStyle(numfont, Color.WHITE);

		atlas = new TextureAtlas(Gdx.files.internal("gfx/ball.pack"));
		bg = new TextureRegion[2];
		bg[0] = atlas.findRegion("bg");
		bg[1] = atlas.findRegion("bg2");

		birds = new TextureRegion[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				birds[i][j] = atlas.findRegion("bird" + i, j);
			}
		}
		deadBird = atlas.findRegion("bird_dead");

		balls = new TextureRegion[5];
		for (int i = 0; i < 5; i++) {
			balls[i] = atlas.findRegion("ball", i);
		}

		buttons = new TextureRegion[3];
		buttons[0] = atlas.findRegion("upBtn");
		buttons[1] = atlas.findRegion("downBtn");

		title = atlas.findRegion("title");
		gameOver = atlas.findRegion("gameover");
		resultDialog = atlas.findRegion("over");
		share = atlas.findRegion("share");

		returnButton = new TextureRegion[2];
		returnButton[0] = atlas.findRegion("return1");
		returnButton[1] = atlas.findRegion("return2");

		playButton = new TextureRegion[2];
		playButton[0] = atlas.findRegion("continue1");
		playButton[1] = atlas.findRegion("continue2");

		menuButton = new TextureRegion[6];
		menuButton[0] = atlas.findRegion("start1");
		menuButton[1] = atlas.findRegion("start2");
		menuButton[2] = atlas.findRegion("more1");
		menuButton[3] = atlas.findRegion("more2");
		menuButton[4] = atlas.findRegion("exit1");
		menuButton[5] = atlas.findRegion("exit2");

		tapfly = atlas.findRegion("tapfly");
		arror = atlas.findRegion("arror");

		file = Gdx.files.local("data/.score");
		if (file.exists()) {
			// System.out.println("here!");
			readScore();
		} else {
			writeScore();
			// System.out.println("bucunzai");
		}
	}

	public void loadMusic() {
		dieMusic = Gdx.audio.newMusic(Gdx.files.internal("mfx/die.mp3"));
		buttonMusic = Gdx.audio.newMusic(Gdx.files
				.internal("mfx/key_press.mp3"));
		flyMusic = Gdx.audio.newMusic(Gdx.files.internal("mfx/fly.mp3"));
		pointMusic = Gdx.audio.newMusic(Gdx.files.internal("mfx/point.mp3"));
	}

	public void readScore() {
		String score = file.readString();
		Constants.bestScore = Integer.parseInt(score);
	}

	public void writeScore() {
		file.writeString("" + Constants.bestScore, false); // ¸²¸ÇÐ´
	}

	public void dispose() {
		font.dispose();
		numfont.dispose();
		atlas.dispose();

		dieMusic.dispose();
		buttonMusic.dispose();
		flyMusic.dispose();
		pointMusic.dispose();
	}
}
