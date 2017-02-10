package com.example.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.utils.PrefecthInfo;
import com.example.yinkoumeiqi.R;

public class MyListViewAdapter extends BaseAdapter {

	ArrayList<PrefecthInfo> list;
	LayoutInflater inflater;
	

	public MyListViewAdapter(Context context) {
		inflater = LayoutInflater.from(context);
	}

	public void setList(ArrayList<PrefecthInfo> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.list_item, null);
			holder = new ViewHolder();
			holder.tv1 = (TextView) convertView.findViewById(R.id.textView1);
			holder.tv2 = (TextView) convertView.findViewById(R.id.textView2);
			holder.tv3 = (TextView) convertView.findViewById(R.id.textView3);
			holder.tv4 = (TextView) convertView.findViewById(R.id.textView4);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.tv1.setText((position+1)+"");
		holder.tv2.setText(list.get(position).getPreMoney());
		holder.tv3.setText(list.get(position).getPreUseDate().subSequence(0, 10));
		if(list.get(position).getPreType().equals("1")){
			holder.tv4.setText("现金");
		}else{
			holder.tv4.setText("支票");
		}
		if(position == list.size()-1){
			convertView.setBackgroundResource(R.drawable.table_shape2);
		}
		return convertView;
	}

	class ViewHolder {
		TextView tv1;
		TextView tv2;
		TextView tv3;
		TextView tv4;
	}
}
