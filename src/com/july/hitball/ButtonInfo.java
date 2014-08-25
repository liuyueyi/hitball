package com.july.hitball;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class ButtonInfo {
	Image upBtn;
	Image downBtn;

	Label scoreLabel;
	Label bestLabel;

	public ButtonInfo(Stage stage) {
		upBtn = new Image(Asset.getInstance().buttons[0]);
		upBtn.setBounds(Constants.upButtonX, Constants.upButtonY,
				Constants.buttonWidth, Constants.buttonHeight);

		downBtn = new Image(Asset.getInstance().buttons[1]);
		downBtn.setBounds(Constants.downButtonX, Constants.downButtonY,
				Constants.buttonWidth, Constants.buttonHeight);

		scoreLabel = new Label("" + Constants.score, Asset.getInstance().fontStyle);
		scoreLabel.setAlignment(Align.center);
		scoreLabel.setBounds(0, Constants.screenHeight * 0.8f, Constants.screenWidth, 60);
		
		bestLabel = new Label("best:" + Constants.bestScore, Asset.getInstance().numfontStyle);
		bestLabel.setAlignment(Align.left);
		bestLabel.setBounds(0, scoreLabel.getY() + 20, Constants.screenWidth, 32);

		stage.addActor(upBtn);
		stage.addActor(downBtn);
		stage.addActor(scoreLabel);
		stage.addActor(bestLabel);
	}
	
	
	/**
	 * 没顶掉一个球，加1分
	 */
	public void addScore(){
		Constants.score += 1;
		scoreLabel.setText("" + Constants.score);
	}
}
