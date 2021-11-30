package cn.lyjj.thepublic;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import allen.frame.ActivityHelper;
import allen.frame.AllenBaseActivity;
import allen.frame.adapter.CommonAdapter;
import allen.frame.adapter.ViewHolder;
import allen.frame.entry.Response;
import allen.frame.entry.Units;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.Constants;
import allen.frame.tools.Logger;
import allen.frame.tools.MaterialUtil;
import allen.frame.widget.FluidLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import cn.lyjj.thepublic.api.TipApi;

public class ShowCanChoiceUnitsActiivty extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.units_rv)
    RecyclerView unitsRv;
    @BindView(R2.id.units_perant)
    FluidLayout unitsPerant;

    private CommonAdapter<Units> adapter;
    private List<Units> list, gens;
    private String mKey = "";
    private int index = 0;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.tipoff_choice_units_layout;
    }

    @Override
    protected void initBar() {
        setToolbarTitle(toolbar, "请选择单位", true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        gens = new ArrayList<>();
        Units gen = new Units();
        gen.setOrgId("");
        gen.setOrgName("根目录");
        gens.add(gen);
        setGenLayout(index);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        unitsRv.setLayoutManager(manager);
        adapter = new CommonAdapter<Units>(context, R.layout.alen_choice_units_item) {
            @Override
            public void convert(ViewHolder holder, Units entity, int position) {
                holder.setText(R.id.item_name,entity.getOrgName());
            }
        };
        unitsRv.setAdapter(adapter);
        loadData("");
    }

    @Override
    protected void addEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ViewHolder holder, int position) {
                Units units = list.get(position);
                if(units.isHasChildren()){
                    index += 1;
                    gens.add(units);
                    setGenLayout(index);
                    loadData(units.getOrgId());
                }else{
                    setResult(RESULT_OK,getIntent().putExtra(Constants.ObjectFirst,units));
                    finish();
                }
            }

            @Override
            public boolean onItemLongClick(View view, ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private void loadData(String pid) {
        actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START, "");
        Https.with(this).url(TipApi.SmartOrgChoice).addParam("pid", pid)
                .addParam("orgName", mKey).get()
                .enqueue(new Callback<List<Units>>() {

                    @Override
                    public void success(List<Units> data) {
                        list = data;
                        showData();
                    }

                    @Override
                    public void token() {
                        list = new ArrayList<>();
                        showData();
                        actHelper.tokenErro2Login(ShowCanChoiceUnitsActiivty.this);
                    }

                    @Override
                    public void fail(Response response) {
                        list = new ArrayList<>();
                        showData();
                    }
                });
    }

    private void showData() {
        adapter.setDatas(list);
        if (list == null || list.size() == 0) {
            actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_FAIL, "暂无数据");
        } else {
            actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_SUCCES, "");
        }
    }

    private void setGenLayout(int mindex) {
        List<Units> temp = new ArrayList<>();
        for (int i = 0; i <= mindex; i++) {
            Logger.e("debug",mindex+"<当前指针："+i);
            temp.add(gens.get(i));
        }
        gens.clear();
        gens.addAll(temp);
        unitsPerant.removeAllViews();
        for (int i = 0; i < gens.size(); i++) {
            final int k = i;
            AppCompatTextView item = new AppCompatTextView(context);
            final Units entry = gens.get(i);
            item.setText(entry.getOrgName() + " >");
            item.setTextColor(Color.GRAY);
            item.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(allen.frame.R.dimen.frame_text_normal_size));
            int padding = MaterialUtil.dip2px(context, 8);
            int margin = MaterialUtil.dip2px(context, 4);
            item.setPaddingRelative(padding, padding, padding, padding);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadData(entry.getOrgId());
                    index = k;
                    setGenLayout(index);
                }
            });
            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(margin, margin, margin, margin);
            unitsPerant.addView(item, params);
        }
    }

    public void back(){
        if(index==0){
            finish();
        }else {
            index-=1;
            setGenLayout(index);
            loadData(gens.get(index).getOrgId());
        }
    }

}
