package com.example.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.example.utils.Item;
import com.example.yinkoumeiqi.R;

public class MyGridAdapter extends BaseAdapter {
	private int x;
	private int y;

	ArrayList<Item> list;
	LayoutInflater inflater;
	GridView gridView;

	public MyGridAdapter(Context context, GridView gridView) {
		inflater = LayoutInflater.from(context);
		this.gridView = gridView;
	}

	public void setList(ArrayList<Item> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		AbsListView.LayoutParams param = new AbsListView.LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				(gridView.getHeight() - 25) / 4);
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.grid_item, null);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView.findViewById(R.id.image);

			x = (gridView.getHeight()) / 8;
			y = (gridView.getWidth()) / 8;
			LayoutParams parms = (LayoutParams) holder.image.getLayoutParams();
			parms.height = x;
			parms.width = y;
			holder.image.setLayoutParams(parms);

			holder.title = (TextView) convertView.findViewById(R.id.title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			LayoutParams parms = (LayoutParams) holder.image.getLayoutParams();
			parms.height = x;
			parms.width = y;
			holder.image.setLayoutParams(parms);
		}

		holder.image.setImageBitmap(list.get(position).bitmap);
		holder.title.setText(list.get(position).str);
		convertView.setLayoutParams(param);
		return convertView;
	}

	class ViewHolder {
		ImageView image;
		TextView title;
	}
}
