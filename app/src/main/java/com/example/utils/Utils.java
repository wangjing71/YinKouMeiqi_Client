package com.example.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Utils {

	//获取状态栏高度
	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier(
				"status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	//获取动态丶
	public static String GetPoint(int count) {
		String str = ".";
		for (int i = 0; i < count; i++) {
			str = str + ".";
		}
		return str;
	}

	//处理图片压缩
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// 源图片的高度和宽度
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			// 计算出实际宽高和目标宽高的比率
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			// 计算缩放比例
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	public static Bitmap decodeSampledBitmapFromResource(Resources res,
			int resId, int reqWidth, int reqHeight) {
		// 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);
		// 调用上面定义的方法计算inSampleSize值
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);
		// 使用获取到的inSampleSize值再次解析图片
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	
	public static String getType(String s) {
		if (s.equals("1")) {
			return "居民";
		} else if (s.equals("2")) {
			return "工业";
		} else if (s.equals("3")) {
			return "居民阶梯";
		} else if (s.equals("4")) {
			return "A居民阶梯";
		} else {
			return "非居民A";
		}
	}
	public static String getPayyesno(String s) {
		if (s.equals("0")) {
			return "未收费";
		} else if (s.equals("2")) {
			return "窗口收费";
		} else if (s.equals("3")) {
			return "预存收费";
		} else if (s.equals("4")) {
			return "补交收费";
		} else if (s.equals("11")) {
			return "银联";
		} else if (s.equals("12")) {
			return "银行代扣";
		} else 
			return "销帐";
	}
	
	//隐藏输入法
    public static void hideInputMethod(Activity activity){
        View a = activity.getCurrentFocus();
        if(a != null){   
            InputMethodManager imm = (InputMethodManager) activity.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);                
            try {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            } catch (Exception e) {    
                e.printStackTrace();
            }
        }
    }
     
     
    //强制显示输入法（未测试）
    public static void toggleSoftInput(View view,Activity activity){
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);          
        } catch (Exception e) {
             
        }
    }  
    
    //判断日期是否在选择的日期之内
    public static boolean comparedate(String date,String startyear,String startmonth,
    		String endyear,String endmonth){
    	boolean flag = false;
    	return flag;
    }

}
