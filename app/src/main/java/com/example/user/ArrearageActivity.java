package com.example.user;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.utils.JsonUtils;
import com.example.utils.Url;
import com.example.utils.UseGas;
import com.example.utils.Utils;
import com.example.yinkoumeiqi.R;

//欠费查询

public class ArrearageActivity extends Activity {

	private RelativeLayout rela;
	private Button back, search;
	private int count, num;
	private RequestQueue queue;
	private EditText edt;
	private SharedPreferences pref;
	private TableLayout tableLayout;
	private TextView name, id, address, info;
	private ArrayList<UseGas> list;
	double total;
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

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_arrearage);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		pref = getSharedPreferences("version", Context.MODE_PRIVATE);
		rela = (RelativeLayout) this.findViewById(R.id.rela1);
		back = (Button) this.findViewById(R.id.back);
		search = (Button) this.findViewById(R.id.search);
		queue = Volley.newRequestQueue(this);
		edt = (EditText) findViewById(R.id.editText1);
		tableLayout = (TableLayout) findViewById(R.id.tablelayout);
		tableLayout.setStretchAllColumns(true);
		address = (TextView) findViewById(R.id.address);
		id = (TextView) findViewById(R.id.id);
		name = (TextView) findViewById(R.id.name);
		list = new ArrayList<UseGas>();
		info = (TextView) findViewById(R.id.info);
		String ID = pref.getString("ID", "");
		edt.setText(ID);
		search.setOnClickListener(new OnClickListener() {
			// TODO 查询按钮点击事件
			@Override
			public void onClick(final View v) {

				count = 0;
				if (edt.length() == 0) {
					Toast.makeText(ArrearageActivity.this, "输入为空，无法查询！！！",
							Toast.LENGTH_LONG).show();
				} else {
					pref.edit().putString("ID", edt.getText().toString())
							.commit();
					handler.sendEmptyMessage(1);
					StringRequest request = new StringRequest(
							StringRequest.Method.GET, Url.GetQianfei
									+ edt.getText().toString(),
							new Listener<String>() {

								@Override
								public void onResponse(String s) {

									Log.i("sssssssss", s);

									Utils.hideInputMethod((Activity) ArrearageActivity.this);

									if (s.equals("false")) {
										Toast.makeText(ArrearageActivity.this,
												"帐号错误或者不存在！！！",
												Toast.LENGTH_LONG).show();

									} else {
										num = 1;
										// 动态删除已经显示的数据的视图
										int j = tableLayout.getChildCount();
										if (j > 1) {
											tableLayout.removeView(tableLayout
													.getChildAt(j - 1));
										}
										for (int i = j; i > 0; i--) {
											tableLayout.removeView(tableLayout
													.getChildAt(i));
										}

										list.clear();
										total = 0;
										list = new JsonUtils().getTarrearage(s);
										if (list.size() == 0) {
											id.setText(edt.getText().toString());
											String[] inf = s.split(",");
											if(inf.length == 1){
												Toast.makeText(ArrearageActivity.this, "未获取到任何数据", Toast.LENGTH_LONG).show();
											}else{
											name.setText(inf[0]);
											address.setText(inf[1]);
											info.setText("当前您没有欠费");
											}
										} else {
											name.setText(list.get(0)
													.getUserName());
											id.setText(list.get(0).getUserID());
											address.setText(list.get(0)
													.getAddress());

											for (int i = 0; i < list.size(); i++) {
												if (list.get(i).getPayyesno()
														.equals("0")) {
													total = total
															+ Double.parseDouble(list
																	.get(i)
																	.getTotal());
													TableRow tableRow = new TableRow(
															ArrearageActivity.this);
													TextView tv1 = new TextView(
															ArrearageActivity.this);
													TextView tv2 = new TextView(
															ArrearageActivity.this);
													TextView tv3 = new TextView(
															ArrearageActivity.this);
													TextView tv4 = new TextView(
															ArrearageActivity.this);
													TextView tv5 = new TextView(
															ArrearageActivity.this);
													TextView tv6 = new TextView(
															ArrearageActivity.this);
													TextView tv7 = new TextView(
															ArrearageActivity.this);
													TextView tv8 = new TextView(
															ArrearageActivity.this);

													tv1.setText(num + "");
													num++;
													if (list.get(i).getCbData()
															.length() != 0) {

														tv2.setText(list
																.get(i)
																.getCbData()
																.subSequence(0,
																		10));
													}
													tv3.setText(list.get(i)
															.getBCGasNum());
													tv4.setText(list.get(i)
															.getTotal());
													tv5.setText(list.get(i)
															.getNowChange());
													tv6.setText(Utils
															.getPayyesno(list
																	.get(i)
																	.getPayyesno()));
													if (list.get(i)
															.getPaydate()
															.length() != 0) {

														tv7.setText(list
																.get(i)
																.getPaydate()
																.subSequence(0,
																		10));
													}
													tv8.setText(Utils
															.getType(list
																	.get(i)
																	.getWatertype()));

													tv1.setGravity(Gravity.CENTER);
													tv2.setGravity(Gravity.CENTER);
													tv3.setGravity(Gravity.CENTER);
													tv4.setGravity(Gravity.CENTER);
													tv5.setGravity(Gravity.CENTER);
													tv6.setGravity(Gravity.CENTER);
													tv7.setGravity(Gravity.CENTER);
													tv8.setGravity(Gravity.CENTER);

													tv1.setTextColor(Color
															.parseColor("#000000"));
													tv2.setTextColor(Color
															.parseColor("#000000"));
													tv3.setTextColor(Color
															.parseColor("#000000"));
													tv4.setTextColor(Color
															.parseColor("#000000"));
													tv5.setTextColor(Color
															.parseColor("#000000"));
													tv6.setTextColor(Color
															.parseColor("#000000"));
													tv7.setTextColor(Color
															.parseColor("#000000"));
													tv8.setTextColor(Color
															.parseColor("#000000"));
													tv1.setTextSize(15);
													tv2.setTextSize(15);
													tv3.setTextSize(15);
													tv4.setTextSize(15);
													tv5.setTextSize(15);
													tv6.setTextSize(15);
													tv7.setTextSize(15);
													tv8.setTextSize(15);
													tv1.setBackgroundColor(Color
															.parseColor("#ffffff"));
													tv2.setBackgroundColor(Color
															.parseColor("#ffffff"));
													tv3.setBackgroundColor(Color
															.parseColor("#ffffff"));
													tv4.setBackgroundColor(Color
															.parseColor("#ffffff"));
													tv5.setBackgroundColor(Color
															.parseColor("#ffffff"));
													tv6.setBackgroundColor(Color
															.parseColor("#ffffff"));
													tv7.setBackgroundColor(Color
															.parseColor("#ffffff"));
													tv8.setBackgroundColor(Color
															.parseColor("#ffffff"));
													tableRow.addView(tv1);
													tableRow.addView(tv2);
													tableRow.addView(tv3);
													tableRow.addView(tv4);
													tableRow.addView(tv5);
													tableRow.addView(tv6);
													tableRow.addView(tv7);
													tableRow.addView(tv8);
													TableLayout.LayoutParams parms1 = new TableLayout.LayoutParams(
															20, 20);
													parms1.setMargins(0, 0, 0,
															1);
													tableLayout.addView(
															tableRow, parms1);
												}
											}

											if (total == 0) {
												info.setText("当前您没有欠费");
											} else {
												info.setText("总计欠费：" + total+" 元");
											}
											info.setBackgroundColor(Color
													.parseColor("#5AA5F0"));
										}
									}
									handler.sendEmptyMessage(2);
								}
							}, new Response.ErrorListener() {
								@Override
								public void onErrorResponse(
										VolleyError volleyError) {
									Toast.makeText(ArrearageActivity.this,
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

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		rela.post(new Runnable() {
			// TODO 处理状态栏高度问题
			@Override
			public void run() {
				int pos = rela.getHeight();
				RelativeLayout.LayoutParams layoutparams = (RelativeLayout.LayoutParams) rela
						.getLayoutParams();
				layoutparams.height = pos
						+ Utils.getStatusBarHeight(ArrearageActivity.this);
				rela.setLayoutParams(layoutparams);
			}
		});
	}

	private DatePicker findDatePicker(ViewGroup group) {
		if (group != null) {
			for (int i = 0, j = group.getChildCount(); i < j; i++) {
				View child = group.getChildAt(i);
				if (child instanceof DatePicker) {
					return (DatePicker) child;
				} else if (child instanceof ViewGroup) {
					DatePicker result = findDatePicker((ViewGroup) child);
					if (result != null)
						return result;
				}
			}
		}
		return null;
	}
}
