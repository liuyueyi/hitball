package com.july.hitball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameScreen implements Screen, InputProcessor {
	MainGame game;

	BackGround bg; // 背景
	private SpriteBatch batch;
	private Stage stage;
	private ButtonInfo buttonInfo;
	private Bird bird;

	private BallFactory ballFactory;
	Result result;

	protected OrthographicCamera camera;
	protected Box2DDebugRenderer renderer; // 测试用绘制器
	World world; // 世界
	private final float PPM = 100; // 将像素转换为米

	public GameScreen(MainGame game) {
		this.game = game;
		Constants.score = 0;
		Constants.status = Constants.isPausing; // 暂停

		camera = new OrthographicCamera();
		// 将像素转换为米，false表示不颠倒相机
		camera.setToOrtho(false, Constants.screenWidth / PPM,
				Constants.screenHeight / PPM);
		world = new World(new Vector2(0, -9.81f), true); // 创建一个世界,
															// ture表示没有事件发生时休眠
		world.setContactListener(new ContactListener() {

			@Override
			public void beginContact(Contact contact) {
				// TODO Auto-generated method stub
				if (Constants.status == Constants.isRuning) {
					// 碰撞了
					bird.body.setLinearVelocity(0, -1);
					if (ballFactory.removeBall(bird.getRectangle())) {
						// 如果撞倒小球，则分数 +1
						buttonInfo.addScore();
						Asset.getInstance().pointMusic.play();
					} else if (bird.getY() > Constants.screenHeight
							|| bird.getY() < Constants.birdHeight / 3) {
						// System.out.println("bird hit other!");
						Asset.getInstance().dieMusic.play();
						Constants.status = Constants.isOvering; // 结束
					}
				}
			}

			@Override
			public void endContact(Contact contact) {
				// TODO Auto-generated method stub

			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				// TODO Auto-generated method stub

			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				// TODO Auto-generated method stub

			}

		});

		initBody();

		bg = new BackGround();

		batch = new SpriteBatch();
		stage = new Stage(Constants.screenWidth, Constants.screenHeight, false);
		buttonInfo = new ButtonInfo(stage);

		bird = new Bird(world, this);
		stage.addActor(bird);

		ballFactory.generateBall();
		stage.addActor(ballFactory.group);

		result = new Result(this);

		Gdx.input.setInputProcessor(this);
	}

	/**
	 * 创建地面和天空
	 */
	public void initBody() {
		renderer = new Box2DDebugRenderer();

		// 创建地面
		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.type = BodyDef.BodyType.StaticBody;
		groundBodyDef.position.set(Constants.screenWidth / 2 / PPM, 0); // 是坐标从起点到圆心的距离
		// 形状
		PolygonShape staticBox = new PolygonShape(); // 创建多边形
		staticBox.setAsBox(Constants.screenWidth / 2 / PPM, 0); // 设置半径
		// 生成
		Body bodyG = world.createBody(groundBodyDef);
		bodyG.createFixture(staticBox, 0); // 形状和密度

		// 创建天空
		BodyDef groundBodyDef2 = new BodyDef();
		groundBodyDef2.type = BodyDef.BodyType.StaticBody;
		groundBodyDef2.position.set(Constants.screenWidth / 2 / PPM,
				(Constants.screenHeight + 50) / PPM); // 是坐标从起点到圆心的距离
		// 形状
		PolygonShape staticBox2 = new PolygonShape(); // 创建多边形
		staticBox2.setAsBox(Constants.screenWidth / 2 / PPM, 0); // 设置半径
		// 生成
		Body bodyG2 = world.createBody(groundBodyDef2);
		bodyG2.createFixture(staticBox2, 0); // 形状和密度

		ballFactory = new BallFactory(world);
	}

	public void draw() {
		batch.begin();
		bg.draw(batch);
		batch.end();

		if (Constants.status == Constants.isRuning) {
			ballFactory.generateBall();
		}

		world.step(Gdx.app.getGraphics().getDeltaTime(), 6, 2);
		// world.step 时步，60帧每秒就设置为 1/60f，后面2个参数是速率迭代 位置迭代
		camera.update();
//		renderer.render(world, camera.combined);

		stage.act();
		stage.draw();

		result.draw(batch);

		if (bird.getX() < -bird.getWidth()) {
			over();
		}

		if (Constants.status == Constants.isPausing) {
			batch.begin();
			batch.draw(Asset.getInstance().tapfly, Constants.flytapX,
					Constants.flytapY, Constants.flytapWidth,
					Constants.flytapHeight);
			batch.draw(Asset.getInstance().arror, Constants.upArrorX,
					Constants.upArrorY, Constants.arrorWidth,
					Constants.arrorHeight);
			batch.draw(Asset.getInstance().arror, Constants.downArrorX,
					Constants.downArrorY, Constants.arrorWidth,
					Constants.arrorHeight);
			batch.end();
		}
	}

	public void over() {
		Constants.status = Constants.isOver;
		result.show();
		ballFactory.removeAll();
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(.4f, .4f, .4f, .4f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		draw();
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
		world.destroyBody(bird.body);
		world.dispose();
		batch.dispose();
		stage.dispose();
		renderer.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		Asset.getInstance().flyMusic.play();

		if (Constants.status == Constants.isPausing) { // 暂停时，tap屏幕开始
			Constants.status = Constants.isRuning;
			bird.body.setGravityScale(1.0f);
			bird.fly();
		} else if (Constants.status == Constants.isRuning) {
			screenY = (int) (Constants.screenHeight - screenY);
			if (Constants.upRectangle.contains(screenX, screenY)) {
				Asset.getInstance().buttonMusic.play();
				bird.hitBall(0);
			} else if (Constants.downRectangle.contains(screenX, screenY)) {
				Asset.getInstance().buttonMusic.play();
				bird.hitBall(1);
			} else {
				bird.fly();
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
