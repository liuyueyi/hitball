package com.july.hitball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Result {
	GameScreen gameScreen;

	Stage stage;
	Image bg;
	TextureRegion gameover;
	Label scoreLabel;
	ImageButton returnButton;
	ImageButton playButton;
	Image share;

	boolean isShowed = false;

	public Result(final GameScreen gameScreen) {
		this.gameScreen = gameScreen;

		gameover = Asset.getInstance().gameOver;

		stage = new Stage(Constants.screenWidth, Constants.screenHeight, false);
		bg = new Image(Asset.getInstance().resultDialog);
		bg.setBounds(Constants.resultX, Constants.resultY
				+ Constants.screenHeight, Constants.resultWidth,
				Constants.resultHeight);
		bg.addAction(Actions.moveTo(Constants.resultX, Constants.resultY,
				Gdx.graphics.getDeltaTime() * 20));
		stage.addActor(bg);

		scoreLabel = new Label("score:" + Constants.score + "\n best:"
				+ Constants.bestScore, Asset.getInstance().fontStyle);
		scoreLabel.setAlignment(Align.center);
		scoreLabel.setBounds(Constants.resultX, Constants.resultY + 15
				+ Constants.screenHeight - Constants.shareHeight,
				Constants.resultWidth, Constants.resultHeight);
		scoreLabel.addAction(Actions.moveTo(Constants.resultX,
				Constants.resultY + 15, Gdx.graphics.getDeltaTime() * 20));
		stage.addActor(scoreLabel);

		share = new Image(Asset.getInstance().share);
		share.setBounds(Constants.shareX, Constants.shareY
				+ Constants.screenHeight, Constants.shareWidth,
				Constants.shareHeight);
		share.addAction(Actions.moveTo(Constants.shareX, Constants.shareY,
				Gdx.graphics.getDeltaTime() * 20));
		share.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// ·ÖÏí
				Asset.getInstance().buttonMusic.play();
				gameScreen.game.main.showAdStatic(Constants.SHARE);
			}
		});
		stage.addActor(share);

		returnButton = new ImageButton(new TextureRegionDrawable(
				Asset.getInstance().returnButton[0]),
				new TextureRegionDrawable(Asset.getInstance().returnButton[1]));
		returnButton.setBounds(Constants.returnButtonX, Constants.returnButtonY
				+ Constants.screenHeight, Constants.menuButtonWidth,
				Constants.menuButtonHeight);
		returnButton.addAction(Actions.moveTo(Constants.returnButtonX,
				Constants.returnButtonY, Gdx.graphics.getDeltaTime() * 20));
		returnButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Asset.getInstance().buttonMusic.play();
				gameScreen.game.setScreen(new MenuScreen(gameScreen.game));
			}
		});
		stage.addActor(returnButton);

		playButton = new ImageButton(new TextureRegionDrawable(
				Asset.getInstance().playButton[0]), new TextureRegionDrawable(
				Asset.getInstance().playButton[1]));
		playButton.setBounds(Constants.playButtonX, Constants.playButtonY
				+ Constants.screenHeight, Constants.menuButtonWidth,
				Constants.menuButtonHeight);
		playButton.addAction(Actions.moveTo(Constants.playButtonX,
				Constants.playButtonY, Gdx.graphics.getDeltaTime() * 20));
		playButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Asset.getInstance().buttonMusic.play();
				gameScreen.game.setScreen(new GameScreen(gameScreen.game));
			}
		});
		stage.addActor(playButton);
	}

	public void show() {
		if (!isShowed) {
			isShowed = true;
			Gdx.input.setInputProcessor(stage);
			if (Constants.score > Constants.bestScore) {
				Constants.bestScore = Constants.score;
				Asset.getInstance().writeScore();
			}
			scoreLabel.setText("" + Constants.score);
			// System.out.println("show result!");
		}
	}

	public void draw(SpriteBatch batch) {
		if (Constants.status == Constants.isOver) {
			batch.begin();
			batch.draw(gameover,
					(Constants.screenWidth - gameover.getRegionWidth()) / 2,
					Constants.resultY + Constants.resultHeight);
			batch.end();

			stage.act();
			stage.draw();
		}
	}
}
