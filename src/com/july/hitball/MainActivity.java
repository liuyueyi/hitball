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
		// ���ô����ޱ�����
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ����ȫ��
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// ȥ��ǿ����Ļװ�Σ���״̬������������
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
		AppConnect.getInstance(this).initPopAd(this); // Ԥ���ز������
	}

	/**
	 * ����
	 */
//	public void initShare() {
//		// ����1Ϊ��ǰActivity������2Ϊ��������QQ���������APP ID������3Ϊ��������QQ���������APP kEY.
//		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler((Activity) mContext,
//				"100424468", "c7394704798a158208a74ab60104f0ba");
//		qqSsoHandler.addToSocialSDK();
//
//		// ����1Ϊ��ǰActivity������2Ϊ��������QQ���������APP ID������3Ϊ��������QQ���������APP kEY.
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
				// ��ʾ�������
				// �жϲ�������Ƿ��ѳ�ʼ����ɣ�����ȷ���Ƿ��ܳɹ����ò������
				AppConnect.getInstance(mContext).showPopAd(mContext);
				break;
			case Constants.TUIGUANG:
				AppConnect.getInstance(mContext).showOffers(mContext);
				break;

			case Constants.EXIT: // �˳�
				// �����˳��Ի���
				AlertDialog isExit = new AlertDialog.Builder(mContext).create();
				// ���öԻ������
				isExit.setTitle("ϵͳ��ʾ");
				// ���öԻ�����Ϣ
				isExit.setMessage("ȷ��Ҫ�˳���");
				// ���ѡ��ť��ע�����
				isExit.setButton("ȷ��", listener);
				isExit.setButton2("ȡ��", listener);
				// ��ʾ�Ի���
				isExit.show();
				break;
			case Constants.SHARE:
				// ���÷�������
				mController.setShareContent("�е����С�񶥼�����ү��С��ŭ��  "
						+ Constants.score + "��С��ȼ�lv:"
						+ (Constants.score / 10));
				mController.setShareMedia(new UMImage(mContext,
						R.drawable.ic_launcher));
				// �Ƿ�ֻ���ѵ�¼�û����ܴ򿪷���ѡ��ҳ
				mController.openShare((Activity) mContext, false);
				break;
			}

		}
	};

	private static Context mContext;

	public void showAdStatic(int adTag) {
		Message msg = handler.obtainMessage();
		msg.what = adTag; // ˽�о�̬�����ͱ����������������ж���ֵ
		handler.sendMessage(msg);
	}

	/** �����Ի��������button����¼� */
	static DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:// "ȷ��"��ť�˳�����
				Gdx.app.exit();
				((Activity) mContext).finish();
				break;
			case AlertDialog.BUTTON_NEGATIVE:// "ȡ��"�ڶ�����ťȡ���Ի���
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