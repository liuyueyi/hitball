package com.july.hitball;

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

public class Ball extends Actor {
	World world;
	TextureRegion ball;
	Body sbody; // 静止放在那得物体
	boolean tag; // 用于标记小球是否被顶掉

	public Ball(World world, float x, float y) {
		this.world = world;
		ball = Asset.getInstance().balls[(int) (5 * Math.random())];
		setSize(Constants.ballWidth, Constants.ballHeight);

		tag = false;

		BodyDef bodyDef = Pools.obtain(BodyDef.class);
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(x / Constants.PPM, y / Constants.PPM); // 设置初始坐标

		CircleShape dynamicCircle = new CircleShape(); // 创建circle
		dynamicCircle.setRadius(Constants.ballHeight / 2 / Constants.PPM); // 设置球的半径
		FixtureDef fixtureDef = Pools.obtain(FixtureDef.class);
		fixtureDef.shape = dynamicCircle;
		fixtureDef.density = 0f; // 密度
		fixtureDef.friction = 0.4f; // 摩擦
		fixtureDef.restitution = 0.5f; // 弹性

		sbody = world.createBody(bodyDef);
		sbody.createFixture(fixtureDef);

		sbody.setLinearVelocity(-Constants.bgVelocity, 0); // 设置速度
		sbody.setGravityScale(0f);// 失重，不要受重力影响
		dynamicCircle.dispose();
	}

	/**
	 * 判断该小球是否被撞到了
	 * 
	 * @param birdRectangle
	 *            : 小鸟的大小位置
	 * @return true 表示被撞倒了，false 表示没有撞倒
	 */
	public boolean hited(Rectangle birdRectangle) {
		if (Math.abs(birdRectangle.x - getX()) <= Constants.ballWidth + 10
				&& Math.abs(birdRectangle.y - getY()) <= Constants.ballHeight + 10) {
//			System.out.println("hit ball in ball.java");
			sbody.setGravityScale(1f); // 标准重力
			sbody.setLinearVelocity(-2, 3);

			tag = true;
			return true;
		}

		return false;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if (getX() < -Constants.ballWidth) { // 小球出了边界
			world.destroyBody(sbody);
			this.remove();
			if (!tag) {
				Constants.status = Constants.isOvering; // 当小球不是被顶出边界的时候，结束
				Asset.getInstance().dieMusic.play();
//				System.out.println("is overing...");
			}
		}

		if (tag && getY() < 20) {
			sbody.setLinearVelocity(-1, 0);
		}
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		setPosition(sbody.getPosition().x, sbody.getPosition().y);
		batch.draw(ball, getX(), getY(), Constants.ballWidth,
				Constants.ballHeight);
		super.draw(batch, parentAlpha);
	}

	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x * Constants.PPM - Constants.ballWidth / 2, y
				* Constants.PPM - Constants.ballHeight / 2);
	}
}
