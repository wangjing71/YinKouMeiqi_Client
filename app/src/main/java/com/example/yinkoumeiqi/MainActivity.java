package com.example.yinkoumeiqi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.adapter.MyGridAdapter;
import com.example.user.ArrearageActivity;
import com.example.user.ChargeActivity;
import com.example.user.GasQueryActivity;
import com.example.user.PrefecthActivity;
import com.example.user.UserQueryActivity;
import com.example.utils.Item;
import com.example.utils.Url;
import com.example.utils.Utils;
import com.example.view.Dialog;
import com.example.view.ViewPagerScroller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity {

	private ViewPager pager;
	private GridView gridView;
	private ArrayList<Item> list;
	private MyGridAdapter adapter;
	private ArrayList<View> view;
	private int count = 0;
	private RelativeLayout rela, rela2;
	private RequestQueue queue;
	private LinearLayout llPointGrouplunbo;
	private View viewSelectPointlunbo;
	private int mpointwidth;
	private int pointMarginLeft;
	String[] str = new String[] { "集团简介", "企业文化", "机构设置", "职责范围", "电话地址",
			"服务承诺", "服务流程", "收费标准", "安全常识", "应急处理", "用户须知", "预存查询", "我的信息", "用气分析"
			,"收费信息", "欠费查询" };
	int[] images = { R.drawable.yk1, R.drawable.yk2, R.drawable.yk3,
			R.drawable.yk4, R.drawable.yk5, R.drawable.yk6, R.drawable.yk7 };
	int[] icons = { R.drawable.jtjj, R.drawable.qywh, R.drawable.jgsz,
			R.drawable.zzfw, R.drawable.dhdz, R.drawable.fwcn, R.drawable.fwlc,
			R.drawable.sfbz, R.drawable.aqcs, R.drawable.yjcl, R.drawable.yhxz,
			R.drawable.yccx, R.drawable.yhcx, R.drawable.yqcx, R.drawable.sfcx,
			R.drawable.qfcx };

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				// 轮播控制
				handler.removeMessages(0);
				pager.setCurrentItem(count);
				count++;
				handler.sendEmptyMessageDelayed(1, 3500);
			} else if (msg.what == 2) {
				// 弹出更新窗口
				Dialog dialog = new Dialog(MainActivity.this, (String) msg.obj);
				dialog.updatedialog();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		String[] dangqian = ((new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss"))
				.format(new Date())).split("\\.");
		String[] daoqi = Url.expiringdate.split("\\.");

		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(Integer.parseInt(daoqi[0]), Integer.parseInt(daoqi[1]),
				Integer.parseInt(daoqi[2]), Integer.parseInt(daoqi[3]),
				Integer.parseInt(daoqi[4]), Integer.parseInt(daoqi[5]));
		calendar1.set(Calendar.MILLISECOND, 0);

		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(Integer.parseInt(dangqian[0]),
				Integer.parseInt(dangqian[1]), Integer.parseInt(dangqian[2]),
				Integer.parseInt(dangqian[3]), Integer.parseInt(dangqian[4]),
				Integer.parseInt(dangqian[5]));
		calendar2.set(Calendar.MILLISECOND, 0);

		if (calendar1.getTime().getTime() < calendar2.getTime().getTime()) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("提示");
			builder.setMessage("使用期限已到，点击确认退出程序");
			builder.setPositiveButton("确定",
					new android.content.DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					});
			builder.setCancelable(false);
			builder.show();
		}

		
		
		pager = (ViewPager) findViewById(R.id.pager);
		gridView = (GridView) findViewById(R.id.gridView1);
		list = new ArrayList<Item>();
		adapter = new MyGridAdapter(MainActivity.this, gridView);
		view = new ArrayList<View>();
		ViewPagerScroller scroller = new ViewPagerScroller(this);
		rela = (RelativeLayout) findViewById(R.id.rela1);
		rela2 = (RelativeLayout) findViewById(R.id.rela2);

		llPointGrouplunbo = (LinearLayout) findViewById(R.id.ll_point_group);
		viewSelectPointlunbo = findViewById(R.id.view_red_point);
		queue = Volley.newRequestQueue(this);

		// 初始化引导页的小圆点
		for (int i = 0; i < images.length; i++) {
			View point = new View(this);
			point.setBackgroundResource(R.drawable.shape_point_default);
			LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(30, 30);
			if (i > 0) {
				parms.leftMargin = 15;
			}
			point.setLayoutParams(parms);
		}

		for (int i = 0; i < images.length; i++) {
			View point = new View(this);
			point.setBackgroundResource(R.drawable.shape_point_default);
			LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(30, 30);
			if (i > 0) {
				parms.leftMargin = 15;
			}
			point.setLayoutParams(parms);
			llPointGrouplunbo.addView(point);
		}



		llPointGrouplunbo.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						pointMarginLeft = llPointGrouplunbo.getLeft();
						mpointwidth = llPointGrouplunbo.getChildAt(1).getLeft()
								- llPointGrouplunbo.getChildAt(0).getLeft();

						//初始化红点
						RelativeLayout.LayoutParams parms = (RelativeLayout.LayoutParams) viewSelectPointlunbo.getLayoutParams();
						parms.width = 30;
						parms.height = 30;
						parms.leftMargin = pointMarginLeft;
						viewSelectPointlunbo.setLayoutParams(parms);
						llPointGrouplunbo.getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
					}
				});


		rela2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent phoneIntent = new Intent("android.intent.action.CALL",
						Uri.parse("tel:" + "2827777"));
				startActivity(phoneIntent);
			}
		});

		for (int i = 0; i < 7; i++) {
			ImageView iv = new ImageView(this);
			Bitmap bitmap = Utils.decodeSampledBitmapFromResource(
					getResources(), images[i], 300, 200);
			iv.setImageBitmap(bitmap);
			iv.setScaleType(ScaleType.FIT_XY);
			view.add(iv);
		}

		for (int i = 1; i < 17; i++) {
			list.add(new Item(str[i - 1], BitmapFactory.decodeResource(
					getResources(), icons[i - 1])));
		}
		scroller.setScrollDuration(2500);
		scroller.initViewPagerScroll(pager);

		adapter.setList(list);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gridView.setAdapter(adapter);
		pager.setAdapter(new Myadapter());
		handler.sendEmptyMessage(1);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels; // 获取屏幕宽度

		// 开启子线程进行检查更新
		new Thread(new Runnable() {
			public void run() {
				try {
					Log.i("===========", "检查版本更新子线程已启动");
					URL url = new URL(Url.CheckUpdateURL);
					HttpURLConnection con = (HttpURLConnection) url
							.openConnection();
					con.connect();
					if (con.getResponseCode() == 200) {
						InputStream is = con.getInputStream();
						BufferedReader br = new BufferedReader(
								new InputStreamReader(is));
						String str = br.readLine();
						Log.i("str", str.substring(0, 7));
						if(str.subSequence(0, 7).equals("success")){
							
							String[] info = str.split(",");
							String newversion = info[0].substring(7, info[0].length());
							String NewVersionDownURL = info[1];
							
							Log.i("************当前程序的版本号为 ", Url.CurrentVersion);
							Log.i("************获取到的最新版本号为", newversion);
							if (!newversion.equals(Url.CurrentVersion)) {
								Message message = Message.obtain();
								message.what = 2;
								message.obj = NewVersionDownURL;
								handler.sendMessage(message);
							}
						}else{
							Log.i("========", "数据错误");
						}
					} else {
						Log.i("========", "连接失败");
					}

				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();

		rela.post(new Runnable() {
			// TODO 处理状态栏高度问题
			@Override
			public void run() {
				int pos = rela.getHeight();
				RelativeLayout.LayoutParams layoutparams = (RelativeLayout.LayoutParams) rela
						.getLayoutParams();
				layoutparams.height = pos
						+ Utils.getStatusBarHeight(MainActivity.this);
				rela.setLayoutParams(layoutparams);
			}
		});



		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				count = position;
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				// TODO Auto-generated method stub
				int len = pointMarginLeft + (int) (mpointwidth * positionOffset) + (position % images.length) * mpointwidth;
				RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewSelectPointlunbo
						.getLayoutParams();
				params.leftMargin = len;
				viewSelectPointlunbo.setLayoutParams(params);

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});


		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.i("position", position + "");
				switch (position) {
				case 0:
					Intent intent0 = new Intent(MainActivity.this,
							DetailInfoActivity.class);
					intent0.putExtra("url", Url.JTJJ);
					intent0.putExtra("id", "0");
					intent0.putExtra("title", "集团简介");
					startActivity(intent0);

					break;
				case 1:
					Intent intent1 = new Intent(MainActivity.this,
							DetailInfoActivity.class);
					intent1.putExtra("url", Url.QYWH);
					intent1.putExtra("id", "1");
					intent1.putExtra("title", "企业文化");
					startActivity(intent1);

					break;
				case 2:
					Intent intent2 = new Intent(MainActivity.this,
							DetailInfoActivity.class);
					intent2.putExtra("url", Url.JGSZ);
					intent2.putExtra("id", "2");
					intent2.putExtra("title", "机构设置");
					startActivity(intent2);

					break;
				case 3:
					Intent intent3 = new Intent(MainActivity.this,
							DetailInfoActivity.class);
					intent3.putExtra("url", Url.ZZFW);
					intent3.putExtra("id", "3");
					intent3.putExtra("title", "职责范围");
					startActivity(intent3);

					break;
				case 4:
					Intent intent4 = new Intent(MainActivity.this,
							DetailInfoActivity.class);
					intent4.putExtra("url", Url.DHDZ);
					intent4.putExtra("id", "4");
					intent4.putExtra("title", "电话地址");
					startActivity(intent4);

					break;
				case 5:
					Intent intent5 = new Intent(MainActivity.this,
							DetailInfoActivity.class);
					intent5.putExtra("url", Url.FWCN);
					intent5.putExtra("id", "5");
					intent5.putExtra("title", "服务承诺");
					startActivity(intent5);
					break;
				case 6:
					Intent intent6 = new Intent(MainActivity.this,
							DetailInfoActivity.class);
					intent6.putExtra("url", Url.FWLC);
					intent6.putExtra("id", "6");
					intent6.putExtra("title", "服务流程");
					startActivity(intent6);
					break;
				case 7:
					Intent intent7 = new Intent(MainActivity.this,
							DetailInfoActivity.class);
					intent7.putExtra("url", Url.SFBZ);
					intent7.putExtra("id", "7");
					intent7.putExtra("title", "收费标准");
					startActivity(intent7);

					break;
				case 8:
					Intent intent8 = new Intent(MainActivity.this,
							DetailInfoActivity.class);
					intent8.putExtra("url", Url.AQCS);
					intent8.putExtra("id", "8");
					intent8.putExtra("title", "安全常识");
					startActivity(intent8);
					break;
				case 9:
					Intent intent9 = new Intent(MainActivity.this,
							DetailInfoActivity.class);
					intent9.putExtra("url", Url.YJCL);
					intent9.putExtra("title", "应急处理");
					intent9.putExtra("id", "9");
					startActivity(intent9);
					break;
				case 10:
					Intent intent10 = new Intent(MainActivity.this,
							DetailInfoActivity.class);
					intent10.putExtra("url", Url.YHXZ);
					intent10.putExtra("id", "10");
					intent10.putExtra("title", "用户须知");
					startActivity(intent10);
					break;
				case 11:
					Intent intent11 = new Intent(MainActivity.this,
							PrefecthActivity.class);

					startActivity(intent11);
					break;
				case 12:
					Intent intent12 = new Intent(MainActivity.this,
							UserQueryActivity.class);
					intent12.putExtra("id", "10");
					intent12.putExtra("title", "我的信息");
					startActivity(intent12);

					break;
				case 13:
					Intent intent13 = new Intent(MainActivity.this,
							GasQueryActivity.class);
					intent13.putExtra("id", "13");
					intent13.putExtra("title", "用气分析");
					startActivity(intent13);
					break;
				case 14:
					Intent intent14 = new Intent(MainActivity.this,
							ChargeActivity.class);
					intent14.putExtra("id", "14");
					intent14.putExtra("title", "收费信息");
					startActivity(intent14);
					break;
				case 15:
					Intent intent15 = new Intent(MainActivity.this,
							ArrearageActivity.class);
					intent15.putExtra("id", "15");
					intent15.putExtra("title", "欠费查询");
					startActivity(intent15);

					break;
				default:
					break;
				}
			}
		});

	}

	class Myadapter extends PagerAdapter {

		public int getCount() {

			 return Integer.MAX_VALUE;
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		public Object instantiateItem(ViewGroup container, int position) {
			position %= view.size();
			if (position < 0) {
				position = view.size() + position;
			}
			View v = view.get(position);

			ViewParent vp = v.getParent();
			if (vp != null) {
				ViewGroup parent = (ViewGroup) vp;
				parent.removeView(v);
			}
			container.addView(view.get(position));
			return view.get(position);
		}

		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(view.get(position%view.size()));
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("提示");
			builder.setMessage("是否退出本程序？");
			builder.setPositiveButton("确定",
					new android.content.DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					});
			builder.setNegativeButton("取消", null);
			builder.show();
		}
		return false;

	}
}
