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
	Body sbody; // ��ֹ�����ǵ�����
	boolean tag; // ���ڱ��С���Ƿ񱻶���

	public Ball(World world, float x, float y) {
		this.world = world;
		ball = Asset.getInstance().balls[(int) (5 * Math.random())];
		setSize(Constants.ballWidth, Constants.ballHeight);

		tag = false;

		BodyDef bodyDef = Pools.obtain(BodyDef.class);
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(x / Constants.PPM, y / Constants.PPM); // ���ó�ʼ����

		CircleShape dynamicCircle = new CircleShape(); // ����circle
		dynamicCircle.setRadius(Constants.ballHeight / 2 / Constants.PPM); // ������İ뾶
		FixtureDef fixtureDef = Pools.obtain(FixtureDef.class);
		fixtureDef.shape = dynamicCircle;
		fixtureDef.density = 0f; // �ܶ�
		fixtureDef.friction = 0.4f; // Ħ��
		fixtureDef.restitution = 0.5f; // ����

		sbody = world.createBody(bodyDef);
		sbody.createFixture(fixtureDef);

		sbody.setLinearVelocity(-Constants.bgVelocity, 0); // �����ٶ�
		sbody.setGravityScale(0f);// ʧ�أ���Ҫ������Ӱ��
		dynamicCircle.dispose();
	}

	/**
	 * �жϸ�С���Ƿ�ײ����
	 * 
	 * @param birdRectangle
	 *            : С��Ĵ�Сλ��
	 * @return true ��ʾ��ײ���ˣ�false ��ʾû��ײ��
	 */
	public boolean hited(Rectangle birdRectangle) {
		if (Math.abs(birdRectangle.x - getX()) <= Constants.ballWidth + 10
				&& Math.abs(birdRectangle.y - getY()) <= Constants.ballHeight + 10) {
//			System.out.println("hit ball in ball.java");
			sbody.setGravityScale(1f); // ��׼����
			sbody.setLinearVelocity(-2, 3);

			tag = true;
			return true;
		}

		return false;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if (getX() < -Constants.ballWidth) { // С����˱߽�
			world.destroyBody(sbody);
			this.remove();
			if (!tag) {
				Constants.status = Constants.isOvering; // ��С���Ǳ������߽��ʱ�򣬽���
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
