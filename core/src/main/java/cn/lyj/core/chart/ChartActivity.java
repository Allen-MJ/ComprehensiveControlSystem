package cn.lyj.core.chart;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;

import java.util.List;

import allen.frame.AllenBaseActivity;
import allen.frame.entry.Response;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.Constants;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import cn.lyj.core.R;
import cn.lyj.core.R2;
import cn.lyj.core.api.CoreApi;
import cn.lyj.core.entry.ChartEntry;
import cn.lyj.core.entry.Model;

public class ChartActivity extends AllenBaseActivity {

    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.chart)
    BarChart chart;
    private Model entry;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.core_chart_layout;
    }

    @Override
    protected void initBar() {
        entry = (Model) getIntent().getSerializableExtra(Constants.ObjectFirst);
        setToolbarTitle(toolbar,entry.getName(),true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        chart.setBackgroundColor(Color.WHITE);
        //不显示图表网格
        chart.setDrawGridBackground(false);
        //背景阴影
        chart.setDrawBarShadow(false);
        chart.setHighlightFullBarEnabled(false);
        //显示边框
        chart.setDrawBorders(true);

        /***XY轴的设置***/
        //X轴设置显示位置在底部
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setAxisMinimum(0f);
//        xAxis.setGranularity(1f);

        YAxis leftAxis = chart.getAxisLeft();
        YAxis rightAxis = chart.getAxisRight();
        //保证Y轴从0开始，不然会上移一点
//        leftAxis.setAxisMinimum(0f);
//        rightAxis.setAxisMinimum(0f);

        /***折线图例 标签 设置***/
        Legend legend = chart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(11f);
        //显示位置
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //是否绘制在图表里面
        legend.setDrawInside(false);


        //不显示表边框
        chart.setDrawBorders(false);
//        不显示右下角描述内容
        Description description = new Description();
        description.setEnabled(false);
        chart.setDescription(description);
//        不显示X轴 Y轴线条
        xAxis.setDrawAxisLine(false);
        leftAxis.setDrawAxisLine(false);
        rightAxis.setDrawAxisLine(false);
        leftAxis.setEnabled(false);
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

    private void loadData(){
        Https.with(this).url(CoreApi.CoreChart).addParam("decisionType",entry.getId()).get()
                .enqueue(new Callback<List<ChartEntry>>() {
                    @Override
                    public void success(List<ChartEntry> data) {

                    }

                    @Override
                    public void fail(Response response) {

                    }
                });
    }

}
