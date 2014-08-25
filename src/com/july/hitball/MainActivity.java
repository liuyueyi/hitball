package com.july.hitball;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import cn.domob.android.ads.DomobAdView;
import cn.waps.AppConnect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;

public class MainActivity extends AndroidApplication {
	static RelativeLayout layout;

	public static final String APP_ID = "9757ee6479e6dfd867b83a7604a375ed";
	public static final String APP_PID = "hipak";

	public static final String PUBLISHER_ID = "56OJwoVIuNCVQaT9xd";
	public static final String InlinePPID = "16TLuUEoApvGcNU0LHP5e72s";
	static DomobAdView MyAdview320x50;

	final static UMSocialService mController = UMServiceFactory
			.getUMSocialService("com.umeng.share");

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		// 设置窗口无标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 去除强制屏幕装饰（如状态条）弹出设置
		getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useGL20 = false;

		View gameView = initializeForView(new MainGame(this), cfg);
		layout = new RelativeLayout(this);
		MyAdview320x50 = new DomobAdView(this, PUBLISHER_ID, InlinePPID);
		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);

		layout.addView(gameView);
		layout.addView(MyAdview320x50, adParams);

		initAd();
		// initShare();
		setContentView(layout);

	}

	public void initAd() {
		AppConnect.getInstance(APP_ID, APP_PID, this);
		// AppConnect.getInstance(this).showBannerAd(this, layout);
		AppConnect.getInstance(this).initPopAd(this); // 预加载插屏广告
	}

	/**
	 * 共享
	 */
//	public void initShare() {
//		// 参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
//		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler((Activity) mContext,
//				"100424468", "c7394704798a158208a74ab60104f0ba");
//		qqSsoHandler.addToSocialSDK();
//
//		// 参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
//		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(
//				(Activity) mContext, "100424468",
//				"c7394704798a158208a74ab60104f0ba");
//		qZoneSsoHandler.addToSocialSDK();
//	}

	private static Handler handler = new Handler() {
		@Override
		public void handleMessage(final Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case Constants.CHAPIN:
				// 显示插屏广告
				// 判断插屏广告是否已初始化完成，用于确定是否能成功调用插屏广告
				AppConnect.getInstance(mContext).showPopAd(mContext);
				break;
			case Constants.TUIGUANG:
				AppConnect.getInstance(mContext).showOffers(mContext);
				break;

			case Constants.EXIT: // 退出
				// 创建退出对话框
				AlertDialog isExit = new AlertDialog.Builder(mContext).create();
				// 设置对话框标题
				isExit.setTitle("系统提示");
				// 设置对话框消息
				isExit.setMessage("确定要退出吗");
				// 添加选择按钮并注册监听
				isExit.setButton("确定", listener);
				isExit.setButton2("取消", listener);
				// 显示对话框
				isExit.show();
				break;
			case Constants.SHARE:
				// 设置分享内容
				mController.setShareContent("有胆你的小鸟顶几个球，爷的小鸟怒顶  "
						+ Constants.score + "球，小鸟等级lv:"
						+ (Constants.score / 10));
				mController.setShareMedia(new UMImage(mContext,
						R.drawable.ic_launcher));
				// 是否只有已登录用户才能打开分享选择页
				mController.openShare((Activity) mContext, false);
				break;
			}

		}
	};

	private static Context mContext;

	public void showAdStatic(int adTag) {
		Message msg = handler.obtainMessage();
		msg.what = adTag; // 私有静态的整型变量，开发者请自行定义值
		handler.sendMessage(msg);
	}

	/** 监听对话框里面的button点击事件 */
	static DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
				Gdx.app.exit();
				((Activity) mContext).finish();
				break;
			case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onDestroy() {
		AppConnect.getInstance(this).close();
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {

	}
}