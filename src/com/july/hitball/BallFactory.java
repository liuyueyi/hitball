package com.july.hitball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

public class BallFactory {
	World world;
	Group group;
	Array<Ball> balls;

	public static float time = 0;

	public BallFactory(World world) {
		this.world = world;
		group = new Group();
		balls = new Array<Ball>();
	}

	public void generateBall() {
		time += Gdx.graphics.getDeltaTime();
		if (time > 3) {
			float x = Constants.ballX[0]
					+ (Constants.ballX[1] - Constants.ballX[0])
					* (float) Math.random();
			float y = Constants.ballY[0]
					+ (Constants.ballY[1] - Constants.ballY[0])
					* (float) Math.random();
			Ball ball = new Ball(world, x, y);
			balls.add(ball);
			group.addActor(ball);
			time = 0;
		}
	}

	/**
	 * 判断小鸟是否撞倒小球
	 * 
	 * @param birdRectangle
	 * @return true是； false 否， 若结果为false，则表示小鸟撞倒了天空或者地板，结束
	 */
	public boolean removeBall(Rectangle birdRectangle) {
		for (int i = balls.size - 1; i >= 0; i--) {
			if (balls.get(i).hited(birdRectangle)) {
				balls.removeIndex(i);
				return true;
			}
		}
		return false;
	}
	
	public void removeAll(){
		group.remove();
	}

}
