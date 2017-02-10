package com.example.yinkoumeiqi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.utils.Utils;

public class DetailInfoActivity extends Activity {

	private RelativeLayout rela;
	private Button back;
	private WebView web;
	private ProgressBar bar;
	private TextView title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detailinfo);

		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		rela = (RelativeLayout) this.findViewById(R.id.rela1);
		back = (Button) this.findViewById(R.id.back);
		web = (WebView) this.findViewById(R.id.webview);
		bar = (ProgressBar) this.findViewById(R.id.progressBar1);
		title = (TextView) this.findViewById(R.id.title);

		WebSettings webSettings = web.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setSupportZoom(true);
		webSettings.setBuiltInZoomControls(true);
		webSettings.setDisplayZoomControls(false);
		;
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webSettings.setUseWideViewPort(true);
		webSettings.setAllowFileAccess(true);
		webSettings.setLoadWithOverviewMode(true);
		web.setWebViewClient(new WebViewClient());
		web.setInitialScale(120);

		Intent intent = getIntent();
		title.setText(intent.getStringExtra("title"));
		web.loadUrl(intent.getStringExtra("url"));

		web.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				bar.setProgress(newProgress);
				Log.i("newProgress", newProgress + "");
				if (newProgress == 100) {
					bar.setVisibility(View.GONE);
					// web.scrollTo(335, 570);
					web.scrollTo(335, 0);
				}
				if (newProgress == 0) {
					bar.setVisibility(View.VISIBLE);
					// web.scrollTo(335, 570);
					web.scrollTo(335, 0);
				}
			}
		});

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();

			}
		});

		rela.post(new Runnable() {
			// TODO 处理状态栏高度问题
			@Override
			public void run() {
				int pos = rela.getHeight();
				LinearLayout.LayoutParams layoutparams = (LinearLayout.LayoutParams) rela
						.getLayoutParams();
				layoutparams.height = pos
						+ Utils.getStatusBarHeight(DetailInfoActivity.this);
				rela.setLayoutParams(layoutparams);
			}
		});

	}

}
