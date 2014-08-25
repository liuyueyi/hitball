package com.july.hitball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pools;

public class Bird extends Actor {
	GameScreen gameScreen;

	Animation anim;
	private TextureRegion draw;
	private float stateTime = 0;
	protected Body body;

	boolean startFall = false; // 当游戏状态为 isOvering时，开始执行小鸟下坠的动画

	public Bird(World world, GameScreen gameScreen) {
		this.gameScreen = gameScreen;

		anim = new Animation(0.2f,
				Asset.getInstance().birds[(int) (4 * Math.random())]);
		setPosition(100f, Constants.screenHeight / 2);
		setSize(Constants.birdWidth, Constants.birdHeight);
		draw = anim.getKeyFrame(0);

		// 创建一个小球
		// Dynamic Body 球
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(Constants.screenWidth * 0.4f / Constants.PPM,
				Constants.screenHeight / 2 / Constants.PPM);

		CircleShape dynamicCircle = new CircleShape(); // 创建多边形
		dynamicCircle.setRadius(Constants.birdWidth / 2 / Constants.PPM);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = dynamicCircle;
		fixtureDef.density = 0f; // 密度
		fixtureDef.friction = 0f; // 摩擦
		fixtureDef.restitution = 0.5f; // 弹性

		body = world.createBody(bodyDef); // 设置是半径
		body.createFixture(fixtureDef); // 暂停状态设置为失重
		body.setGravityScale(0f);
	}

	/**
	 * 点击一下屏幕时，给一个向上的速度
	 */
	public void fly() {
		body.setLinearVelocity(0, 6);
	}

	/**
	 * 顶小球
	 * 
	 * @param type
	 *            0 表示向上顶， 1 表示向下顶
	 */
	public void hitBall(int type) {
		if (type == 0) {
			body.setLinearVelocity(0, 12);
		} else {
			body.setLinearVelocity(0, -12);
		}
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if (Constants.status != Constants.isOver
				&& Constants.status != Constants.isOvering) {
			stateTime += Gdx.graphics.getDeltaTime();
			draw = anim.getKeyFrame(stateTime, true);
		}

		if (!startFall && Constants.status == Constants.isOvering) {
			// 开始执行结束动画
			// System.out.println("小鸟下降动画");
			startFall = true;
			draw = Asset.getInstance().deadBird;
			if (getY() > 10)
				body.setLinearVelocity(0, -10);
		}

		if (startFall && getY() < 9) { // 结束动画over
		// System.out.println("小鸟下降结束");
			draw = Asset.getInstance().deadBird;
			startFall = false;
			gameScreen.over(); // 显示结束
		}
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		setPosition(body.getPosition().x, body.getPosition().y);
		batch.draw(draw, getX(), getY(), getWidth(), getHeight());
		super.draw(batch, parentAlpha);
	}

	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x * Constants.PPM - Constants.birdWidth / 2, y
				* Constants.PPM - Constants.birdHeight / 2);
	}

	public Rectangle getRectangle() {
		Rectangle rec = Pools.obtain(Rectangle.class);
		rec.x = getX();
		rec.y = getY();
		rec.width = getWidth();
		rec.height = getHeight();
		return rec;
	}
}
