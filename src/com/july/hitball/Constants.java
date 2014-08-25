package com.july.hitball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class Constants {
	public static float screenWidth = Gdx.graphics.getWidth();
	public static float screenHeight = Gdx.graphics.getHeight();

	public static float wrate = screenWidth / 320f;
	public static float hrate = screenHeight / 480;

	public static final int isPausing = 0;
	public static final int isMenu = 1;
	public static final int isRuning = 2;
	public static final int isOver = 3;
	public static final int isOvering = 4;
	public static int status = isPausing;

	public static int bgVelocity = 4; // 背景图的移动速度
	public static int cloudVelocity = 3; // 云的移动速度

	// 小鸟的大小
	public static float birdWidth = 40f * hrate;
	public static float birdHeight = 40f * hrate;
	// ball size
	public static float ballWidth = 40f * hrate;
	public static float ballHeight = 40f * hrate;
	public static float ballX[] = { screenWidth, screenWidth * 1.3f };
	public static float ballY[] = { screenHeight * 0.2f, screenHeight * 0.8f };

	public static final float PPM = 100; // 像素和米之间的转换
	public static int score = 0; // 分数
	public static int bestScore = 0;

	// button
	public static float buttonWidth = 62 * screenWidth / 320f;
	public static float buttonHeight = 62 * screenHeight / 480f;
	public static float upButtonX = screenWidth - buttonWidth * 1.5f;
	public static float upButtonY = 30f;

	public static float downButtonX = buttonWidth / 2;
	public static float downButtonY = 30f;

	public static float arrorWidth = 56;
	public static float arrorHeight = 72;
	public static float upArrorX = upButtonX + (buttonWidth - arrorWidth) / 2;
	public static float upArrorY = upButtonY + buttonHeight + 3;
	public static float downArrorX = downButtonX + (buttonWidth - arrorWidth)
			/ 2;
	public static float downArrorY = upArrorY;

	public static Rectangle upRectangle = new Rectangle(upButtonX, upButtonY,
			buttonWidth, buttonHeight);
	public static Rectangle downRectangle = new Rectangle(downButtonX,
			downButtonY, buttonWidth, buttonHeight);

	// menu
	public static float resultWidth = screenWidth * 0.8f;
	public static float resultHeight = 230f / 382f * resultWidth;
	public static float resultX = (screenWidth - resultWidth) / 2;
	public static float resultY = (screenHeight - resultHeight) / 2;

	public static float shareWidth = resultWidth;
	public static float shareHeight = shareWidth * 0.2f;
	public static float shareX = Constants.resultX;
	public static float shareY = Constants.resultY + 15 * screenWidth / 320;

	public static float menuButtonWidth = 68f * screenWidth / 320f;
	public static float menuButtonHeight = 68f * screenHeight / 480f;
	public static float returnButtonX = resultX + resultWidth - menuButtonWidth
			- 20f;
	public static float returnButtonY = resultY - menuButtonHeight;

	public static float playButtonX = resultX + 20f;
	public static float playButtonY = returnButtonY;

	public static float startButtonX = (screenWidth - menuButtonWidth) / 2;
	public static float startButtonY = playButtonY + menuButtonHeight;
	public static float exitButtonX = playButtonX;
	public static float exitButtonY = playButtonY;
	public static float moreButtonX = returnButtonX;
	public static float moreButtonY = returnButtonY;

	public static float flytapWidth = 312;
	public static float flytapHeight = 244;
	public static float flytapX = (Constants.screenWidth - flytapWidth) / 2;
	public static float flytapY = (Constants.screenHeight - flytapHeight) / 2;

	
	public static final int CHAPIN = 0;
	public static final int TUIGUANG = 1;
	public static final int EXIT = 2;
	public static final int SHARE = 3;
}
