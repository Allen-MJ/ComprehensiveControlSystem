package cn.lyj.core.chart;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import allen.frame.AllenBaseActivity;
import allen.frame.entry.Response;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.Constants;
import allen.frame.tools.DatePickerDialog;
import allen.frame.tools.MsgUtils;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.OnClick;
import cn.lyj.core.R;
import cn.lyj.core.R2;
import cn.lyj.core.api.CoreApi;
import cn.lyj.core.entry.ChartEntry;
import cn.lyj.core.entry.Model;

public class ZdpcChartActivity extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.start_date)
    AppCompatTextView startDate;
    @BindView(R2.id.end_date)
    AppCompatTextView endDate;
    @BindView(R2.id.chart)
    BarChart chart;
    private List<ChartEntry> list;

    private YAxis leftAxis;             //左侧Y轴
    private YAxis rightAxis;            //右侧Y轴
    private XAxis xAxis;                //X轴
    private Legend legend;              //图例

    private Model entry;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.core_zdpc_chart_layout;
    }

    @Override
    protected void initBar() {
        entry = (Model) getIntent().getSerializableExtra(Constants.ObjectFirst);
        setToolbarTitle(toolbar,entry.getName(),true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        String text = year + "-" + String.format("%02d", monthOfYear + 1) + "-" + String.format("%02d", dayOfMonth);
        startDate.setText(text);
        endDate.setText(text);
        initChart();
        loadData();
    }

    @Override
    protected void addEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initChart(){
        chart.setBackgroundColor(Color.WHITE);
        //不显示图表网格
        chart.setDrawGridBackground(false);
        //背景阴影
        chart.setDrawBarShadow(false);
        chart.setHighlightFullBarEnabled(false);
        //显示边框
        chart.setDrawBorders(true);
        chart.setPinchZoom(false);
        chart.setExtraOffsets(15f,15f,15f,15f);
        /***XY轴的设置***/
        //X轴设置显示位置在底部
        xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setAxisMinimum(0f);
//        xAxis.setGranularity(1f);
        xAxis.setGranularity(1);
        leftAxis = chart.getAxisLeft();
        rightAxis = chart.getAxisRight();
        //保证Y轴从0开始，不然会上移一点
        leftAxis.setAxisMinimum(0f);
        rightAxis.setAxisMinimum(0f);

        /***折线图例 标签 设置***/
        legend = chart.getLegend();
        legend.setEnabled(false);
        /*legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(10f);
        //显示位置
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //是否绘制在图表里面
        legend.setDrawInside(false);*/

        chart.setDrawBorders(true);
//        不显示右下角描述内容
        Description description = new Description();
        description.setEnabled(false);
        chart.setDescription(description);
//        不显示X轴 Y轴线条
        xAxis.setDrawAxisLine(false);
        leftAxis.setDrawAxisLine(false);
        rightAxis.setDrawAxisLine(false);
        leftAxis.setEnabled(false);
    }

    @OnClick({R2.id.start_date, R2.id.end_date})
    public void onViewClicked(View view) {
        view.setEnabled(false);
        int id = view.getId();
        if(id==R.id.start_date){
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth) {
                    startDate.setText(startYear + "-" + String.format("%02d", startMonthOfYear + 1) + "-" + String.format("%02d", startDayOfMonth));
                    loadData();
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }else if(id==R.id.end_date){
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth) {
                    endDate.setText(startYear + "-" + String.format("%02d", startMonthOfYear + 1) + "-" + String.format("%02d", startDayOfMonth));
                    loadData();
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }
        view.setEnabled(true);
    }

    private void loadData(){
        Https.with(this).url(CoreApi.CoreEventsAreal)
                .addParam("startDate",startDate.getText().toString())
                .addParam("endDate",endDate.getText().toString()).get()
                .enqueue(new Callback<List<ChartEntry>>() {
                    @Override
                    public void success(List<ChartEntry> data) {
                        list = data;
                        setBarData();
                    }

                    @Override
                    public void fail(Response response) {
                        MsgUtils.showMDMessage(context,response.getMsg());
                        list = new ArrayList<>();
                        setBarData();
                    }
                });
    }

    private void setBarData(){
        if(list.size()==0){
            chart.setNoDataText("暂无数据!");
            chart.invalidate();
            return;
        }
        List<BarEntry> bars = new ArrayList<>();
        List<Integer> integers = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            BarEntry barEntry = new BarEntry(i,list.get(i).getValue());
            bars.add(barEntry);
            int color = Colors.getColor(i);
            integers.add(color);
        }
        chart.getXAxis().setLabelCount(list.size());
        chart.getXAxis().setLabelRotationAngle(45f);
        chart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return list.get((int)value).getLabel();
            }
        });
        BarDataSet barDataSet = new BarDataSet(bars,"");
        barDataSet.setColors(integers);
        barDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int)value);
            }
        });
        BarData data = new BarData(barDataSet);
        data.notifyDataChanged();
        chart.setData(data);
        chart.notifyDataSetChanged();
        chart.invalidate();
    }
}
