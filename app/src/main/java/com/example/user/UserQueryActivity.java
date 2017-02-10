package com.example.user;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.utils.JsonUtils;
import com.example.utils.Url;
import com.example.utils.Utils;
import com.example.view.MarqueeTextView;
import com.example.yinkoumeiqi.R;

public class UserQueryActivity extends Activity {

	// 用户信息查询

	private RelativeLayout rela;
	private Button search, back;
	private RequestQueue queue;
	private EditText editText;
	private int count;
	private ArrayList<String> list;
	private MarqueeTextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8;
	private SharedPreferences pref;

	public Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				handler.removeMessages(1);
				search.setText("正在查询" + Utils.GetPoint(count % 5));
				handler.sendEmptyMessageDelayed(1, 600);
				count++;
			} else if (msg.what == 2) {
				handler.removeMessages(1);
				handler.removeMessages(2);
				search.setText("查    询");
			}

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userquery_activity);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		
		pref = getSharedPreferences("version", Context.MODE_PRIVATE);
		rela = (RelativeLayout) findViewById(R.id.rela1);
		search = (Button) findViewById(R.id.search);
		editText = (EditText) findViewById(R.id.editText1);
		back = (Button) findViewById(R.id.back);
		list = new ArrayList<String>();
		tv1 = (MarqueeTextView) findViewById(R.id.textView1);
		tv2 = (MarqueeTextView) findViewById(R.id.textView2);
		tv3 = (MarqueeTextView) findViewById(R.id.textView3);
		tv4 = (MarqueeTextView) findViewById(R.id.textView4);
		tv5 = (MarqueeTextView) findViewById(R.id.textView5);
		tv6 = (MarqueeTextView) findViewById(R.id.textView6);
		tv7 = (MarqueeTextView) findViewById(R.id.textView7);
		tv8 = (MarqueeTextView) findViewById(R.id.textView8);
		queue = Volley.newRequestQueue(this);

		String ID = pref.getString("ID", "");
		editText.setText(ID);

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
						+ Utils.getStatusBarHeight(UserQueryActivity.this);
				rela.setLayoutParams(layoutparams);
			}
		});

		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				// TODO Auto-generated method stub

				count = 0;
				if (editText.length() == 0) {
					Toast.makeText(UserQueryActivity.this, "输入为空，无法查询！！！",
							Toast.LENGTH_LONG).show();
				} else {
					pref.edit().putString("ID", editText.getText().toString())
							.commit();
					handler.sendEmptyMessage(1);
					StringRequest request = new StringRequest(
							StringRequest.Method.GET, Url.GetUserInfoUrl
									+ editText.getText().toString(),
							new Listener<String>() {

								@Override
								public void onResponse(String s) {
									// TODO Auto-generated method stub

									// InputMethodManager imm =
									// (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
									// if (imm.isActive()) {
									// imm.hideSoftInputFromWindow(v.getWindowToken(),
									// 0);
									// }

									Utils.hideInputMethod((Activity) UserQueryActivity.this);

									Log.i("sssssssss", s);
									if (s.equals("false")) {
										Toast.makeText(UserQueryActivity.this,
												"帐号不存在！！！", Toast.LENGTH_LONG)
												.show();
										tv1.setText("");
										tv2.setText("");
										tv3.setText("");
										tv4.setText("");
										tv5.setText("");
										tv6.setText("");
										tv7.setText("");
										tv8.setText("");
									} else {
										list = new JsonUtils()
												.getUserInfoList(s);
										if (list.size() == 0) {
											Toast.makeText(
													UserQueryActivity.this,
													"未获取到任何数据，请检查连接。",
													Toast.LENGTH_LONG).show();
										} else {
											tv1.setText(list.get(0));
											tv2.setText(list.get(1));
											tv3.setText(list.get(2));
											tv4.setText(list.get(3));
											if (list.get(4).equals("1")) {
												tv5.setText("居民");
											} else if (list.get(4).equals("2")) {
												tv5.setText("工业");
											} else if (list.get(4).equals("3")) {
												tv5.setText("居民阶梯");
											} else if (list.get(4).equals("4")) {
												tv5.setText("A居民阶梯");
											} else if (list.get(4).equals("5")) {
												tv5.setText("非居民A");
											}
											if (list.get(5).equals("1")) {
												tv6.setText("站前");
											} else if (list.get(5).equals("2")) {
												tv6.setText("西市");
											} else if (list.get(5).equals("3")) {
												tv6.setText("站前（老）");
											} else if (list.get(5).equals("4")) {
												tv6.setText("西市（老）");
											}
											tv7.setText(list.get(6) + " 元");
											if (list.get(7).equals("0")) {
												tv8.setText("正常");
											} else if (list.get(7).equals("1")) {
												tv8.setText("报停");
											} else if (list.get(7).equals("2")) {
												tv8.setText("拆表");
											} else if (list.get(7).equals("3")) {
												tv8.setText("注销");
											} else if (list.get(7).equals("5")) {
												tv8.setText("迁户登记");
											} else if (list.get(7).equals("6")) {
												tv8.setText("注销登记");
											}
										}
									}
									handler.sendEmptyMessage(2);

								}
							}, new Response.ErrorListener() {
								@Override
								public void onErrorResponse(
										VolleyError volleyError) {
									Toast.makeText(UserQueryActivity.this,
											"连接服务器失败。", Toast.LENGTH_SHORT)
											.show();
									handler.sendEmptyMessage(2);
								}
							});

					request.setTag("get");
					queue.add(request);
				}
			}
		});

	}

}
