package com.july.hitball;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BackGround {
	private TextureRegion bg1;
	private float bg1x, bg1y, bg2x, bg2y, bg3x, bg3y, bg4x, bg4y;

	public BackGround() {
		bg1 = Asset.getInstance().bg[(int) (2 * Math.random())];

		bg1y = bg2y = bg3y = bg4y = 0;
		bg1x = 0;
		bg2x = bg1.getRegionWidth();
		bg3x = 2 * bg1.getRegionWidth();
		bg4x = 3 * bg1.getRegionWidth();
	}

	public void update() {
		bg1x -= Constants.bgVelocity;
		bg2x -= Constants.bgVelocity;
		bg3x -= Constants.bgVelocity;
		bg4x -= Constants.bgVelocity;

		if (bg1x <= -bg1.getRegionWidth()) {
			bg1x = bg4x + bg1.getRegionWidth();
		} else if (bg2x <= -bg1.getRegionWidth()) {
			bg2x = bg1x + bg1.getRegionWidth();
		} else if (bg3x <= -bg1.getRegionWidth()) {
			bg3x = bg2x + bg1.getRegionWidth();
		} else if(bg4x <= -bg1.getRegionWidth()){
			bg4x = bg3x + bg1.getRegionWidth();
		}

	}

	public void draw(SpriteBatch batch) {
		if (Constants.status != Constants.isOver) {
			update();
		}
		batch.draw(bg1, bg1x, bg1y, bg1.getRegionWidth(),
				Constants.screenHeight);
		batch.draw(bg1, bg2x, bg2y, bg1.getRegionWidth(),
				Constants.screenHeight);
		batch.draw(bg1, bg3x, bg3y, bg1.getRegionWidth(),
				Constants.screenHeight);
		batch.draw(bg1, bg4x, bg4y, bg1.getRegionWidth(),
				Constants.screenHeight);
	}
}
