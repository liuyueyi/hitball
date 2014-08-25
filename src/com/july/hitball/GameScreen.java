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

	BackGround bg; // ����
	private SpriteBatch batch;
	private Stage stage;
	private ButtonInfo buttonInfo;
	private Bird bird;

	private BallFactory ballFactory;
	Result result;

	protected OrthographicCamera camera;
	protected Box2DDebugRenderer renderer; // �����û�����
	World world; // ����
	private final float PPM = 100; // ������ת��Ϊ��

	public GameScreen(MainGame game) {
		this.game = game;
		Constants.score = 0;
		Constants.status = Constants.isPausing; // ��ͣ

		camera = new OrthographicCamera();
		// ������ת��Ϊ�ף�false��ʾ���ߵ����
		camera.setToOrtho(false, Constants.screenWidth / PPM,
				Constants.screenHeight / PPM);
		world = new World(new Vector2(0, -9.81f), true); // ����һ������,
															// ture��ʾû���¼�����ʱ����
		world.setContactListener(new ContactListener() {

			@Override
			public void beginContact(Contact contact) {
				// TODO Auto-generated method stub
				if (Constants.status == Constants.isRuning) {
					// ��ײ��
					bird.body.setLinearVelocity(0, -1);
					if (ballFactory.removeBall(bird.getRectangle())) {
						// ���ײ��С������� +1
						buttonInfo.addScore();
						Asset.getInstance().pointMusic.play();
					} else if (bird.getY() > Constants.screenHeight
							|| bird.getY() < Constants.birdHeight / 3) {
						// System.out.println("bird hit other!");
						Asset.getInstance().dieMusic.play();
						Constants.status = Constants.isOvering; // ����
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
	 * ������������
	 */
	public void initBody() {
		renderer = new Box2DDebugRenderer();

		// ��������
		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.type = BodyDef.BodyType.StaticBody;
		groundBodyDef.position.set(Constants.screenWidth / 2 / PPM, 0); // ���������㵽Բ�ĵľ���
		// ��״
		PolygonShape staticBox = new PolygonShape(); // ���������
		staticBox.setAsBox(Constants.screenWidth / 2 / PPM, 0); // ���ð뾶
		// ����
		Body bodyG = world.createBody(groundBodyDef);
		bodyG.createFixture(staticBox, 0); // ��״���ܶ�

		// �������
		BodyDef groundBodyDef2 = new BodyDef();
		groundBodyDef2.type = BodyDef.BodyType.StaticBody;
		groundBodyDef2.position.set(Constants.screenWidth / 2 / PPM,
				(Constants.screenHeight + 50) / PPM); // ���������㵽Բ�ĵľ���
		// ��״
		PolygonShape staticBox2 = new PolygonShape(); // ���������
		staticBox2.setAsBox(Constants.screenWidth / 2 / PPM, 0); // ���ð뾶
		// ����
		Body bodyG2 = world.createBody(groundBodyDef2);
		bodyG2.createFixture(staticBox2, 0); // ��״���ܶ�

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
		// world.step ʱ����60֡ÿ�������Ϊ 1/60f������2�����������ʵ��� λ�õ���
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

		if (Constants.status == Constants.isPausing) { // ��ͣʱ��tap��Ļ��ʼ
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
