package cn.lyj.work.masswork;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import allen.frame.ActivityHelper;
import allen.frame.AllenBaseActivity;
import allen.frame.tools.Constants;
import cn.lyj.work.R;
import cn.lyj.work.adapter.GzSthAdapter;
import cn.lyj.work.entry.SthEntry;
import cn.lyj.work.utils.OnNumDataListenner;

public class SthGzActivity extends AllenBaseActivity {

    private Toolbar bar;
    private List<SthEntry> sublist,list;
    private int page = 0,pagesize = 20;
    private String ucode;
    private GzSthAdapter adapter;
    private RecyclerView lv;
    private SmartRefreshLayout mater;
    private AppCompatEditText keyet;
    private String key="";
    private boolean isRefresh = false;

    @Override
    protected void onResume() {
        super.onResume();
        actHelper.hideSoftInputView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        actHelper.hideSoftInputView();
    }

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.sth_list_layout;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                isRefresh = true;
                page = 0;
                loadData();
            }
        }
    }

    @Override
    protected void initBar() {
        ucode = actHelper.getSharedPreferences().getString(Constants.USER_UNITCODE,"");
        bar = findViewById(R.id.toolbar);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        keyet = findViewById(R.id.key_et);
        mater = findViewById(R.id.mater);
        lv = findViewById(R.id.list);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        lv.setLayoutManager(manager);
        adapter = new GzSthAdapter();
        lv.setAdapter(adapter);
        actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START,"");
        loadData();
    }

    @Override
    protected void addEvent() {
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mater.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isRefresh = false;
                loadData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isRefresh = true;
                page = 0;
                loadData();
            }
        });
        adapter.setOnItemClickListener(itemclick);
        findViewById(R.id.key_bt).setOnClickListener(l);
    }
    private OnNumDataListenner dataListenner = new OnNumDataListenner() {
        @Override
        public void showTotal(final String num) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    bar.setTitle("("+num+")跟踪督办");
                }
            });
        }

        @Override
        public void enpty() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    bar.setTitle("跟踪督办");
                }
            });
        }
    };
    private void loadData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
//                sublist = AppDataHelper.init().getSxGZDBList(ucode,key,page++,pagesize,dataListenner);
//                handler.sendEmptyMessage(0);
            }
        }).start();
    }


    private GzSthAdapter.OnItemClickListener itemclick = new GzSthAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(SthEntry entry) {
            startActivityForResult(new Intent(context,SthGzDetailActivity.class).putExtra(Constants.STH_ID,entry.getId()),1);
        }
    };

    private View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.setClickable(false);
            if (v.getId() == R.id.key_bt) {
                key = keyet.getText().toString().trim();
                actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START, "");
                isRefresh = true;
                page = 0;
                loadData();
            }
            v.setClickable(true);
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_SUCCES,"");
                    if(isRefresh){
                        list = sublist;
                        mater.finishRefresh();
                    }else{
                        if(page==1){
                            list = sublist;
                        }else{
                            list.addAll(sublist);
                        }
                        mater.finishLoadMore();
                    }
                    if(list.size()==0){
                        actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_FAIL,"");
                    }
                    adapter.setList(list);
                    mater.setNoMoreData(actHelper.isNoMoreData(sublist,pagesize));
                    break;
            }
        }
    };
}
