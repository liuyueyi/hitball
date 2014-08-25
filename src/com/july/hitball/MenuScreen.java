package com.july.hitball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MenuScreen implements Screen {
	MainGame game;

	private SpriteBatch batch;
	private Stage stage;
	private BackGround background;
	private Animation bird;
	private float stateTime = 0;
	private TextureRegion title;
	private ImageButton startButton, moreButton, exitButton;

	public MenuScreen(final MainGame game) {
		this.game = game;

		Constants.status = Constants.isPausing;
		background = new BackGround();
		batch = new SpriteBatch();
		stage = new Stage(Constants.screenWidth, Constants.screenHeight, false);

		bird = new Animation(0.2f,
				Asset.getInstance().birds[(int) (4 * Math.random())]);
		title = Asset.getInstance().title;

		startButton = new ImageButton(new TextureRegionDrawable(
				Asset.getInstance().menuButton[0]), new TextureRegionDrawable(
				Asset.getInstance().menuButton[1]));
		startButton.setBounds(Constants.startButtonX, Constants.startButtonY,
				Constants.menuButtonWidth, Constants.menuButtonHeight);
		startButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Asset.getInstance().buttonMusic.play();
				game.setScreen(new GameScreen(game));
			}
		});
		stage.addActor(startButton);

		moreButton = new ImageButton(new TextureRegionDrawable(
				Asset.getInstance().menuButton[2]), new TextureRegionDrawable(
				Asset.getInstance().menuButton[3]));
		moreButton.setBounds(Constants.moreButtonX, Constants.moreButtonY,
				Constants.menuButtonWidth, Constants.menuButtonHeight);
		moreButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Asset.getInstance().buttonMusic.play();
				game.main.showAdStatic(Constants.TUIGUANG);
			}
		});
		stage.addActor(moreButton);

		exitButton = new ImageButton(new TextureRegionDrawable(
				Asset.getInstance().menuButton[4]), new TextureRegionDrawable(
				Asset.getInstance().menuButton[5]));
		exitButton.setBounds(Constants.exitButtonX, Constants.exitButtonY,
				Constants.menuButtonWidth, Constants.menuButtonHeight);
		exitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Asset.getInstance().buttonMusic.play();
				game.main.showAdStatic(Constants.CHAPIN);
				game.main.showAdStatic(Constants.EXIT);
			}
		});
		stage.addActor(exitButton);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(.4f, .4f, .4f, .4f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stateTime += Gdx.graphics.getDeltaTime();

		batch.begin();
		background.draw(batch);
		batch.draw(title, Constants.screenWidth / 2 - title.getRegionWidth() * Constants.wrate
				/ 2, Constants.screenHeight * 0.67f, title.getRegionWidth()
				* Constants.wrate, title.getRegionHeight() * Constants.hrate);
		batch.draw(bird.getKeyFrame(stateTime, true),
				Constants.screenWidth * 0.45f, Constants.screenHeight / 2,
				Constants.birdWidth, Constants.birdHeight);
		batch.end();

		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		batch.dispose();
		stage.dispose();
	}

}
