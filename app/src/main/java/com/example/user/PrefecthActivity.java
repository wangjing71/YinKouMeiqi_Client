package com.example.user;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adapter.MyListViewAdapter;
import com.example.utils.JsonUtils;
import com.example.utils.PrefecthInfo;
import com.example.utils.Url;
import com.example.utils.Utils;
import com.example.view.MyDatePickerDialog;
import com.example.yinkoumeiqi.R;

public class PrefecthActivity extends Activity {

	// 欠费信息查询
	private RelativeLayout rela;
	private Button back, search;
	private int count;
	private RequestQueue queue;
	private EditText edt;
	private TextView start, end;
	private ArrayList<PrefecthInfo> list;
	private SharedPreferences pref;
	private TextView name, id, address, textviewtotal;
	private ListView listView;
	private MyListViewAdapter adapter;
	private double total;
	private MyDatePickerDialog dialog1, dialog2;
	private int year, monthofyear, dayofmonth;
	private String startyear, startmonth, endyear, endmonth;

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
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_prefecthinfo);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		
		pref = getSharedPreferences("version", Context.MODE_PRIVATE);
		rela = (RelativeLayout) findViewById(R.id.rela1);
		back = (Button) findViewById(R.id.back);
		search = (Button) findViewById(R.id.search);
		Calendar calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		monthofyear = calendar.get(Calendar.MONTH);
		dayofmonth = calendar.get(Calendar.DAY_OF_MONTH);
		queue = Volley.newRequestQueue(this);
		edt = (EditText) findViewById(R.id.editText1);
		name = (TextView) findViewById(R.id.name);
		id = (TextView) findViewById(R.id.id);
		start = (TextView) this.findViewById(R.id.start);
		end = (TextView) this.findViewById(R.id.end);
		address = (TextView) findViewById(R.id.address);
		list = new ArrayList<PrefecthInfo>();
		listView = (ListView) findViewById(R.id.listView1);
		textviewtotal = (TextView) findViewById(R.id.textView5);
		startyear = (year - 1) + "";
		endyear = year + "";
		startmonth = monthofyear + "";
		endmonth = monthofyear + "";
		start.setText(startyear + "-" + (Integer.parseInt(startmonth) + 1));
		end.setText(endyear + "-" + (Integer.parseInt(startmonth) + 1));

		String ID = pref.getString("ID", "");
		edt.setText(ID);
		adapter = new MyListViewAdapter(this);

		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				String url = Url.GetPrefecthInfoUrl + edt.getText().toString();
				Log.i("============url", url);
				count = 0;
				if (edt.length() == 0) {
					Toast.makeText(PrefecthActivity.this, "输入为空，无法查询！！！",
							Toast.LENGTH_LONG).show();
				} else {
					pref.edit().putString("ID", edt.getText().toString())
							.commit();
					handler.sendEmptyMessage(1);
					StringRequest request = new StringRequest(
							StringRequest.Method.GET, url,
							new Listener<String>() {

								@Override
								public void onResponse(String s) {
									total = 0;
									// TODO Auto-generated method stub
									Log.i("sssssssss", s);

									Utils.hideInputMethod((Activity) PrefecthActivity.this);

									// InputMethodManager imm =
									// (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
									// if (imm.isActive()) {
									// imm.hideSoftInputFromWindow(v.getWindowToken(),
									// 0);
									// }

									if (s.equals("false")) {
										list.clear();
										adapter.notifyDataSetChanged();
										Toast.makeText(PrefecthActivity.this,
												"帐号错误不存在！！！", Toast.LENGTH_LONG)
												.show();
										textviewtotal.setText(total + "");

									} else {
										textviewtotal.setText(total + "");
										list.clear();
										list = new JsonUtils()
												.getPrefecthInfo(s);

										// 处理服务器未开启的状态
										if (list.size() == 0) {
											Toast.makeText(
													PrefecthActivity.this,
													"未获取到任何数据，请检查连接。",
													Toast.LENGTH_LONG).show();
										} else {
											name.setText(list.get(0)
													.getUserName());
											id.setText(list.get(0).getUserID());
											address.setText(list.get(0)
													.getAddress());
											for (int i = 0; i < list.size(); i++) {
												total = total
														+ Double.parseDouble((list
																.get(i)
																.getPreMoney()));
											}
											textviewtotal.setText(total + " 元");
											adapter.setList(list);
											listView.setAdapter(adapter);

										}
									}
									handler.sendEmptyMessage(2);
								}
							}, new Response.ErrorListener() {
								@Override
								public void onErrorResponse(
										VolleyError volleyError) {
									Toast.makeText(PrefecthActivity.this,
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
				// TODO Auto-generated method stub
				finish();
			}
		});

		dialog1 = new MyDatePickerDialog(this, new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				start.setText(year + "-" + (monthOfYear + 1));
				startyear = year + "";
				startmonth = monthOfYear + "";
			}
		}, year, monthofyear, dayofmonth);
		
		dialog1.setButton(DialogInterface.BUTTON_NEGATIVE,"取消",new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
            }  
        });  

		dialog2 = new MyDatePickerDialog(this, new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				end.setText(year + "-" + (monthOfYear + 1));
				endyear = year + "";
				endmonth = monthOfYear + "";
			}
		}, year, monthofyear, dayofmonth);
		
		dialog2.setButton(DialogInterface.BUTTON_NEGATIVE,"取消",new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
            }  
        });  
		
		dialog1.setTitle("");
		dialog2.setTitle("");
		
		start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				dialog1.show();
				DatePicker dp = findDatePicker((ViewGroup) dialog1.getWindow()
						.getDecorView());
				if (dp != null) {
					((ViewGroup) ((ViewGroup) dp.getChildAt(0)).getChildAt(0))
							.getChildAt(2).setVisibility(View.GONE);
				}
			}
		});
		end.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				dialog2.show();
				DatePicker dp = findDatePicker((ViewGroup) dialog2.getWindow()
						.getDecorView());
				if (dp != null) {
					((ViewGroup) ((ViewGroup) dp.getChildAt(0)).getChildAt(0))
							.getChildAt(2).setVisibility(View.GONE);
				}
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
						+ Utils.getStatusBarHeight(PrefecthActivity.this);
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
