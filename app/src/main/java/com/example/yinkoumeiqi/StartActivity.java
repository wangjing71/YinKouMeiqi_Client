package com.example.yinkoumeiqi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class StartActivity extends Activity {

	private TextView skip;
	int count = 0; // 设置跳入主界面的时间
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (count < 3) {
				skip.setText("跳过：" + (3 - count));
				handler.sendEmptyMessageDelayed(1, 1000);
			} else {
				startActivity(new Intent(StartActivity.this, MainActivity.class));
				finish();
			}
			count++;
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.startactivity);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		skip = (TextView) findViewById(R.id.skip);
		skip.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(StartActivity.this, MainActivity.class));
				finish();
				handler.removeMessages(1);
			}
		});

		handler.sendEmptyMessage(1);
	}
}
