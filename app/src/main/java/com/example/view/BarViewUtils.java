package com.example.view;

import java.util.ArrayList;
import java.util.Calendar;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;

public class BarViewUtils {
	public GraphicalView xychar(String[] titles, ArrayList<Double> value,
			int[] colors, int x, int y, double[] range, int[] xLable,
			String xtitle, boolean f, Context context) {
		// 多个渲染
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		// 多个序列的数据集
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		// 构建数据集以及渲染
		XYSeries series = new XYSeries(titles[0]);
		for (int j = 0; j < value.size(); j++) {
			series.add(xLable[j], value.get(j));
		}
		dataset.addSeries(series);
		XYSeriesRenderer xyRenderer = new XYSeriesRenderer();
		// 设置颜色
		xyRenderer.setColor(colors[0]);
		// 设置点的样式 //
		xyRenderer.setPointStyle(PointStyle.SQUARE);
		// 将要绘制的点添加到坐标绘制中

		renderer.addSeriesRenderer(xyRenderer);
		// 设置x轴标签数
		renderer.setXLabels(x);
		// 设置Y轴标签数
		renderer.setYLabels(y);
		// 设置x轴的最大值
		renderer.setXAxisMax(x - 0.5);
		// 设置轴的颜色
		renderer.setAxesColor(Color.BLACK);
		// 设置x轴和y轴的标签对齐方式
		renderer.setXLabelsAlign(Align.CENTER);
		renderer.setYLabelsAlign(Align.RIGHT);
		// 设置现实网格
		renderer.setShowGrid(true);

		renderer.setShowAxes(true);
		// 设置条形图之间的距离
		renderer.setBarSpacing(0.5);
		renderer.setInScroll(false);
		renderer.setPanEnabled(false, false);
		renderer.setClickEnabled(false);
		// 设置x轴和y轴标签的颜色
		renderer.setXLabelsColor(Color.RED);
		renderer.setYLabelsColor(0, Color.RED);
		int length = renderer.getSeriesRendererCount();
		// 设置图标的标题
		renderer.setChartTitle(xtitle);
		renderer.setLabelsColor(Color.BLACK);
		renderer.setChartTitleTextSize(40);
		// 设置 xy 轴标题字体大小
		renderer.setAxisTitleTextSize(30);
		renderer.setLabelsTextSize(30);
		// 设置图例的字体大小
		renderer.setLegendTextSize(30);
		// 设置x轴和y轴的最大最小值
		renderer.setRange(range);
		renderer.setMarginsColor(0x00888888);
		renderer.setMargins(new int[] { 60, 80, 60, 00 });// 设置视图位置

		for (int i = 0; i < length; i++) {
			SimpleSeriesRenderer ssr = renderer.getSeriesRendererAt(i);
			ssr.setChartValuesTextAlign(Align.RIGHT);
			ssr.setChartValuesTextSize(12);
			ssr.setDisplayChartValues(f);
		}

		
		Calendar calendar = Calendar.getInstance();
		int monthofyear = calendar.get(Calendar.MONTH);
		for(int i = 0;i<12;i++){
			renderer.addXTextLabel(xLable[i], ((monthofyear+i)%12+1)+"");
		}
		renderer.setXLabels(0);
//		renderer.setXTitle("月份"); // 设置X轴的标题
//		renderer.setYTitle("用气量"); // 设置Y轴的标题
		GraphicalView mChartView = ChartFactory.getBarChartView(
				context.getApplicationContext(), dataset, renderer,
				Type.DEFAULT);

		return mChartView;

	}

}
