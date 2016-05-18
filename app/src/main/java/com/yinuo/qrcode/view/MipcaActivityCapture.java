package com.yinuo.qrcode.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.yinuo.R;
import com.yinuo.qrcode.camera.CameraManager;
import com.yinuo.qrcode.decoding.CaptureActivityHandler;
import com.yinuo.qrcode.decoding.InactivityTimer;
import com.yinuo.utils.ResUtils;


import java.io.IOException;
import java.util.Vector;

/**
 * Initial the camera
 * @author Ryan.Tang
 */
@TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
public class MipcaActivityCapture extends Activity implements Callback {

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	private int sign;
	private Camera camera;
	private ImageButton btn_light;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capture);
		CameraManager.init(getApplication());

		CameraManager.get().closeDriver();

		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		btn_light = (ImageButton) findViewById(R.id.btn_light);
		btn_light.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(sign % 2 == 0){
					CameraManager.get().openF();
				}
				else {
					CameraManager.get().stopF();
				}
				sign++;
			}
		});
//		Button mButtonBack = (Button) findViewById(R.id.button_back);
//		mButtonBack.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				MipcaActivityCapture.this.finish();
//
//			}
//		});
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);

		readNFCTag(getIntent());

	}
	/**
	 * @param view 标题栏左侧按钮点击事件
	 */
	public void goBack(View view) {
		onBackPressed();
	}


	protected void onNewIntent(Intent intent) {
		//读取NFC标签内容
		readNFCTag(intent);
	}

	@Override
	protected void onResume() {
		super.onResume();

		String url = getIntent().getStringExtra("uriRecord");
		if(url != null && !"".equals(url)){
//			BXTNetClientSender.getInstatnce().qrScan(this, url, BXTTabMainActivity.NFC_TYPE, qrScanResult);
		}

		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
		
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}
	
	/**
	 *
	 * @param result
	 * @param barcode
	 */
	public void handleDecode(Result result, Bitmap barcode) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		String resultString = result.getText();
		if (resultString.equals("")) {
			Toast.makeText(MipcaActivityCapture.this, "不是正确的二维码", Toast.LENGTH_SHORT).show();
		}else {
//			this.setResult(RESULT_OK, resultIntent);
//			BXTNetClientSender.getInstatnce().qrScan(this,resultString, BXTTabMainActivity.QRCODE_TYPE,qrScanResult);
		}
//		MipcaActivityCapture.this.finish();
	}
	
	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(ResUtils.getString(this, R.string.qrcode_permission))
					.setNegativeButton(R.string.app_confirm, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					finish();
				}
			}).show();
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

//	BXTNetListener qrScanResult = new BXTNetListener() {
//		@Override
//		public void onComplete(String jason) {
//			LogUtils.d("qrcode", "the result string is " + jason);
//			if (TextUtils.isEmpty(jason)) {
//				return;
//			}
//			Gson gson = new Gson();
//			QrScanEntity entity = gson.fromJson(jason,QrScanEntity.class);
//			if("0".equals(entity.getReturncode()) && entity.getData() != null
//					&& entity.getData().size() > 0){
//				deviceId = entity.getData().get(0).getQr_content();
//				if(entity.getData().get(0).getQr_more() != null &&
//						entity.getData().get(0).getQr_more().size() > 0){
//					deviceName = entity.getData().get(0).getQr_more().get(0).getName() + "\n"
//										+ entity.getData().get(0).getQr_more().get(0).getCode_number();
//					placeId = entity.getData().get(0).getQr_more().get(0).getPlace_id();
//					placeName = entity.getData().get(0).getQr_more().get(0).getPlace_name();
//
//				}
//				new Handler().postDelayed(new Runnable(){
//
//					public void run() {
//						new PopubWindows(MipcaActivityCapture.this, fl_scan);
//					}
//
//				}, 100L);
//
//
////				String device_id = entity.getData().get(0).getQr_content();
////
////				if(getIntent().getStringArrayListExtra("devices") == null){
////					Intent resultIntent = new Intent(MipcaActivityCapture.this, DeviceDetailActivity.class);
////					Bundle bundle = new Bundle();
////					bundle.putString("result", device_id);
////					//			bundle.putParcelable("bitmap", barcode);
////					resultIntent.putExtras(bundle);
////					startActivity(resultIntent);
////					MipcaActivityCapture.this.finish();
////				}else{
////					if(getIntent().getStringArrayListExtra("devices").size() > 0
////							&& getIntent().getStringArrayListExtra("devices").contains(device_id)){
////						Intent resultIntent = new Intent(MipcaActivityCapture.this, DeviceDetailActivity.class);
////						Bundle bundle = new Bundle();
////						bundle.putString("result", device_id);
////						//			bundle.putParcelable("bitmap", barcode);
////						resultIntent.putExtras(bundle);
////						startActivity(resultIntent);
////						MipcaActivityCapture.this.finish();
////					}else{
////						Toast.makeText(MipcaActivityCapture.this,getString(R.string.device_not_exist),Toast.LENGTH_SHORT).show();
////						MipcaActivityCapture.this.finish();
////					}
////				}
//			}
//		}
//
//		@Override
//		public void onError(NetResultEntity result) {
//
//		}
//
//		@Override
//		public void onFailure(String failure) {
//
//		}
//
//		@Override
//		public void onException() {
//
//		}
//	};

//	public class PopubWindows extends PopupWindow
//	{
//
//		private LinearLayout ll_device_detail,ll_device_repair;
//		private TextView tv_cancel_scan;
//
//		public PopubWindows(Context mContext,View parent)
//		{
//
//			View view = View.inflate(mContext, R.layout.scan_code_pupwindow, null);
//			view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_ins));
//
//			setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//			setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//			setFocusable(true);
//			setOutsideTouchable(true);
//			setContentView(view);
//			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
//			update();
//
//			tv_cancel_scan = (TextView) view.findViewById(R.id.tv_cancel_scan);
//			ll_device_detail = (LinearLayout) view.findViewById(R.id.ll_device_detail);
//			ll_device_repair = (LinearLayout) view.findViewById(R.id.ll_device_repair);
//			//跳转维保详情
//			ll_device_detail.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View view) {
//					Intent repair = new Intent(MipcaActivityCapture.this, DeviceDetailActivity.class);
//					repair.putExtra("result",deviceId);
//					startActivity(repair);
//					dismiss();
//					MipcaActivityCapture.this.finish();
//				}
//			});
//			//跳转报修页面
//			ll_device_repair.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View view) {
//
//					Intent repair = new Intent(MipcaActivityCapture.this, AddRepairNewOrderActivity.class);
//					repair.putExtra("placeId",placeId);
//					repair.putExtra("placeName",placeName);
//					repair.putExtra("deviceId",deviceId);
//					repair.putExtra("deviceName",deviceName);
//					startActivity(repair);
//					dismiss();
//					MipcaActivityCapture.this.finish();
//				}
//			});
//			tv_cancel_scan.setOnClickListener(new OnClickListener()
//			{
//				public void onClick(View v)
//				{
//					dismiss();
//					MipcaActivityCapture.this.finish();
//				}
//			});
//		}
//	}

	//读取NFC信息
	public void readNFCTag(Intent intent)
	{

		if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction()))
		{
			Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

			NdefMessage ndefMessage = null;

			int contentSize = 0;
			if(rawMsgs != null)
			{
				if(rawMsgs.length > 0)
				{
					ndefMessage = (NdefMessage)rawMsgs[0];
					contentSize = ndefMessage.toByteArray().length;
				}
				else
				{
					return;
				}
			}
			try
			{
				NdefRecord ndefRecord = ndefMessage.getRecords()[0];
//				UriRecord uriRecord = UriRecord.parse(ndefRecord);
//                Toast.makeText(BXTTabMainActivity.this, uriRecord.getUri().toString() + "\n\nUri\n" + contentSize + " bytes", Toast.LENGTH_SHORT).show();
//				BXTNetClientSender.getInstatnce().qrScan(this,uriRecord.getUri().toString(), BXTTabMainActivity.NFC_TYPE, qrScanResult);

			}
			catch (Exception e) {
			}
		}
	}


}